-- ================================================================
-- TRAIN SCHEDULE DATA
-- Generates schedules for all trains for the next 30 days
-- from 2026-05-10 to 2026-06-08
-- available_seats initialized to train's total_seats
-- ================================================================

INSERT INTO train_schedule (train_id, journey_date, available_seats)
SELECT t.train_id, d.journey_date, t.total_seats
FROM trains t
JOIN (
    SELECT DATE_ADD('2026-05-10', INTERVAL seq DAY) AS journey_date
    FROM (
        SELECT 0 AS seq UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4
        UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9
        UNION SELECT 10 UNION SELECT 11 UNION SELECT 12 UNION SELECT 13 UNION SELECT 14
        UNION SELECT 15 UNION SELECT 16 UNION SELECT 17 UNION SELECT 18 UNION SELECT 19
        UNION SELECT 20 UNION SELECT 21 UNION SELECT 22 UNION SELECT 23 UNION SELECT 24
        UNION SELECT 25 UNION SELECT 26 UNION SELECT 27 UNION SELECT 28 UNION SELECT 29
    ) AS seq_table
) AS d
ON 1=1;