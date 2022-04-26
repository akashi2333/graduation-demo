/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 5.7.35-log : Database - ckmp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ckmp` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `ckmp`;

/*Table structure for table `chat` */

DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
  `sid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `send_date` datetime DEFAULT NULL,
  `send_all` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `idea` */

DROP TABLE IF EXISTS `idea`;

CREATE TABLE `idea` (
  `id` int(11) DEFAULT NULL,
  `idea` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `img` mediumblob,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `project_exit` */

DROP TABLE IF EXISTS `project_exit`;

CREATE TABLE `project_exit` (
  `pid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `project_join` */

DROP TABLE IF EXISTS `project_join`;

CREATE TABLE `project_join` (
  `pid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `project_resource` */

DROP TABLE IF EXISTS `project_resource`;

CREATE TABLE `project_resource` (
  `pid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `resource_name` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `project_task` */

DROP TABLE IF EXISTS `project_task`;

CREATE TABLE `project_task` (
  `pid` int(11) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `begin` timestamp NOT NULL DEFAULT '2020-01-01 01:01:11' ON UPDATE CURRENT_TIMESTAMP,
  `end` timestamp NOT NULL DEFAULT '2020-01-01 01:01:12',
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `project_task_member` */

DROP TABLE IF EXISTS `project_task_member`;

CREATE TABLE `project_task_member` (
  `tid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `team` */

DROP TABLE IF EXISTS `team`;

CREATE TABLE `team` (
  `tid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `isowner` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `brief` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `img` mediumblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `team_exit` */

DROP TABLE IF EXISTS `team_exit`;

CREATE TABLE `team_exit` (
  `tid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `team_join` */

DROP TABLE IF EXISTS `team_join`;

CREATE TABLE `team_join` (
  `tid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `team_resource` */

DROP TABLE IF EXISTS `team_resource`;

CREATE TABLE `team_resource` (
  `tid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `resource_name` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `todo` */

DROP TABLE IF EXISTS `todo`;

CREATE TABLE `todo` (
  `id` int(11) DEFAULT NULL,
  `todo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `password` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `e_mail` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `role` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `user_project` */

DROP TABLE IF EXISTS `user_project`;

CREATE TABLE `user_project` (
  `uid` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
