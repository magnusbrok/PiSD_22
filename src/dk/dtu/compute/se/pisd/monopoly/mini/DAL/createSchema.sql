CREATE TABLE `Game` (
  `g_ID` int(3) NOT NULL,
  `name` text,
  `currentplayer` int(11) DEFAULT NULL,
  PRIMARY KEY (`g_ID`),
  UNIQUE KEY `g_ID` (`g_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `Player` (
  `pl_ID` int(4) NOT NULL,
  `name` text,
  `position` int(3) DEFAULT NULL,
  `balance` int(6) DEFAULT NULL,
  `inprison` tinyint(1) DEFAULT NULL,
  `broke` tinyint(1) DEFAULT NULL,
  `g_ID` int(3) NOT NULL,
  PRIMARY KEY (`pl_ID`,`g_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Property` (
  `pr_ID` int(2) NOT NULL,
  `pl_ID` int(4) DEFAULT NULL,
  `houses` int(1) DEFAULT NULL,
  `g_ID` int(3) NOT NULL,
  PRIMARY KEY (`pr_ID`,`g_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;