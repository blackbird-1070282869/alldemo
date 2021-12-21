-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
                         `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                         `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
                         `count` int(11) NOT NULL COMMENT '库存',
                         `sale` int(11) NOT NULL COMMENT '已售',
                         `version` int(11) NOT NULL COMMENT '乐观锁，版本号',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for stock_order
-- ----------------------------
DROP TABLE IF EXISTS `stock_order`;
CREATE TABLE `stock_order` (
                               `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                               `sid` int(11) NOT NULL COMMENT '库存ID',
                               `name` varchar(30) NOT NULL DEFAULT '' COMMENT '商品名称',
                               `create_time` date NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO stock (`name`,count,sale,version) VALUES('iphone',100,0,0);