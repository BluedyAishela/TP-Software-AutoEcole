-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : db:3306
-- Généré le : lun. 27 mars 2023 à 17:02
-- Version du serveur : 8.0.32
-- Version de PHP : 8.0.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `app_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `id` int NOT NULL,
  `libelle` varchar(50) NOT NULL,
  `prix` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`id`, `libelle`, `prix`) VALUES
(1, 'Poids Lourd', 10.39),
(2, 'Voiture', 14.03);

-- --------------------------------------------------------

--
-- Structure de la table `eleve`
--

CREATE TABLE `eleve` (
  `id` int NOT NULL,
  `idUser` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `eleve`
--

INSERT INTO `eleve` (`id`, `idUser`) VALUES
(1, 8),
(2, 14),
(18, 20),
(19, 21),
(20, 22),
(21, 23),
(22, 24),
(23, 25),
(24, 26),
(25, 27),
(26, 28),
(27, 29),
(28, 30),
(29, 31),
(30, 32),
(31, 33),
(32, 34),
(33, 35),
(34, 36),
(35, 37),
(36, 38),
(37, 39),
(38, 40),
(39, 41),
(40, 42),
(41, 43);

-- --------------------------------------------------------

--
-- Structure de la table `lecon`
--

CREATE TABLE `lecon` (
  `id` int NOT NULL,
  `date` date NOT NULL,
  `heure` varchar(50) NOT NULL,
  `idMoniteur` int NOT NULL,
  `idEleve` int NOT NULL,
  `idVehicule` int NOT NULL,
  `reglee` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `lecon`
--

INSERT INTO `lecon` (`id`, `date`, `heure`, `idMoniteur`, `idEleve`, `idVehicule`, `reglee`) VALUES
(31, '2023-03-27', '8', 1, 1, 2, 0),
(32, '2023-03-27', '14', 1, 1, 2, 0),
(33, '2023-03-27', '16', 1, 1, 2, 1),
(34, '2023-03-29', '14', 1, 1, 2, 1),
(35, '2023-03-31', '14', 1, 1, 13, 0),
(36, '2023-03-28', '16', 1, 1, 13, 0),
(37, '2023-03-28', '10', 1, 1, 3, 1),
(38, '2023-04-13', '10', 1, 1, 3, 0),
(39, '2023-04-13', '9', 1, 1, 3, 1),
(40, '2023-04-13', '14', 8, 1, 3, 0),
(41, '2023-04-13', '14', 1, 1, 2, 1),
(42, '2023-03-27', '8', 3, 2, 13, 0),
(43, '2023-03-27', '9', 1, 2, 13, 1),
(44, '2023-03-27', '10', 1, 2, 13, 1),
(45, '2023-03-30', '10', 1, 2, 13, 0),
(46, '2023-03-29', '10', 1, 2, 13, 0),
(47, '2023-03-28', '10', 3, 2, 13, 1),
(48, '2023-03-28', '17', 1, 2, 2, 0),
(49, '2023-03-28', '17', 3, 2, 13, 0),
(50, '2023-04-22', '17', 1, 2, 13, 0),
(51, '2023-04-22', '18', 7, 2, 13, 0),
(52, '2023-04-22', '18', 1, 2, 3, 1),
(53, '2023-05-19', '8', 8, 2, 2, 0),
(54, '2023-06-23', '8', 1, 2, 2, 1),
(55, '2024-03-14', '8', 1, 1, 2, 0);

-- --------------------------------------------------------

--
-- Structure de la table `licence`
--

CREATE TABLE `licence` (
  `id` int NOT NULL,
  `idMoniteur` int NOT NULL,
  `idCategorie` int NOT NULL,
  `dateobtention` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `licence`
--

INSERT INTO `licence` (`id`, `idMoniteur`, `idCategorie`, `dateobtention`) VALUES
(1, 1, 1, '2022-12-01'),
(6, 1, 2, '2022-12-16');

-- --------------------------------------------------------

--
-- Structure de la table `moniteur`
--

CREATE TABLE `moniteur` (
  `id` int NOT NULL,
  `idUser` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `moniteur`
--

INSERT INTO `moniteur` (`id`, `idUser`) VALUES
(1, 10),
(2, 14),
(3, 44),
(4, 45),
(5, 46),
(6, 47),
(7, 48),
(8, 49),
(9, 50),
(10, 51);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(50) NOT NULL,
  `nom` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `prenom` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `sexe` int DEFAULT NULL,
  `datedenaissance` date DEFAULT NULL,
  `adresse` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `codepostal` varchar(7) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `ville` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `telephone` varchar(12) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `login`, `password`, `role`, `nom`, `prenom`, `sexe`, `datedenaissance`, `adresse`, `codepostal`, `ville`, `telephone`) VALUES
(8, 'Ryu', '$2a$10$fTzPVBwu7MPtxo66StF11.Xr9EYFLCvNeD1aCwQN82AE1siVWb//S', 'ROLE_ELEVE', 'Bladar', 'Raijin', 3, '2022-10-01', '', '74666', 'Paris', '93763'),
(10, 'Bluedy', '$2a$10$z8D1gxjc1HPGj8HDYTf9NeTe28RlEWzfV6U3qVliU2oZ.LbofItvO', 'ROLE_MONITEUR', 'TORDJMAN', 'Mickaël', 1, '2003-10-19', 'Sihde', '74666', 'Paris', '0695733629'),
(14, 'Salameche', '$2a$10$Dd94aDn1qUlWEVQvCRDo1OdvbkCi9/4KLNg3MFJGwjm4bTGeoh3mK', 'ROLE_ELEVE', 'Klair', 'Sieg', 2, '2003-10-19', 'Seine', '30333', 'Mars', '0694327692'),
(15, 'Kagenou', '$2a$10$Dd94aDn1qUlWEVQvCRDo1OdvbkCi9/4KLNg3MFJGwjm4bTGeoh3mK', 'ROLE_RESPONSABLE', 'Kageno', 'Cid', 0, '2001-10-04', '28 Rue Charles', '77600', 'Lille', '0674359247'),
(20, 'FreezUranium', '$2a$10$u7.k9GUSBpPXKurXkbG7kuJlrZgeIiFSGtUhEZWhg7cY4ZBKtS..y', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(21, 'BurgerPredator', '$2a$10$Q7VKTqbmYMt5Tc.VHH4GL.ooYRHbZfSLTA0maIKC4f6CdduMgW51e', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(22, 'XxxBolt', '$2a$10$JSaP6jYPb56N./NdGkHRk.xDlUoKLWt20YiOsu4SBqBQ0cXeB3I4e', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(23, 'OnixVador', '$2a$10$l.TzlPozYzkq7tcKqQH2NufZ3tPymcaSOJT0gQ.ZoQFeWpinFLs4m', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(24, 'RikuJackpot', '$2a$10$wVjrQ/T/0ATJFrjZyRV3sO.W/kaUsU3P01FOUu7E97SBzWePRcxym', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(25, 'BoltBad', '$2a$10$xogudlvQbVnr5SVcNh0OGO6lT1FfEZMV1GSwkNAVRoOJT148BKMYq', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(26, 'SushiTek', '$2a$10$r4xDZo/.cAN0j2PLfdN.9ut16/AuvCNL5ylxOW6dhf2SXIbsKxt8.', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(27, 'PhantomGame', '$2a$10$z8K1/xhfMKTiHNjKRMOCqeE8qxQU5FrIyPn90xWVhnTuBXYBtATTC', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(28, 'DocteurHydro', '$2a$10$PocLOLQe9w3N4h3FW4Ki7ONniguoZ.Xf0PVEnHonf/eeQT/STUSNS', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(29, 'FantasySasuke', '$2a$10$ytH3QTnOGTfAcvO1vuBoF.zg3CdQx6JO3wVhARxARI.TA5utawIcW', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(30, 'LegendStun', '$2a$10$zk2KDchAgrtwnAg.mL/qr.3r7f.7atDnsVzzxdkxBd/4NDX7PNlc2', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(31, 'SnakeFr', '$2a$10$cDf1c4EzWdl0q3ZCY0Zwx.h53FZqepeePAQwv9cGc7Hxk5fAHvWXu', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(32, 'ViperFreez', '$2a$10$Cq0drMQSZC67TiITB8zM9O9u7viSTLpzyn6zNl0XH5UUvFfSj2Pwm', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(33, 'XxxRoxxor', '$2a$10$Jfx.pX64SOS/tqVsMG/60u9UPp22cm7uBFEXAuxk6e.zuqIc/APru', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(34, 'LagRussian', '$2a$10$ygjMxQi553hjIJ/YNKd.kegfjWSR4gTbsTCt3mQFQkOapgXGSI1Ne', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(35, 'FlammeDiabète', '$2a$10$Mc2anlhjhBgaE9/DVFuk3.vytW4rLPjCIptaSZUGNFkXeboGOmh0C', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(36, 'BurgerSky', '$2a$10$q8hKuWrCI9PZtSiRF7wJZ.jZcPF.z/QQMHkqiIWaEsZazluOKts9O', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(37, 'MoonBlade', '$2a$10$sCxkunNe8Tq9scF.bNhp5OnYdFhK3jzAd2T9WqFynhQJ1o/Q/.qNi', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(38, 'DragonPickle', '$2a$10$mbkKX9yUNY/KQRuOusunaO96vlouexe1LghR04XiU01AYALfTU6Xq', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(39, 'XJackpot', '$2a$10$As0uwAP8Kj3qoX2qSTDi5e01jA0wxBu6cBsIqRyl258LVLQDQqwDy', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(40, 'SasukeCar', '$2a$10$Qmxor4WaSVqdUnU8z4nsReIPqffWQDsCpb50jPKDUxz83uGZGvmIG', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(41, 'DemonRiver', '$2a$10$cZh4jDN22oIbe1oSoi.Nye4pkHaJIMQdRfDDo8OC2tX0CaRrbu8RW', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(42, 'ZeLord', '$2a$10$Y2US6Vn37TZiGw/w3tmOEuLCN8b1YuOm8aeEM.s9Yj7IU8DYZ/hFm', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(43, 'FreestyleKill', '$2a$10$Q6aOg/nKotZg0sNqrVTSRuMrwNDNFccLae.SPElKAD.4b3wXBE9E6', 'ROLE_ELEVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(44, 'HyperFantasy', '$2a$10$WbjPHS.6/gWU4V/SY2yBoeqPKScZUBCNeQ5xGaaCyosnWoO55Mt3m', 'ROLE_MONITEUR', 'Anglais', 'Orane', 0, NULL, NULL, NULL, NULL, NULL),
(45, 'DinoIron', '$2a$10$p/XztGeawJ/eOdTk2lxlcebb.YGtxxBq/gAWJkWPrnD/xH54idWCe', 'ROLE_MONITEUR', 'Ormazd', 'Goudreau', 2, NULL, NULL, NULL, NULL, NULL),
(46, 'PredatorHadess', '$2a$10$tykOjDWvU7QElUNPNYzxTeQb5I35jZXtjv9klk501aEpwYySuERku', 'ROLE_MONITEUR', 'Panetier', 'Eustache', 0, NULL, NULL, NULL, NULL, NULL),
(47, 'PepitoBrave', '$2a$10$WrdAui9sddE/QAy0NVGeXe.BgpiWcqGCEDa6vnWiMgiOvO4wpObhW', 'ROLE_MONITEUR', 'Tyson', 'Sciverit', 1, NULL, NULL, NULL, NULL, NULL),
(48, 'PotatoHyper', '$2a$10$7l3fCVF78kmIQJrh7QIwtOxY14gv9Us/Gu0SX/cteO537zPhXepyq', 'ROLE_MONITEUR', 'Valentine', 'Doyon', 0, NULL, NULL, NULL, NULL, NULL),
(49, 'HackCar', '$2a$10$dBoQPy1gWEk4O6qei0JFaek.3Fhib3w605l/o4RMUVL//SUyngCKa', 'ROLE_MONITEUR', 'Auguste', 'Roussel', 1, NULL, NULL, NULL, NULL, NULL),
(50, 'TotoHeal', '$2a$10$VMZNVU/eKR40qxaUeiJBnue6Goy/woVJPeNhtX2Io2hOWdgZuBySy', 'ROLE_MONITEUR', 'Gladu', 'Pansy', 2, NULL, NULL, NULL, NULL, NULL),
(51, 'GalacticBurger', '$2a$10$1ZCrrjTnY4tNk07Ys7c8l.O20iH4Ec4idaQOIrJtblnGL5g6709Yq', 'ROLE_MONITEUR', 'Lapresse', 'Blondelle', 3, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `vehicule`
--

CREATE TABLE `vehicule` (
  `id` int NOT NULL,
  `immatriculation` varchar(50) NOT NULL,
  `marque` varchar(50) NOT NULL,
  `modele` varchar(50) NOT NULL,
  `annee` int NOT NULL,
  `idCategorie` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `vehicule`
--

INSERT INTO `vehicule` (`id`, `immatriculation`, `marque`, `modele`, `annee`, `idCategorie`) VALUES
(2, 'HD-208-Ja', 'Ferrari', '458 Italia', 2007, 1),
(3, 'AA-282-BB', 'Porche', '2006', 2000, 2),
(12, 'FF-157-AY', 'citroen', 'C3', 2019, 2),
(13, 'AC-548-BD', 'Audi', 'C574', 2004, 2);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `eleve`
--
ALTER TABLE `eleve`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idUser` (`idUser`);

--
-- Index pour la table `lecon`
--
ALTER TABLE `lecon`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idMoniteur` (`idMoniteur`,`idEleve`,`idVehicule`),
  ADD KEY `idVehicule` (`idVehicule`),
  ADD KEY `idEleve` (`idEleve`);

--
-- Index pour la table `licence`
--
ALTER TABLE `licence`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idMoniteur` (`idMoniteur`,`idCategorie`),
  ADD KEY `idCategorie` (`idCategorie`);

--
-- Index pour la table `moniteur`
--
ALTER TABLE `moniteur`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idUser` (`idUser`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UQ_user_login` (`login`);

--
-- Index pour la table `vehicule`
--
ALTER TABLE `vehicule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idCategorie` (`idCategorie`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `eleve`
--
ALTER TABLE `eleve`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT pour la table `lecon`
--
ALTER TABLE `lecon`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT pour la table `licence`
--
ALTER TABLE `licence`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `moniteur`
--
ALTER TABLE `moniteur`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT pour la table `vehicule`
--
ALTER TABLE `vehicule`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `eleve`
--
ALTER TABLE `eleve`
  ADD CONSTRAINT `eleve_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `lecon`
--
ALTER TABLE `lecon`
  ADD CONSTRAINT `lecon_ibfk_1` FOREIGN KEY (`idVehicule`) REFERENCES `vehicule` (`id`),
  ADD CONSTRAINT `lecon_ibfk_2` FOREIGN KEY (`idEleve`) REFERENCES `eleve` (`id`),
  ADD CONSTRAINT `lecon_ibfk_3` FOREIGN KEY (`idMoniteur`) REFERENCES `moniteur` (`id`);

--
-- Contraintes pour la table `licence`
--
ALTER TABLE `licence`
  ADD CONSTRAINT `licence_ibfk_1` FOREIGN KEY (`idMoniteur`) REFERENCES `moniteur` (`id`),
  ADD CONSTRAINT `licence_ibfk_2` FOREIGN KEY (`idCategorie`) REFERENCES `categorie` (`id`);

--
-- Contraintes pour la table `moniteur`
--
ALTER TABLE `moniteur`
  ADD CONSTRAINT `moniteur_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `vehicule`
--
ALTER TABLE `vehicule`
  ADD CONSTRAINT `vehicule_ibfk_1` FOREIGN KEY (`idCategorie`) REFERENCES `categorie` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
