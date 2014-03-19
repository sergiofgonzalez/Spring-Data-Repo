/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

INSERT INTO `Customer` (`id`, `email`, `firstname`, `lastname`) VALUES (1, 'dave@dmband.com', 'Dave', 'Matthews');
INSERT INTO `Customer` (`id`, `email`, `firstname`, `lastname`) VALUES (2, 'carter@dmband.com', 'Carter', 'Beauford');
INSERT INTO `Customer` (`id`, `email`, `firstname`, `lastname`) VALUES (3, 'boyd@dmband.com', 'Boyd', 'Tinsley');

INSERT INTO `Address` (`id`, `street`, `city`, `country`, `customer_id`) VALUES (1, '27 Broadway', 'New York', 'United States', 1);
INSERT INTO `Address` (`id`, `street`, `city`, `country`, `customer_id`) VALUES (2, '27 Broadway', 'New York', 'United States', 1);

INSERT INTO `Product` (`id`, `name`, `description`, `price`) VALUES (1, 'iPad', 'Apple tablet device', 499.0);
INSERT INTO `Product` (`id`, `name`, `description`, `price`) VALUES (2, 'MacBook Pro', 'Apple notebook', 1299.0);
INSERT INTO `Product` (`id`, `name`, `description`, `price`) VALUES (3, 'Dock', 'Dock for iPhone/iPad', 49.0);

INSERT INTO `Product_Attributes` (`attributes_key`, `product_id`, `attributes`) VALUES ('connector', 1, 'socket');
INSERT INTO `Product_Attributes` (`attributes_key`, `product_id`, `attributes`) VALUES ('connector', 3, 'plug');

INSERT INTO `Orders` (`id`, `customer_id`, `shippingaddress_id`) VALUES (1, 1, 2);
INSERT INTO `LineItem` (`id`, `product_id`, `amount`, `order_id`, `price`) VALUES (1, 1, 2, 1, 499.0);
INSERT INTO `LineItem` (`id`, `product_id`, `amount`, `order_id`, `price`) VALUES (2, 2, 1, 1, 1299.0);

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
