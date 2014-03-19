/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

INSERT INTO `Customer` (`id`, `first_name`, `last_name`, `email_address`) VALUES (1, 'John', 'Doe', 'john@doe.com');
INSERT INTO `Customer` (`id`, `first_name`, `last_name`, `email_address`) VALUES (2, 'Jane', 'Doe', 'jane@doe.com');
INSERT INTO `Customer` (`id`, `first_name`, `last_name`, `email_address`) VALUES (3, 'Bob', 'Doe', 'bob@doe.com');

INSERT INTO `Address` (`id`, `customer_id`, `street`, `city`, `country`) VALUES (1, 1, '6 Main St', 'Newtown', 'USA');
INSERT INTO `Address` (`id`, `customer_id`, `street`, `city`, `country`) VALUES (2, 1, '128 N. South St', 'Middletown', 'USA');
INSERT INTO `Address` (`id`, `customer_id`, `street`, `city`, `country`) VALUES (3, 3, '512 North St', 'London', 'UK');


/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
