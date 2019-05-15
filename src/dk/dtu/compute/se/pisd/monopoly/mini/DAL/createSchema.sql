CREATE TABLE `Game` (
  `g_ID` int(3) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `currentplayer` int(11) DEFAULT NULL,
  PRIMARY KEY (`g_ID`));

CREATE TABLE `Player` (
  `pl_ID` int(4) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `position` int(3) DEFAULT NULL,
  `balance` int(6) DEFAULT NULL,
  `inprison` tinyint(1) DEFAULT NULL,
  `broke` tinyint(1) DEFAULT NULL,
  `g_ID` int(3) NOT NULL,
  PRIMARY KEY (`pl_ID`,`g_ID`),
  KEY `g_ID` (`g_ID`),
  CONSTRAINT `Player_ibfk_1` FOREIGN KEY (`g_ID`) REFERENCES `Game` (`g_id`) ON DELETE CASCADE);

CREATE TABLE `Property` (
  `pr_ID` int(2) NOT NULL,
  `pl_ID` int(4) DEFAULT NULL,
  `houses` int(1) DEFAULT NULL,
  `g_ID` int(3) NOT NULL,
  PRIMARY KEY (`pr_ID`,`g_ID`),
  KEY `Property_ibfk_1` (`g_ID`),
  KEY `Property_ibfk_2` (`pl_ID`),
  CONSTRAINT `Property_ibfk_1` FOREIGN KEY (`g_ID`) REFERENCES `Game` (`g_id`),
  CONSTRAINT `Property_ibfk_2` FOREIGN KEY (`pl_ID`) REFERENCES `Player` (`pl_id`) ON DELETE CASCADE);