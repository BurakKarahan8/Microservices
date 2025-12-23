CREATE DATABASE IF NOT EXISTS `order-service`;
CREATE DATABASE IF NOT EXISTS `inventory-service`;
CREATE DATABASE IF NOT EXISTS `identity-service`;

GRANT ALL PRIVILEGES ON `order-service`.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON `inventory-service`.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON `identity-service`.* TO 'root'@'%';

FLUSH PRIVILEGES;