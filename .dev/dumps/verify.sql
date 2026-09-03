SELECT table_schema, COUNT(*) AS tbl_cnt
FROM information_schema.tables
WHERE table_schema IN ('autoee_ems','xxl_job')
GROUP BY table_schema;