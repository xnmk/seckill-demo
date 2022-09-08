/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : seckill

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 08/09/2022 21:14:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_order`;
CREATE TABLE `t_seckill_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint(11) NULL DEFAULT NULL COMMENT '用户ID',
  `order_id` int(11) NULL DEFAULT NULL COMMENT '订单ID',
  `goods_id` int(11) NULL DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `seckill_uid_gid`(`user_id`, `goods_id`) USING BTREE COMMENT '用户id+商品id的唯一索引，解决同一个用户秒杀多商品'
) ENGINE = InnoDB AUTO_INCREMENT = 377 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_seckill_order
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
