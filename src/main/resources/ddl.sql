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