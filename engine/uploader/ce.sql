-- MySQL dump 10.13  Distrib 5.5.37, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: ce
-- ------------------------------------------------------
-- Server version	5.5.37-0ubuntu0.12.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Adapters`
--

DROP TABLE IF EXISTS `Adapters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Adapters` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `continent` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `lastRunOn` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Adapters`
--

LOCK TABLES `Adapters` WRITE;
/*!40000 ALTER TABLE `Adapters` DISABLE KEYS */;
INSERT INTO `Adapters` VALUES (1,'NA','US',1,'2014-141T19:29:57.011','St Timothy','../adapters/north-america/us/va/chantilly/st-timothy/','VA','Chantilly'),(2,'NA','US',1,'2014-141T19:30:21.111','St Leo the Great','../adapters/north-america/us/va/fairfax/st-leo-the-great','VA','Fairfax');
/*!40000 ALTER TABLE `Adapters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ChurchDetails`
--

DROP TABLE IF EXISTS `ChurchDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ChurchDetails` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(32) DEFAULT NULL,
  `citySlug` varchar(32) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `hours` varchar(255) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lon` double DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `nameSlug` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `stateSlug` varchar(32) DEFAULT NULL,
  `streetAddress` varchar(255) DEFAULT NULL,
  `updatedOn` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `adapter_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_121947c494bc4532af325779b85` (`adapter_id`),
  CONSTRAINT `FK_121947c494bc4532af325779b85` FOREIGN KEY (`adapter_id`) REFERENCES `Adapters` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ChurchDetails`
--

LOCK TABLES `ChurchDetails` WRITE;
/*!40000 ALTER TABLE `ChurchDetails` DISABLE KEYS */;
INSERT INTO `ChurchDetails` VALUES (16,'Chantilly','chantilly','US',NULL,NULL,38.872481,-77.424497,'St. Timothy Catholic Church','st-timothy','703-378-7646','VA',NULL,'13807 Poplar Tree Road','2014-141T19:29:56.340','http://www.sttimothyparish.org','20151',1),(17,'Fairfax','fairfax','US',NULL,NULL,38.85403,-77.296582,'St. Leo the Great','st-leo-the-great','703-273-5369','VA',NULL,'3700 Old Lee Highway','2014-141T19:30:20.549','http://www.stleofairfax.com','22033',2);
/*!40000 ALTER TABLE `ChurchDetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ChurchEvents`
--

DROP TABLE IF EXISTS `ChurchEvents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ChurchEvents` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` varchar(255) DEFAULT NULL,
  `description` text,
  `type` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `repeatType` varchar(255) DEFAULT NULL,
  `startDate` varchar(255) DEFAULT NULL,
  `startTime` varchar(255) DEFAULT NULL,
  `stopDate` varchar(255) DEFAULT NULL,
  `stopTime` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `church_detail_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7464dea47cd44de882f742a1d6f` (`church_detail_id`),
  CONSTRAINT `FK_7464dea47cd44de882f742a1d6f` FOREIGN KEY (`church_detail_id`) REFERENCES `ChurchDetails` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=676 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ChurchEvents`
--

LOCK TABLES `ChurchEvents` WRITE;
/*!40000 ALTER TABLE `ChurchEvents` DISABLE KEYS */;
INSERT INTO `ChurchEvents` VALUES (602,'SAT',NULL,'VIGIL_MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'17:30',NULL,NULL,NULL,16),(603,'SAT',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,16),(604,'SUN',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'07:30',NULL,NULL,NULL,16),(605,'SUN',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,16),(606,'SUN',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'10:45',NULL,NULL,NULL,16),(607,'SUN',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'12:30',NULL,NULL,NULL,16),(608,'SUN',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'17:30',NULL,NULL,NULL,16),(609,'MON',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'06:15',NULL,NULL,NULL,16),(610,'MON',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,16),(611,'MON',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'12:00',NULL,NULL,NULL,16),(612,'TUE',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'06:15',NULL,NULL,NULL,16),(613,'TUE',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,16),(614,'TUE',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'12:00',NULL,NULL,NULL,16),(615,'WED',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'06:15',NULL,NULL,NULL,16),(616,'WED',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,16),(617,'WED',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'12:00',NULL,NULL,NULL,16),(618,'THU',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'06:15',NULL,NULL,NULL,16),(619,'THU',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,16),(620,'THU',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'12:00',NULL,NULL,NULL,16),(621,'FRI',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'06:15',NULL,NULL,NULL,16),(622,'FRI',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,16),(623,'FRI',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'12:00',NULL,NULL,NULL,16),(624,'HOLY',NULL,'MASS','ENGLISH',NULL,'Vigil','NONE',NULL,'19:30',NULL,NULL,NULL,16),(625,'HOLY',NULL,'MASS','ENGLISH',NULL,NULL,'NONE',NULL,'06:15',NULL,NULL,NULL,16),(626,'HOLY',NULL,'MASS','ENGLISH',NULL,NULL,'NONE',NULL,'09:00',NULL,NULL,NULL,16),(627,'HOLY',NULL,'MASS','ENGLISH',NULL,NULL,'NONE',NULL,'12:00',NULL,NULL,NULL,16),(628,'HOLY',NULL,'MASS','ENGLISH',NULL,NULL,'NONE',NULL,'18:00',NULL,NULL,NULL,16),(629,'HOLY',NULL,'MASS','ENGLISH',NULL,NULL,'NONE',NULL,'19:30',NULL,NULL,NULL,16),(630,'WED',NULL,'CONFESSION','ENGLISH',NULL,NULL,'WEEKLY',NULL,'11:00',NULL,'11:45',NULL,16),(631,'WED',NULL,'CONFESSION','ENGLISH',NULL,NULL,'WEEKLY',NULL,'20:00',NULL,NULL,NULL,16),(632,'SAT',NULL,'CONFESSION','ENGLISH',NULL,NULL,'WEEKLY',NULL,'15:30',NULL,'17:00',NULL,16),(633,'MON',NULL,'ADORATION','ENGLISH',NULL,'Usually open until 10 p.m. in the summer','NONE',NULL,'07:00',NULL,'20:30',NULL,16),(634,'TUE',NULL,'ADORATION','ENGLISH',NULL,'Usually open until 10 p.m. in the summer','NONE',NULL,'07:00',NULL,'20:30',NULL,16),(635,'WED',NULL,'ADORATION','ENGLISH',NULL,'Usually open until 10 p.m. in the summer','NONE',NULL,'07:00',NULL,'20:30',NULL,16),(636,'THU',NULL,'ADORATION','ENGLISH',NULL,'Usually open until 10 p.m. in the summer','NONE',NULL,'07:00',NULL,'20:30',NULL,16),(637,'FRI',NULL,'ADORATION','ENGLISH',NULL,'Usually open until 10 p.m. in the summer','NONE',NULL,'07:00',NULL,'20:30',NULL,16),(638,'SAT',NULL,'ADORATION','ENGLISH',NULL,NULL,'NONE',NULL,'09:30',NULL,'12:30',NULL,16),(639,NULL,'2014 T.E.A.M. Retreat and Permission Form','OTHER','ENGLISH','Legion of Mary Retreat for Teens',NULL,'NONE','2014-05-20','21:18:09',NULL,NULL,'http://sttimothyparish.org/legion-of-mary-retreat-for-teens/',16),(640,NULL,'Congratulations to Deacon Conroy on the 35th Anniversary of his Diaconate. When you see him, please thank him for all his years of dedicated service to St. Timothy Parish.','OTHER','ENGLISH','Congratulations  Deacon Conroy',NULL,'NONE','2014-05-16','15:24:57',NULL,NULL,'http://sttimothyparish.org/congratulations-deacon-conroy/',16),(641,NULL,'Join fellow parishioners for Italian Night, hosted by the Knights of Columbus, on Saturday May 24th at 6:30 PM in the Cafeteria.','OTHER','ENGLISH','Italian Night with the Knights of Columbus',NULL,'NONE','2014-05-16','15:16:55',NULL,NULL,'http://sttimothyparish.org/italian-night-with-the-knights-of-columbus/',16),(642,NULL,'Healing Mass and Service with Fr. Stefan Tuesday, June 10th 7 PM','OTHER','ENGLISH','Healing Mass',NULL,'NONE','2014-05-01','22:08:15',NULL,NULL,'http://sttimothyparish.org/healing-mass/',16),(643,NULL,'WELCOME TO THE CATHOLIC CHURCH! April Holmes, Gia-Hoa Nguyen, Lani Tran, Kori Truong, Donna Sorrentino WELCOME INTO FULL COMMUNION! Steven Agostinho, Rozelle Baltazar, Carlyn Bender, Katherine Black, James Boyle, Lissa Formanes, Joan Haar, David Jeyes, Sarah Jeyes, Mark Ko, Dewanda Marlow, Melissa Myette, Bryan Otto, Christine Jin Park, Bradford Payne, Colin Thomas Probert, Judith Tchokokam','OTHER','ENGLISH','Welcome Catechumens!',NULL,'NONE','2014-04-22','16:37:11',NULL,NULL,'http://sttimothyparish.org/welcome-catechumens/',16),(644,NULL,'Do you like to cook? One highlight of our High School Youth Group is the home cooked meal that our Parishioners provide each Sunday. Can you cook one Sunday night this year for our teens? We really need your help.','OTHER','ENGLISH','Cooks Needed for Youth Group',NULL,'NONE','2014-04-18','20:34:51',NULL,NULL,'http://sttimothyparish.org/cooks-needed-for-youth-group/',16),(645,NULL,'Mark your calendars and register for St. Tim&#8217;s Catholic VBS! Marvelous Mystery! The Mass Comes Alive! Tues, July 8 &#8211; Sat July 12, 2014! Each day begins after the 9am Mass in the School Cafeteria! Program available for PreK -through high school. Older teens signup for service hours, pre-camp meetings and fun! Registration opens April [&#8230;]','OTHER','ENGLISH','Vacation Bible School',NULL,'NONE','2014-03-20','16:19:24',NULL,NULL,'http://sttimothyparish.org/vacation-bible-school/',16),(646,NULL,'Click here to view to video from the St. Patrick&#8217;s Day Appreciation Dinner','OTHER','ENGLISH','St. Patrick’s Day Appreciation Dinner Pictures',NULL,'NONE','2014-02-19','15:49:29',NULL,NULL,'http://sttimothyparish.org/st-patricks-day-appreciation-dinner/',16),(647,NULL,'','OTHER','ENGLISH','Irish Night with the Knights Of Columbus Pictures',NULL,'NONE','2014-02-12','11:11:36',NULL,NULL,'http://sttimothyparish.org/irish-night-with-the-knights-of-columbus/',16),(648,NULL,'&#8220;Thank you for all the support and material items. You all have been such a blessing to my family and I. You have no idea what a blessing Gabriel Project has been to us.&#8221; &#8220;I just wanted to say thank you so so much for your help and for the gift bag full of goodies [&#8230;]','OTHER','ENGLISH','A Thank You from the Mother of Twins and Pictures from the Baby Shower Hosted by the Gabriel Society',NULL,'NONE','2014-02-05','18:19:45',NULL,NULL,'http://sttimothyparish.org/pictures-from-the-baby-shower-for-twins-hosted-by-the-gabriel-society/',16),(649,'SAT',NULL,'VIGIL_MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'17:00',NULL,NULL,NULL,17),(650,'SAT',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,17),(651,'SUN',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'07:30',NULL,NULL,NULL,17),(652,'SUN',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,17),(653,'SUN',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'11:00',NULL,NULL,NULL,17),(654,'SUN',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'17:00',NULL,NULL,NULL,17),(655,'SUN',NULL,'MASS','SPANISH',NULL,NULL,'WEEKLY',NULL,'13:00',NULL,NULL,NULL,17),(656,'MON',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'06:15',NULL,NULL,NULL,17),(657,'MON',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,17),(658,'TUE',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'06:15',NULL,NULL,NULL,17),(659,'TUE',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,17),(660,'WED',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'06:15',NULL,NULL,NULL,17),(661,'WED',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,17),(662,'THU',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'06:15',NULL,NULL,NULL,17),(663,'THU',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,17),(664,'FRI',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'06:15',NULL,NULL,NULL,17),(665,'FRI',NULL,'MASS','ENGLISH',NULL,NULL,'WEEKLY',NULL,'09:00',NULL,NULL,NULL,17),(666,'FRI',NULL,'CONFESSION','ENGLISH',NULL,NULL,'WEEKLY',NULL,'19:00',NULL,'20:00',NULL,17),(667,'SAT',NULL,'CONFESSION','ENGLISH',NULL,NULL,'WEEKLY',NULL,'15:30',NULL,'16:30',NULL,17),(668,'FRI',NULL,'ADORATION','ENGLISH',NULL,NULL,'WEEKLY',NULL,'20:00',NULL,NULL,NULL,17),(669,'FIRST_SAT',NULL,'ADORATION','ENGLISH',NULL,NULL,'MONTHLY',NULL,'20:00',NULL,NULL,NULL,17),(670,NULL,'The Fortnight For Freedom: Freedom to Serve will take place from June 21 to July 4, 2014. Please join with Catholics from the Diocese of Arlington for a special day of prayer and speakers on Saturday, June 28, 2014 at Saint Joseph Catholic Church Parish Hall, in Herndon, VA. The opening prayer and welcome will [&#8230;]','OTHER','ENGLISH','Fortnight For Freedom  June 21 – July 4, 2014',NULL,'NONE','2014-05-16','12:11:27',NULL,NULL,'http://stleofairfax.com/2014/05/16/fortnight-for-freedom-june-28-2014/',17),(671,NULL,'The Saint Leo the Great School Odyssey of the Mind Division II team has won first place in Virginia and is headed to compete in the World Finals! Our team of seven 7th grade boys from our own Saint Leo the Great School recently won the first place title at the Odyssey of the Mind [&#8230;]','OTHER','ENGLISH','St. Leo School Odyssey of the Mind team wins Virginia State Competition- Advances to World Finals',NULL,'NONE','2014-05-05','14:05:22',NULL,NULL,'http://stleofairfax.com/2014/05/05/st-leo-school-odyssey-of-the-mind-team-wins-virginia-state-competition-advances-to-world-finals/',17),(672,NULL,'Feb. 14, 2014 DIOCESE OF ARLINGTON ? DIOCESE OF RICHMOND Representing the Virginia Catholic Bishops in Public Policy Matters Statement by Bishops Francis X. DiLorenzo and Paul S. Loverde on Decision in Bostic v. Rainey Marriage Case &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; Last night’s decision by U.S. District Judge Arenda Wright Allen in Bostic v. Rainey, a case that [&#8230;]','OTHER','ENGLISH','Joint Statement by VA Bishops on Decision in Bostic v. Rainey Marriage Case',NULL,'NONE','2014-02-19','13:20:19',NULL,NULL,'http://stleofairfax.com/2014/02/19/joint-statement-by-va-bishops-on-decision-in-bostic-v-rainey-marriage-case/',17),(673,NULL,'Please join Father Whitestone on Monday, June 2nd, at P.J. Skidoo&#8217;s, 9908 Fairfax Blvd., Fairfax, for an evening of fellowship and lively discussion over dinner (appetizers on us!). We will be in the room in the back to the right beginning at 7:00 pm. Join us and bring a friend, all ages welcome! To suggest dinner [&#8230;]','OTHER','ENGLISH','Faith & Food at P.J. Skidoos June 2',NULL,'NONE','2014-01-01','18:11:44',NULL,NULL,'http://stleofairfax.com/2014/01/01/faith-food-at-p-j-skidoos-new-series/',17),(674,NULL,'Are you, or someone you know, questioning or struggling to accept some aspect of Church teachings?  Are you unable to participate in the Sacraments of the Church? Do you think that the Catholic faith is irrelevant? Welcome Home is an opportunity for Catholics to find answers &#38; support in a respectful, welcoming setting.  Please contact Deacon Dave [&#8230;]','OTHER','ENGLISH','Welcome Home',NULL,'NONE','2013-12-30','10:13',NULL,NULL,'http://stleofairfax.com/2013/12/30/welcome-home-2/',17),(675,NULL,'Gabriel Project is a ministry helping pregnant women in need. Are you an expectant mother? Would you like to have a friend supporting and accompanying you on your journey to motherhood? Gabriel Project is a parish-based ministry that assists pregnant women with material, emotional, and spiritual support. Please call our confidential helpline at 1-866-444-3553. For volunteer info or to [&#8230;]','OTHER','ENGLISH','Gabriel Project at Saint Leo',NULL,'NONE','2012-09-01','08:46:22',NULL,NULL,'http://stleofairfax.com/2012/09/01/gabriel-project-starting-at-st-leos/',17);
/*!40000 ALTER TABLE `ChurchEvents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tags`
--

DROP TABLE IF EXISTS `Tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `church_event_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_a029cf2b66da47319a88f501940` (`church_event_id`),
  CONSTRAINT `FK_a029cf2b66da47319a88f501940` FOREIGN KEY (`church_event_id`) REFERENCES `ChurchEvents` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tags`
--

LOCK TABLES `Tags` WRITE;
/*!40000 ALTER TABLE `Tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `Tags` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-22 17:39:06
