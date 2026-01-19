SET default_tablespace = '';

CREATE TABLE sales (
    id          BIGINT  GENERATED ALWAYS AS IDENTITY,
    sold_at     DATE        NOT NULL,
    amount      NUMERIC(12,2),
    PRIMARY KEY (sold_at, id)
) PARTITION BY RANGE (sold_at);

CREATE TABLE sales_2025_q1 PARTITION OF sales
    FOR VALUES FROM ('2025-01-01') TO ('2025-04-01')
    TABLESPACE tsp_nzz47;

CREATE TABLE sales_2025_q2 PARTITION OF sales
    FOR VALUES FROM ('2025-04-01') TO ('2025-07-01')
    TABLESPACE tsp_ymk58;

INSERT INTO sales (sold_at, amount) VALUES
    ('2025-01-15',  1200.00),
    ('2025-02-01',   450.50),
    ('2025-05-20',  3780.40);
