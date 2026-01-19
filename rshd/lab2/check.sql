WITH spaced_tables AS (
SELECT
COALESCE(t.spcname, 'pg_default') AS spcname,
c.relname,
ROW_NUMBER() OVER (PARTITION BY COALESCE(t.spcname, 'pg_default') ORDER BY c.relname) AS rn
FROM pg_tablespace t
FULL JOIN pg_class c ON c.reltablespace = t.oid
ORDER BY spcname, c.relname
)
SELECT
CASE WHEN rn = 1 THEN spcname ELSE NULL END AS spcname,
relname
FROM spaced_tables;
