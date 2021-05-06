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
