CREATE TABLE `suppliers`
(
    `id`          INT(10) NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(500) NOT NULL DEFAULT '0' COLLATE 'utf8mb4_unicode_ci',
    `description` VARCHAR(500) NOT NULL DEFAULT '0' COLLATE 'utf8mb4_unicode_ci',
    PRIMARY KEY (`id`) USING BTREE
) ;
INSERT INTO suppliers VALUES (1,'Amazon', 'Digital content and services');
INSERT INTO suppliers  VALUES (2, 'Lenovo', 'Computers');
