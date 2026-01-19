set -euo pipefail


PGPASSWORD=1234 psql -h 127.0.0.1 -p 9958 -U postgres1 -d fatwhiteidea <<'SQL'
INSERT INTO customers(name) VALUES ('Petr'), ('Irina');
INSERT INTO customer_sales(customer_id, sold_at, amount)
     VALUES (999, CURRENT_DATE, 550.00),
            (2, CURRENT_DATE, 880.00);
SQL

PGPASSWORD=1234 psql -h 127.0.0.1 -p 9958 -d fatwhiteidea -c "select * from customer_sales ORDER BY id"
PGPASSWORD=1234 pg_dump -U postgres1 -h 127.0.0.1 -F p -d fatwhiteidea -p 9958 > ./backup.sql
PGPASSWORD=1234 psql -h 127.0.0.1 -p 9958 -U postgres1 -d fatwhiteidea -f $HOME/uyu39/break.sql
PGPASSWORD=1234 psql -h 127.0.0.1 -p 9958 -d fatwhiteidea -c "select * from customer_sales ORDER BY id"


PGPASSWORD=1234 psql -h pg116 -p 9958 -U postgres1 postgres -c "DROP DATABASE fatwhiteidea"
PGPASSWORD=1234 psql -h pg116 -p 9958 -U postgres1 postgres -c "CREATE DATABASE fatwhiteidea TEMPLATE template1;"
PGPASSWORD=1234 psql -h 127.0.0.1 -p 9958 -d fatwhiteidea -f ./backup.sql
PGPASSWORD=1234 psql -h 127.0.0.1 -p 9958 -d fatwhiteidea -c "select * from customer_sales ORDER BY id"

