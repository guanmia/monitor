CREATE TABLE `client_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL,
  `client_host_name` varchar(45) DEFAULT NULL,
  `client_host_ip` varchar(45) DEFAULT NULL,
  `region_center_id` int(11) NOT NULL,
  `location_id` varchar(32) DEFAULT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  `comments` varchar(200) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `created_ts` datetime(6) DEFAULT NULL,
  `modified_by` varchar(45) DEFAULT NULL,
  `modified_ts` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `client_config_fk1_idx` (`location_id`),
  KEY `client_config_fk2_idx` (`region_center_id`),
  CONSTRAINT `client_config_fk1` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `client_config_fk2` FOREIGN KEY (`region_center_id`) REFERENCES `region_center` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8