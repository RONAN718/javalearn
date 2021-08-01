CREATE TABLE `customer` (
  `customer_id` bigint NOT NULL COMMENT '顾客id',
  `customer_nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '顾客用户名',
  `customer_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '顾客密码',
  `customer_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '顾客邮箱号',
  `customer_telephone` bigint DEFAULT NULL COMMENT '顾客电话号码',
  `customer_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '顾客地址',
  `is_deleted` char(1) DEFAULT NULL COMMENT '删除标识符',
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `category` (
  `category_id` bigint NOT NULL COMMENT '种类id',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '种类名',
  `is_deleted` char(1) DEFAULT NULL COMMENT '删除标识符',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `order` (
  `order_id` bigint NOT NULL COMMENT '订单号',
  `custmer_id` bigint DEFAULT NULL COMMENT '客户id',
  `created_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
  `order_state` int DEFAULT NULL COMMENT '订单的状态',
  `order_amount_total` float(10,2) DEFAULT NULL COMMENT '订单的总金额',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order_detail` (
  `order_detail_no` bigint NOT NULL COMMENT '自动编号',
  `order_detail_id` bigint DEFAULT NULL COMMENT '订单详情编号',
  `product_id` bigint DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品单价',
  `product_number` int DEFAULT NULL COMMENT '商品个数',
  `discount_rate` tinyint DEFAULT NULL COMMENT '折扣比例',
  `subtotal` decimal(15,2) DEFAULT NULL COMMENT '小计金额',
  `is_product_exists` char(1) DEFAULT NULL COMMENT '商品是否有效',
  PRIMARY KEY (`order_detail_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `product` (
  `product_id` bigint NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_price` float(10,2) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;