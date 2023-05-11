create database iatserver_db;
use iatserver_db;
-- MariaDB dump 10.19  Distrib 10.6.7-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: iatserver_db
-- ------------------------------------------------------
-- Server version	10.6.7-MariaDB-2ubuntu1.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin_timers`
--
DROP TABLE IF EXISTS `admin_timers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_timers` (
  `timer_id` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `last_timer_refresh` datetime NOT NULL,
  `TestID` int(64) unsigned NOT NULL,
  `complete` bit(1) NOT NULL,
  `testee_token` varbinary(1024) DEFAULT NULL,
  `session_id` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`timer_id`),
  KEY `timers_test` (`TestID`),
  CONSTRAINT `timers_test` FOREIGN KEY (`TestID`) REFERENCES `tests` (`TestID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1690 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `client_exception`
--

DROP TABLE IF EXISTS `client_exception`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_exception` (
  `ExceptionID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `UserID` int(64) unsigned NOT NULL,
  `ClientID` int(64) unsigned NOT NULL,
  `exception_timestamp` datetime NOT NULL,
  `exception_xml` text CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`ExceptionID`),
  KEY `exceptions_client` (`ClientID`),
  KEY `exceptions_user` (`UserID`),
  CONSTRAINT `exceptions_client` FOREIGN KEY (`ClientID`) REFERENCES `clients` (`ClientID`) ON DELETE CASCADE,
  CONSTRAINT `exceptions_user` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1440 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients` (
  `ClientID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `product_key` char(20) CHARACTER SET latin1 NOT NULL,
  `activations_remaining` int(16) unsigned DEFAULT NULL,
  `activations_consumed` int(16) NOT NULL,
  `contact_fname` varchar(80) CHARACTER SET latin1 NOT NULL,
  `contact_lname` varchar(80) CHARACTER SET latin1 NOT NULL,
  `organization` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `organization_id` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `email` varchar(255) CHARACTER SET latin1 NOT NULL,
  `phone` varchar(25) CHARACTER SET latin1 DEFAULT NULL,
  `province` varchar(80) CHARACTER SET latin1 DEFAULT NULL,
  `streetaddress1` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `streetaddress2` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `city` varchar(80) CHARACTER SET latin1 DEFAULT NULL,
  `postalcode` varchar(25) CHARACTER SET latin1 DEFAULT NULL,
  `country` varchar(80) CHARACTER SET latin1 DEFAULT NULL,
  `registration_date` datetime DEFAULT NULL,
  `disk_alottment_mb` int(16) unsigned DEFAULT NULL,
  `num_iats_alotted` int(16) unsigned DEFAULT NULL,
  `administrations` int(32) unsigned NOT NULL,
  `administrations_remaining` int(32) unsigned DEFAULT NULL,
  `frozen` bit(1) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `kill_filed` bit(1) NOT NULL,
  `invalid_save_files` int(16) NOT NULL,
  `isolate_users` bit(1) NOT NULL,
  `product_use` text CHARACTER SET latin1 DEFAULT NULL,
  `download_password` char(20) CHARACTER SET latin1 DEFAULT NULL,
  `downloads_remaining` int(16) DEFAULT NULL,
  `downloads_consumed` int(16) NOT NULL,
  `oauth_access_expires` datetime DEFAULT NULL,
  PRIMARY KEY (`ClientID`),
  UNIQUE KEY `unique_email` (`email`),
  KEY `clients_product_keys` (`product_key`)
) ENGINE=InnoDB AUTO_INCREMENT=892 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cors_origins`
--

DROP TABLE IF EXISTS `cors_origins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cors_origins` (
  `OriginID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `origin` varchar(512) COLLATE utf8mb3_unicode_ci NOT NULL,
  `http` bit(1) NOT NULL,
  `ClientID` int(64) unsigned NOT NULL,
  PRIMARY KEY (`OriginID`),
  KEY `cors_origins_client` (`ClientID`),
  CONSTRAINT `cors_origins_client` FOREIGN KEY (`ClientID`) REFERENCES `clients` (`ClientID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `country_codes`
--

DROP TABLE IF EXISTS `country_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country_codes` (
  `CodeID` int(16) unsigned NOT NULL AUTO_INCREMENT,
  `code` char(2) CHARACTER SET latin1 NOT NULL,
  `country` varchar(80) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`CodeID`)
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deployment_packets`
--

DROP TABLE IF EXISTS `deployment_packets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deployment_packets` (
  `DeploymentPacketID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `DeploymentSessionID` int(64) unsigned NOT NULL,
  `packet_data` mediumblob NOT NULL,
  `upload_ordinal` int(32) unsigned NOT NULL,
  `packet_type` enum('DEPLOYMENT_DATA','ITEM_SLIDE') CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`DeploymentPacketID`),
  KEY `tests_deployment` (`DeploymentSessionID`),
  CONSTRAINT `packets_deployment_session` FOREIGN KEY (`DeploymentSessionID`) REFERENCES `deployment_sessions` (`SessionID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deployment_sessions`
--

DROP TABLE IF EXISTS `deployment_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deployment_sessions` (
  `SessionID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `deployment_start` datetime NOT NULL,
  `item_slide_upload_key` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `reconnection_key` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `web_socket_id` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `deployment_upload_key` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `TestID` int(64) unsigned NOT NULL,
  PRIMARY KEY (`SessionID`),
  KEY `deployments_test` (`TestID`),
  CONSTRAINT `deployments_test` FOREIGN KEY (`TestID`) REFERENCES `tests` (`TestID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `downloads`
--

DROP TABLE IF EXISTS `downloads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `downloads` (
  `client_id` int(64) unsigned NOT NULL,
  `download_password` char(20) CHARACTER SET latin1 NOT NULL,
  `downloads_remaining` int(16) NOT NULL,
  `downloads_consumed` int(16) NOT NULL,
  PRIMARY KEY (`client_id`),
  CONSTRAINT `downloads_client` FOREIGN KEY (`client_id`) REFERENCES `clients` (`ClientID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dynamic_specifiers`
--

DROP TABLE IF EXISTS `dynamic_specifiers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dynamic_specifiers` (
  `SpecifierID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `TestID` int(64) unsigned NOT NULL,
  `TestSegmentID` int(64) unsigned NOT NULL,
  `test_specifier_id` int(16) unsigned NOT NULL,
  `item_num` int(16) unsigned NOT NULL,
  `SpecifierType` enum('Mask','TrueFalse','Selection','Range') CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`SpecifierID`),
  KEY `specifiers_test_segment` (`TestSegmentID`),
  KEY `specifiers_tests_id` (`TestID`),
  CONSTRAINT `specifiers_test` FOREIGN KEY (`TestID`) REFERENCES `tests` (`TestID`) ON DELETE CASCADE,
  CONSTRAINT `specifiers_test_segment` FOREIGN KEY (`TestSegmentID`) REFERENCES `test_segments` (`TestSegmentID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1147 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_slides`
--

DROP TABLE IF EXISTS `item_slides`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_slides` (
  `SlideID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `TestID` int(64) unsigned NOT NULL,
  `file_name` varchar(255) CHARACTER SET latin1 NOT NULL,
  `slide_num` int(16) NOT NULL,
  `slide_size` int(64) NOT NULL,
  `image_data` mediumblob NOT NULL,
  PRIMARY KEY (`SlideID`),
  KEY `items_test_ndx` (`TestID`),
  CONSTRAINT `slides_test` FOREIGN KEY (`TestID`) REFERENCES `tests` (`TestID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=70451 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manifest_files`
--

DROP TABLE IF EXISTS `manifest_files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manifest_files` (
  `ManifestFileID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `SessionID` int(64) unsigned NOT NULL,
  `file_name` varchar(80) CHARACTER SET latin1 NOT NULL,
  `file_size` int(64) NOT NULL,
  `file_path` varchar(2500) CHARACTER SET latin1 NOT NULL,
  `transmission_order` int(32) unsigned NOT NULL,
  `file_type` enum('ITEM_SLIDES','DEPLOYMENT_FILES') CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`ManifestFileID`),
  KEY `files_deployment_session` (`SessionID`),
  CONSTRAINT `files_session` FOREIGN KEY (`SessionID`) REFERENCES `deployment_sessions` (`SessionID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mask_specifiers`
--

DROP TABLE IF EXISTS `mask_specifiers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mask_specifiers` (
  `SpecifierID` int(64) unsigned NOT NULL,
  `mask` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`SpecifierID`),
  CONSTRAINT `mask_spec_fk` FOREIGN KEY (`SpecifierID`) REFERENCES `dynamic_specifiers` (`SpecifierID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_access`
--

DROP TABLE IF EXISTS `oauth_access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_access` (
  `AccessID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `ClientID` int(64) unsigned NOT NULL,
  `TestID` int(64) unsigned NOT NULL,
  `access_token` char(40) CHARACTER SET latin1 NOT NULL,
  `refresh_token` char(40) CHARACTER SET latin1 NOT NULL,
  `auth_token` char(40) CHARACTER SET latin1 NOT NULL,
  `access_expires` datetime NOT NULL,
  `refresh_expires` datetime NOT NULL,
  `auth_token_consumed` bit(1) NOT NULL,
  PRIMARY KEY (`AccessID`),
  KEY `oauths_client` (`ClientID`),
  KEY `oauths_test` (`TestID`),
  KEY `oauth_access_index` (`access_token`),
  CONSTRAINT `oauths_client` FOREIGN KEY (`ClientID`) REFERENCES `clients` (`ClientID`) ON DELETE CASCADE,
  CONSTRAINT `oauths_test` FOREIGN KEY (`TestID`) REFERENCES `tests` (`TestID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchases`
--

DROP TABLE IF EXISTS `purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchases` (
  `PurchaseID` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `purchase_time` datetime NOT NULL,
  `ClientID` int(64) unsigned NOT NULL,
  `num_administrations` int(16) unsigned NOT NULL,
  `administrations_price` int(16) unsigned NOT NULL,
  `num_tests` int(16) unsigned NOT NULL,
  `tests_price` int(16) unsigned NOT NULL,
  `disk_space` int(16) unsigned NOT NULL,
  `disk_space_price` int(16) unsigned NOT NULL,
  `total` int(16) unsigned NOT NULL,
  `credit_card` varchar(20) CHARACTER SET latin1 NOT NULL,
  `ending_card_digits` char(4) CHARACTER SET latin1 NOT NULL,
  `cardholder_fname` varchar(255) CHARACTER SET latin1 NOT NULL,
  `cardholder_lname` varchar(255) CHARACTER SET latin1 NOT NULL,
  `card_exp_month` int(16) unsigned NOT NULL,
  `card_exp_year` int(16) unsigned NOT NULL,
  PRIMARY KEY (`PurchaseID`),
  KEY `purchases_client_ndx` (`ClientID`),
  CONSTRAINT `purchases_client` FOREIGN KEY (`ClientID`) REFERENCES `clients` (`ClientID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `range_specifiers`
--

DROP TABLE IF EXISTS `range_specifiers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `range_specifiers` (
  `SpecifierID` int(64) unsigned NOT NULL,
  `cutoff` int(16) unsigned NOT NULL,
  `reverse_scored` bit(1) NOT NULL,
  `cutoff_excludes` bit(1) NOT NULL,
  PRIMARY KEY (`SpecifierID`),
  CONSTRAINT `range_spec_fk` FOREIGN KEY (`SpecifierID`) REFERENCES `dynamic_specifiers` (`SpecifierID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requests` (
  `request_id` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `fname` varchar(80) CHARACTER SET latin1 NOT NULL,
  `lname` varchar(80) CHARACTER SET latin1 NOT NULL,
  `organization` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `email` varchar(255) CHARACTER SET latin1 NOT NULL,
  `phone` varchar(25) CHARACTER SET latin1 NOT NULL,
  `address1` varchar(255) CHARACTER SET latin1 NOT NULL,
  `address2` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `city` varchar(255) CHARACTER SET latin1 NOT NULL,
  `province` varchar(255) CHARACTER SET latin1 NOT NULL,
  `postalcode` varchar(25) CHARACTER SET latin1 NOT NULL,
  `country` varchar(80) CHARACTER SET latin1 NOT NULL,
  `product_use` text CHARACTER SET latin1 NOT NULL,
  `registration_timestamp` datetime NOT NULL,
  `delete_flag` bit(1) NOT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=313 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resource_prices`
--

DROP TABLE IF EXISTS `resource_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource_prices` (
  `ResourcePriceID` int(16) unsigned NOT NULL AUTO_INCREMENT,
  `resource_type` enum('ADMINISTRATION','DISK_SPACE','IAT_WITH_10_MB') NOT NULL,
  `quantity` int(16) unsigned NOT NULL,
  `price` int(16) unsigned NOT NULL,
  PRIMARY KEY (`ResourcePriceID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resource_references`
--

DROP TABLE IF EXISTS `resource_references`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource_references` (
  `id` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `reference_id` int(16) unsigned NOT NULL,
  `TestResourceID` int(64) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `references_resource` (`TestResourceID`),
  CONSTRAINT `references_resource` FOREIGN KEY (`TestResourceID`) REFERENCES `test_resources` (`TestResourceID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13000 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `results`
--

DROP TABLE IF EXISTS `results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `results` (
  `ResultID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `TestID` int(64) unsigned NOT NULL,
  `admin_time` datetime NOT NULL,
  `results` mediumblob NOT NULL,
  `toc` text NOT NULL,
  `testee_token` varbinary(1024) DEFAULT NULL,
  PRIMARY KEY (`ResultID`),
  KEY `results_tests_id` (`TestID`),
  CONSTRAINT `results_test` FOREIGN KEY (`TestID`) REFERENCES `tests` (`TestID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6582 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rsa_key_data`
--

DROP TABLE IF EXISTS `rsa_key_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rsa_key_data` (
  `KeyID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `priv_key` int(64) unsigned NOT NULL,
  `pub_key` int(64) unsigned NOT NULL,
  `modulus` int(64) unsigned NOT NULL,
  `ordinal` int(16) unsigned NOT NULL,
  PRIMARY KEY (`KeyID`),
  KEY `datas_ndx` (`ordinal`)
) ENGINE=InnoDB AUTO_INCREMENT=4231 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `selection_specifiers`
--

DROP TABLE IF EXISTS `selection_specifiers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `selection_specifiers` (
  `SpecifierID` int(64) unsigned NOT NULL,
  `key_specifiers` varchar(2500) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`SpecifierID`),
  CONSTRAINT `selection_spec_fk` FOREIGN KEY (`SpecifierID`) REFERENCES `dynamic_specifiers` (`SpecifierID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `specifier_values`
--

DROP TABLE IF EXISTS `specifier_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `specifier_values` (
  `SpecifierValueID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `AdminID` int(64) unsigned NOT NULL,
  `specifier_value` varchar(255) CHARACTER SET latin1 NOT NULL,
  `test_specifier_id` int(16) NOT NULL,
  PRIMARY KEY (`SpecifierValueID`),
  KEY `specifier_values_admin_ndx` (`AdminID`),
  CONSTRAINT `specifier_values_admin` FOREIGN KEY (`AdminID`) REFERENCES `admin_timers` (`timer_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test_backup_files`
--

DROP TABLE IF EXISTS `test_backup_files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_backup_files` (
  `ManifestFileID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `TestID` int(64) unsigned NOT NULL,
  `file_name` varchar(2500) CHARACTER SET latin1 NOT NULL,
  `DeploymentSessionID` int(64) unsigned NOT NULL,
  `file_data` mediumblob NOT NULL,
  PRIMARY KEY (`ManifestFileID`),
  KEY `backups_test` (`TestID`),
  KEY `backups_deployment_session` (`DeploymentSessionID`),
  CONSTRAINT `backups_deployment_session` FOREIGN KEY (`DeploymentSessionID`) REFERENCES `deployment_sessions` (`SessionID`) ON DELETE CASCADE,
  CONSTRAINT `backups_test` FOREIGN KEY (`TestID`) REFERENCES `tests` (`TestID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=729 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test_code`
--

DROP TABLE IF EXISTS `test_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_code` (
  `TestCodeID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `TestSegmentID` int(64) unsigned NOT NULL,
  `entity_name` varchar(255) CHARACTER SET latin1 NOT NULL,
  `andx` int(16) unsigned NOT NULL,
  `bndx` int(16) unsigned NOT NULL,
  `cl` int(16) unsigned NOT NULL,
  `code_type` enum('CONSTRUCTOR','DECLARATION','CODE','GLOBAL_DECLARATION','GLOBAL_CODE','TOC') CHARACTER SET latin1 NOT NULL,
  `code1` text CHARACTER SET latin1 NOT NULL,
  `code2` text CHARACTER SET latin1 NOT NULL,
  `code3` text CHARACTER SET latin1 NOT NULL,
  `code4` text CHARACTER SET latin1 NOT NULL,
  `ordinal` int(16) NOT NULL,
  PRIMARY KEY (`TestCodeID`),
  KEY `codes_index` (`TestSegmentID`),
  CONSTRAINT `codes_segment` FOREIGN KEY (`TestSegmentID`) REFERENCES `test_segments` (`TestSegmentID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=203427 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test_encryption_keys`
--

DROP TABLE IF EXISTS `test_encryption_keys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_encryption_keys` (
  `KeyID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `TestID` int(64) unsigned DEFAULT NULL,
  `keytype` enum('DATA','ADMIN','NONE') CHARACTER SET latin1 NOT NULL,
  `modulus` varbinary(255) NOT NULL,
  `exponent` varbinary(255) NOT NULL,
  `encrypted_key` varbinary(2500) NOT NULL,
  PRIMARY KEY (`KeyID`),
  KEY `encryption_keys_test_id` (`TestID`),
  CONSTRAINT `encryption_keys_test` FOREIGN KEY (`TestID`) REFERENCES `tests` (`TestID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2719 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test_resources`
--

DROP TABLE IF EXISTS `test_resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_resources` (
  `TestResourceID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `path` varchar(1024) NOT NULL,
  `mimetype` varchar(32) NOT NULL,
  `size` int(32) unsigned NOT NULL,
  `resource_type` enum('DEPLOYMENT_FILE','DEPLOYMENT_IMAGE','ITEM_SLIDE','UPDATE_FILE') NOT NULL,
  `TestID` int(64) unsigned NOT NULL,
  `resource_data` mediumblob DEFAULT NULL,
  `resource_id` int(16) unsigned NOT NULL,
  PRIMARY KEY (`TestResourceID`),
  KEY `resources_test` (`TestID`),
  CONSTRAINT `resources_test` FOREIGN KEY (`TestID`) REFERENCES `tests` (`TestID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1905 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test_result_fragments`
--

DROP TABLE IF EXISTS `test_result_fragments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_result_fragments` (
  `ResultFragmentID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `TestAdminID` int(64) unsigned NOT NULL,
  `ordinal` int(16) NOT NULL,
  `result_fragment` blob NOT NULL,
  `cipher` varbinary(256) NOT NULL,
  `iv` varbinary(256) NOT NULL,
  PRIMARY KEY (`ResultFragmentID`),
  KEY `admin_id` (`TestAdminID`),
  CONSTRAINT `fragments_result` FOREIGN KEY (`TestAdminID`) REFERENCES `admin_timers` (`timer_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=905 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test_segments`
--

DROP TABLE IF EXISTS `test_segments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_segments` (
  `TestSegmentID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `TestID` int(64) unsigned NOT NULL,
  `elem_name` varchar(255) CHARACTER SET latin1 NOT NULL,
  `jskeys_xml` text CHARACTER SET latin1 NOT NULL,
  `html` mediumtext COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `alternation_priority` int(16) NOT NULL,
  `initial_pos` int(16) NOT NULL,
  `num_alternations` int(16) NOT NULL,
  `iat` bit(1) NOT NULL,
  `html_resource_id` int(64) unsigned DEFAULT NULL,
  `header_resource_id` int(64) unsigned DEFAULT NULL,
  `script_resource_id` int(64) unsigned DEFAULT NULL,
  PRIMARY KEY (`TestSegmentID`),
  KEY `segments_test_id` (`TestID`),
  KEY `test_segments_header_resource` (`header_resource_id`),
  KEY `test_segments_html_resource` (`html_resource_id`),
  KEY `test_segments_script_resource` (`script_resource_id`),
  CONSTRAINT `segments_test` FOREIGN KEY (`TestID`) REFERENCES `tests` (`TestID`) ON DELETE CASCADE,
  CONSTRAINT `test_segments_header_resource` FOREIGN KEY (`header_resource_id`) REFERENCES `test_resources` (`TestResourceID`) ON DELETE CASCADE,
  CONSTRAINT `test_segments_html_resource` FOREIGN KEY (`html_resource_id`) REFERENCES `test_resources` (`TestResourceID`) ON DELETE CASCADE,
  CONSTRAINT `test_segments_script_resource` FOREIGN KEY (`script_resource_id`) REFERENCES `test_resources` (`TestResourceID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1652 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tests`
--

DROP TABLE IF EXISTS `tests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tests` (
  `TestID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` int(64) unsigned NOT NULL,
  `test_name` varchar(255) CHARACTER SET latin1 NOT NULL,
  `user_id` int(64) unsigned NOT NULL,
  `num_administrations` int(16) unsigned NOT NULL,
  `test_size_kb` int(16) DEFAULT NULL,
  `test_type` enum('None','RandomOrder','SetNumberOfPresentations') CHARACTER SET latin1 NOT NULL,
  `upload_timestamp` datetime NOT NULL,
  `last_data_retrieval` datetime DEFAULT NULL,
  `version` varchar(16) CHARACTER SET latin1 NOT NULL,
  `result_format` int(16) unsigned NOT NULL,
  `alternate` bit(1) DEFAULT NULL,
  `alternated` bit(1) NOT NULL,
  `aes_code` text CHARACTER SET latin1 DEFAULT NULL,
  `deployment_descriptor` blob DEFAULT NULL,
  `redirect_on_complete` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `num_elems` int(16) DEFAULT NULL,
  `result_retrieval_token` binary(64) DEFAULT NULL,
  `result_retrieval_token_age` datetime DEFAULT NULL,
  `item_slide_download_key` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `url` varchar(300) CHARACTER SET latin1 DEFAULT NULL,
  `oauth_client_redirect` varchar(511) CHARACTER SET latin1 DEFAULT NULL,
  `oauth_subpath_redirects` bit(1) NOT NULL,
  `oauth_client_id` varchar(80) CHARACTER SET latin1 DEFAULT NULL,
  `oauth_client_secret` varchar(80) CHARACTER SET latin1 DEFAULT NULL,
  `token_type` enum('NONE','VALUE','HEX','BASE_64','BASE_64_UTF_8') CHARACTER SET latin1 NOT NULL,
  `token_name` varchar(80) CHARACTER SET latin1 DEFAULT NULL,
  `redeployed` bit(1) NOT NULL,
  `manifest` mediumtext COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`TestID`),
  KEY `tests_user` (`user_id`),
  KEY `tests` (`client_id`,`test_name`),
  CONSTRAINT `tests_client` FOREIGN KEY (`client_id`) REFERENCES `clients` (`ClientID`) ON DELETE CASCADE,
  CONSTRAINT `tests_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`UserID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1037 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `true_false_specifiers`
--

DROP TABLE IF EXISTS `true_false_specifiers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `true_false_specifiers` (
  `SpecifierID` int(64) unsigned NOT NULL,
  PRIMARY KEY (`SpecifierID`),
  CONSTRAINT `tf_spec_fk` FOREIGN KEY (`SpecifierID`) REFERENCES `dynamic_specifiers` (`SpecifierID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `unique_response_items`
--

DROP TABLE IF EXISTS `unique_response_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unique_response_items` (
  `UniqueResponseItemID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `TestID` int(64) unsigned NOT NULL,
  `additive` bit(1) NOT NULL,
  `survey_name` varchar(255) CHARACTER SET latin1 NOT NULL,
  `item_num` int(16) NOT NULL,
  PRIMARY KEY (`UniqueResponseItemID`),
  KEY `questionnaire` (`TestID`,`survey_name`),
  CONSTRAINT `unique_response_items_test` FOREIGN KEY (`TestID`) REFERENCES `tests` (`TestID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `unique_responses`
--

DROP TABLE IF EXISTS `unique_responses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unique_responses` (
  `UniqueResponseID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `ItemID` int(64) unsigned NOT NULL,
  `val` varchar(255) CHARACTER SET latin1 NOT NULL,
  `taken` bit(1) NOT NULL,
  `consumed` bit(1) NOT NULL,
  `AdminID` int(64) unsigned DEFAULT NULL,
  PRIMARY KEY (`UniqueResponseID`),
  KEY `unique_responses_admin` (`AdminID`),
  KEY `items_id_index` (`ItemID`),
  CONSTRAINT `unique_responses_item` FOREIGN KEY (`ItemID`) REFERENCES `unique_response_items` (`UniqueResponseItemID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `UserID` int(64) unsigned NOT NULL AUTO_INCREMENT,
  `ClientID` int(64) unsigned NOT NULL,
  `user_num` int(16) unsigned NOT NULL,
  `title` varchar(25) CHARACTER SET latin1 DEFAULT NULL,
  `fname` varchar(50) CHARACTER SET latin1 NOT NULL,
  `lname` varchar(50) CHARACTER SET latin1 NOT NULL,
  `email` varchar(255) CHARACTER SET latin1 NOT NULL,
  `activation_date` datetime DEFAULT NULL,
  `activation_key` varchar(64) CHARACTER SET latin1 DEFAULT NULL,
  `verification_code` varchar(64) CHARACTER SET latin1 NOT NULL,
  `email_verified` bit(1) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `client_user` (`ClientID`,`user_num`),
  CONSTRAINT `users_client` FOREIGN KEY (`ClientID`) REFERENCES `clients` (`ClientID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=513 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-02  1:00:36
create user 'iat'@'localhost' identified by 'ezasabcdefg';
grant all on iatserver_db.* to 'iat'@'localhost';