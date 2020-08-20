CREATE TABLE cronjob_log
(
    log_id serial NOT NULL,
    cronjob_name text NOT NULL,
    start_dtime timestamp with time zone NOT NULL,
    end_dtime timestamp with time zone NOT NULL,
    exit_status text NOT NULL,
    PRIMARY KEY (log_id)
);