/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : beauty

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2018-04-29 09:10:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for broker
-- ----------------------------
DROP TABLE IF EXISTS `broker`;
CREATE TABLE `broker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client` int(11) DEFAULT NULL COMMENT '客户',
  `puller` int(11) NOT NULL COMMENT '开户人',
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of broker
-- ----------------------------

-- ----------------------------
-- Table structure for buygoods
-- ----------------------------
DROP TABLE IF EXISTS `buygoods`;
CREATE TABLE `buygoods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsId` int(11) NOT NULL COMMENT '产品ID',
  `price` float(10,2) NOT NULL COMMENT '产品价格',
  `goodsSnapshot` varchar(1024) NOT NULL COMMENT '购买产品快照',
  `createDate` datetime NOT NULL COMMENT '购买时间',
  `num` int(11) NOT NULL COMMENT '购买数量',
  `state` int(1) NOT NULL COMMENT '状态',
  `updateDate` datetime DEFAULT NULL,
  `user_Id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of buygoods
-- ----------------------------

-- ----------------------------
-- Table structure for carerecord
-- ----------------------------
DROP TABLE IF EXISTS `carerecord`;
CREATE TABLE `carerecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) DEFAULT NULL COMMENT '客户',
  `userId` int(11) DEFAULT NULL COMMENT '技师',
  `goodsId` int(11) DEFAULT NULL COMMENT '使用产品IDs',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of carerecord
-- ----------------------------

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `price` float(10,2) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别',
  `address` varchar(255) DEFAULT NULL COMMENT '住址',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系方式',
  `userId` int(11) DEFAULT NULL COMMENT '账号ID',
  `type` int(11) DEFAULT NULL COMMENT '用户类别  0 管理员 1用户',
  `demand` varchar(255) DEFAULT NULL COMMENT '客户需求',
  `personDesc` varchar(255) DEFAULT NULL COMMENT '客户描述',
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `lastCareDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('15', '超级管理员', null, null, null, null, '14', '0', null, null, null, null, null);
INSERT INTO `person` VALUES ('16', '测试', '18', '女', '鹿邑', '123456789', '15', '0', null, null, '2018-04-28 20:01:06', null, null);

-- ----------------------------
-- Table structure for sys
-- ----------------------------
DROP TABLE IF EXISTS `sys`;
CREATE TABLE `sys` (
  `id` int(11) NOT NULL,
  `key` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys
-- ----------------------------
INSERT INTO `sys` VALUES ('1', 'indexStatistics', '7', '2018-04-28 18:30:47', '2018-04-28 18:43:01');
INSERT INTO `sys` VALUES ('2', 'dontCareByDay', '7', '2018-04-28 18:30:50', '2018-04-28 18:43:01');
INSERT INTO `sys` VALUES ('3', 'inventoryWarning', '2', '2018-04-28 18:30:54', '2018-04-28 18:43:01');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '账号状态',
  `createDate` datetime DEFAULT NULL,
  `lastLoginDate` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('14', 'admin', 'admin', '0', '2018-04-28 18:57:28', '2018-04-29 09:03:36');
INSERT INTO `user` VALUES ('15', '123', '123', '1', '2018-04-28 20:01:06', '2018-04-28 20:01:52');
