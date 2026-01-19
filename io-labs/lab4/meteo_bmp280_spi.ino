#include <avr/io.h>
#include <string.h>
#include <Adafruit_BMP280.h>

#define BMP_SCK   13
#define BMP_MISO  12
#define BMP_MOSI  11
#define BMP_CS    10

Adafruit_BMP280 bmp(BMP_CS);

#define SYNC_BYTE 0x5A
#define MAX_LEN   64
#define POLY      0x07

uint8_t  rx_buf[MAX_LEN];
uint8_t  rx_len   = 0;
uint8_t  rx_state = 0;
uint8_t  rx_need  = 0;
uint8_t  rx_crc   = 0;
bool     packet_ready = false;

static void uart_init(void) {
    const uint32_t BAUD     = 8400UL;
    uint16_t ubrr = F_CPU / 16 / BAUD - 1;

    UBRR0H = ubrr >> 8;
    UBRR0L = ubrr & 0xFF;

    UCSR0B = _BV(TXEN0) | _BV(RXEN0);

    UCSR0C = _BV(UPM01) | _BV(UPM00) | _BV(USBS0) | _BV(UCSZ01) | _BV(UCSZ00);
}

static inline void uart_wait_tx_ready(void) {
    while (!(UCSR0A & _BV(UDRE0))) {
        ;
    }
}

static void uart_send_byte(uint8_t b) {
    uart_wait_tx_ready();
    UDR0 = b;
}

static uint8_t crc8_direct(const uint8_t *data, uint8_t len) {
    uint8_t crc = 0;
    while (len--) {
        crc ^= *data++;
        for (uint8_t i = 0; i < 8; ++i) {
            crc = (crc & 0x80) ? ((crc << 1) ^ POLY) : (crc << 1);
        }
    }
    return crc;
}

static void send_packet_raw(const uint8_t *data, uint8_t len) {
    if (len == 0 || len > 0xFF) return;

    uart_send_byte(SYNC_BYTE);
    uart_send_byte(len);
    for (uint8_t i = 0; i < len; ++i) {
        uart_send_byte(data[i]);
    }
    uart_send_byte(crc8_direct(data, len));
}

static void send_packet(const String &s) {
    const uint8_t *buf = (const uint8_t *)s.c_str();
    uint8_t         l   = (uint8_t)s.length();
    send_packet_raw(buf, l);
}

static void poll_rx_fsm(void) {
    while (UCSR0A & _BV(RXC0)) {
        uint8_t b = UDR0;

        switch (rx_state) {
            case 0:
                if (b == SYNC_BYTE) {
                    rx_state = 1;
                }
                break;

            case 1:
                rx_need = b;
                rx_len  = 0;
                rx_crc  = 0;
                rx_state = (rx_need ? 2 : 3);
                break;

            case 2:
                if (rx_len < MAX_LEN) {
                    rx_buf[rx_len++] = b;
                    rx_crc ^= b;
                    for (uint8_t i = 0; i < 8; ++i) {
                        rx_crc = (rx_crc & 0x80) ? ((rx_crc << 1) ^ POLY) : (rx_crc << 1);
                    }
                }
                if (rx_len == rx_need) {
                    rx_state = 3;
                }
                break;

            case 3:
                if (rx_len == rx_need && rx_crc == b) {
                    packet_ready = true;
                }
                rx_state = 0;
                break;
        }
    }
}

static void bmp_init() {
    if (!bmp.begin(BMP280_ADDRESS_ALT, BMP280_CHIPID)) {
        bmp.begin();
    }
    bmp.setSampling(
        Adafruit_BMP280::MODE_NORMAL,
        Adafruit_BMP280::SAMPLING_X2,
        Adafruit_BMP280::SAMPLING_X16,
        Adafruit_BMP280::FILTER_X16,
        Adafruit_BMP280::STANDBY_MS_500
    );
}

void setup() {
    uart_init();
    bmp_init();
}

void loop() {
    poll_rx_fsm();

    if (packet_ready) {
        packet_ready = false;
        static uint8_t tmp[MAX_LEN + 4];
        const char prefix[] = "OK: ";
        memcpy(tmp, prefix, 4);
        memcpy(tmp + 4, rx_buf, rx_len);
        uint8_t total_len = rx_len + 4;
        send_packet_raw(tmp, total_len);
    }

    static uint32_t last_ms = 0;
    uint32_t now = millis();
    if (now - last_ms >= 1000) {
        last_ms = now;
        // Температура
        char buf1[32];
        float t = bmp.readTemperature();
        snprintf(buf1, sizeof(buf1), "Temperature = %.2f", t);
        send_packet(String(buf1));
        send_packet(String(" *C\n"));

        // Давление
        char buf2[32];
        float p = bmp.readPressure();
        snprintf(buf2, sizeof(buf2), "Pressure = %.2f", p);
        send_packet(String(buf2));
        send_packet(String(" Pa\n"));
    }
}
