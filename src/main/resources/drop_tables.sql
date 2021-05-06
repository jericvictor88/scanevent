SET foreign_key_checks = 0;

drop table batch_repo.BATCH_JOB_EXECUTION;
drop table batch_repo.BATCH_JOB_EXECUTION_CONTEXT;
drop table batch_repo.BATCH_JOB_EXECUTION_PARAMS;
drop table batch_repo.BATCH_JOB_EXECUTION_SEQ;
drop table batch_repo.BATCH_JOB_INSTANCE;
drop table batch_repo.BATCH_JOB_SEQ;
drop table batch_repo.BATCH_STEP_EXECUTION;
drop table batch_repo.BATCH_STEP_EXECUTION_CONTEXT;
drop table batch_repo.BATCH_STEP_EXECUTION_SEQ;

SET foreign_key_checks = 1;

drop database if exists batch_repo;
create database batch_repo;

truncate table mydb.cartonEvt;
truncate table mydb.cartonEvtExt;

CREATE TABLE IF NOT EXISTS `mydb`.`cartonEvt` (
  `label` varchar(32) NOT NULL DEFAULT '',
  `courier` varchar(32) NOT NULL DEFAULT '',
  `status` varchar(32) NOT NULL DEFAULT '',
  `date` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `mydb`.`cartonEvtExt` (
  `label` varchar(32) NOT NULL DEFAULT '',
  `courier` varchar(32) NOT NULL DEFAULT '',
  `status` varchar(32) NOT NULL DEFAULT '',
  `date` timestamp NULL DEFAULT NULL,
  `isFinalEvent` TINYINT (1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8