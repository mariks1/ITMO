import time
import sys
import serial
from serial import PARITY_ODD, STOPBITS_TWO

PORT = "COM12"
BAUD = 8400

SYNC = 0x5A
POLY = 0x07  # CRC-8: x^8 + x^2 + x + 1

def crc8(buf: bytes) -> int:
    crc = 0
    for b in buf:
        crc ^= b
        for _ in range(8):
            crc = ((crc << 1) ^ POLY) & 0xFF if (crc & 0x80) else (crc << 1) & 0xFF
    return crc

def make_packet(data: bytes, bad: str | None = None) -> bytes:
    sync_byte = b'' if bad == 'sync' else bytes([SYNC])
    length = len(data)
    crc = crc8(data)
    if bad == 'crc':
        crc ^= 0xFF
    return sync_byte + bytes([length]) + data + bytes([crc])

def read_packet(ser: serial.Serial, timeout: float = 2.0) -> str | None:
    deadline = time.time() + timeout
    while time.time() < deadline:
        b = ser.read(1)
        if not b or b[0] != SYNC:
            continue
        ln_b = ser.read(1)
        if not ln_b:
            continue
        length = ln_b[0]
        data = ser.read(length)
        crc_byte = ser.read(1)
        if len(data) != length or not crc_byte:
            continue
        if crc8(data) != crc_byte[0]:
            continue
        try:
            return data.decode('utf-8', errors='replace')
        except:
            return None
    return None

def main():
    try:
        ser = serial.Serial(
            PORT,
            BAUD,
            parity=PARITY_ODD,
            stopbits=STOPBITS_TWO,
            timeout=0.1
        )
    except serial.SerialException as e:
        print(f"Ошибка при открытии порта {PORT}: {e}")
        sys.exit(1)

    print(f"Открыт порт {ser.port}: {BAUD} baud, 8O2\n")
    print("Меню:")
    print(" 1 – отправить корректный пакет (HELLO) + сразу попытаться принять ответ")
    print(" 2 – отправить некорректный пакет (битый CRC) + сразу попытаться принять ответ")
    print(" 3 – принять один пакет")
    print(" 4 – непрерывно принимать пакеты")
    print(" 5 – выйти\n")

    try:
        while True:
            choice = input("Выберите пункт (1-5): ").strip()
            if choice == '1':
                pkt = make_packet(b'HELLO', bad=None)
                ser.write(pkt)
                print("→ Отправлен корректный пакет: HELLO")
                resp = read_packet(ser, timeout=2.0)
                if resp is not None:
                    print("← Получено:", resp)
                else:
                    print("← Нет ответа или получен некорректный пакет")

            elif choice == '2':
                pkt = make_packet(b'HELLO', bad='crc')
                ser.write(pkt)
                print("→ Отправлен некорректный пакет (битый CRC): HELLO")
                resp = read_packet(ser, timeout=2.0)
                if resp is not None:
                    print("← Получено (ожидаем, что не должно быть OK):", resp)
                else:
                    print("← Нет ответа (CRC-ошибка)")

            elif choice == '3':
                print("← Ожидание одного пакета")
                resp = read_packet(ser, timeout=2.0)
                if resp is not None:
                    print("← Получено:", resp)
                else:
                    print("← Таймаут: пакет не получен или некорректен")

            elif choice == '4':
                print("← Непрерывный приём запущен. Для выхода нажмите Ctrl+C.")
                try:
                    while True:
                        resp = read_packet(ser, timeout=0.5)
                        if resp is not None:
                            print("← Получено:", resp)
                except KeyboardInterrupt:
                    print("\n← Остановка непрерывного приёма. Возврат в меню.")

            elif choice == '5':
                print("Выход.")
                break

            else:
                print("Неправильный выбор. Введите число от 1 до 5.")

            time.sleep(0.1)
            ser.reset_input_buffer()

    except KeyboardInterrupt:
        print("\nПрерывание от пользователя. Завершаем.")
    finally:
        ser.close()

if __name__ == "__main__":
    main()
