# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.19)
# Database: learn
# Generation Time: 2020-11-25 15:30:05 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_goods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_goods`;

CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(45) NOT NULL,
  `goods_type` varchar(45) NOT NULL,
  `goods_detail` varchar(45) DEFAULT NULL,
  `create_time` int(11) NOT NULL,
  `create_by` varchar(45) NOT NULL,
  `update_time` int(11) NOT NULL,
  `update_by` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table t_goods_spec
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_goods_spec`;

CREATE TABLE `t_goods_spec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `spec_name` varchar(45) NOT NULL,
  `unit_price` int(11) NOT NULL,
  `create_time` int(11) NOT NULL,
  `create_by` varchar(45) NOT NULL,
  `update_time` int(11) NOT NULL,
  `update_by` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格';



# Dump of table t_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderNo` varchar(45) NOT NULL,
  `user_id` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `payment_amount` int(11) NOT NULL,
  `order_status` varchar(45) NOT NULL,
  `create_time` int(11) NOT NULL,
  `create_by` varchar(45) NOT NULL,
  `update_time` int(11) NOT NULL,
  `update_by` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主表';



# Dump of table t_order_delivery
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_order_delivery`;

CREATE TABLE `t_order_delivery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(45) NOT NULL,
  `receiver` varchar(45) NOT NULL,
  `addr` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `delivery_no` varchar(45) NOT NULL,
  `delivery_status` varchar(45) DEFAULT NULL,
  `create_time` int(11) NOT NULL,
  `create_by` varchar(45) NOT NULL,
  `update_time` int(11) NOT NULL,
  `update_by` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单配送';



# Dump of table t_order_item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_order_item`;

CREATE TABLE `t_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `goods_id` int(11) NOT NULL,
  `goods_name` varchar(45) NOT NULL,
  `goods_spec_id` int(11) NOT NULL,
  `goods_spec_name` varchar(45) NOT NULL,
  `goods_detail` varchar(45) NOT NULL,
  `unit_price` int(11) NOT NULL,
  `quantity` tinyint(4) NOT NULL,
  `create_time` int(11) NOT NULL,
  `create_by` varchar(45) NOT NULL,
  `update_time` int(11) NOT NULL,
  `update_by` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细';



# Dump of table t_order_payment
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_order_payment`;

CREATE TABLE `t_order_payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `payment_no` varchar(45) NOT NULL,
  `payment_amount` int(11) NOT NULL,
  `create_time` int(11) NOT NULL,
  `create_by` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单支付记录';



# Dump of table t_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(45) NOT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `brithday` varchar(45) DEFAULT NULL,
  `icon` varchar(45) DEFAULT NULL,
  `user_status` varchar(45) NOT NULL,
  `create_time` int(11) NOT NULL,
  `create_by` varchar(45) NOT NULL,
  `udpate_time` varchar(45) NOT NULL,
  `update_by` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';



# Dump of table t_user_address
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_address`;

CREATE TABLE `t_user_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) NOT NULL,
  `receiver` varchar(45) NOT NULL,
  `addr` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `create_time` int(11) NOT NULL,
  `create_by` varchar(45) NOT NULL,
  `update_time` int(11) NOT NULL,
  `updateby_` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收货地址';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
