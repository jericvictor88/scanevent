SET foreign_key_checks = 0;
CREATE TABLE `cartonEvt` (
  `enterpriseId` int(11) NOT NULL,
  `courierType` varchar(32) NOT NULL DEFAULT '',
  `label` varchar(32) NOT NULL,
  `event` varchar(128) NOT NULL DEFAULT '',
  `event_time` timestamp NULL DEFAULT NULL,
  `event_desc` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`enterpriseId`,`courierType`,`label`,`event`),
  KEY `label` (`enterpriseId`,`label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8