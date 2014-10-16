-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb2
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Mar 22 Avril 2014 à 18:35
-- Version du serveur: 5.5.35
-- Version de PHP: 5.4.4-14+deb7u8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `java`
--
CREATE DATABASE java;

-- --------------------------------------------------------

--
-- Structure de la table `Articles`
--

DROP TABLE IF EXISTS `Articles`;
CREATE TABLE IF NOT EXISTS `Articles` (
  `id_article` int(11) NOT NULL AUTO_INCREMENT,
  `id_membre_article` int(11) NOT NULL,
  `lien_video_article` varchar(250) NOT NULL,
  `lien_miniature_article` varchar(250) NOT NULL DEFAULT 'http://placehold.it/320x180/007dbb/FFFFFF/&text=video',
  `titre_article` varchar(250) NOT NULL,
  `texte_article` text NOT NULL,
  `date_postage_article` date NOT NULL,
  `vote_article` int(11) NOT NULL,
  PRIMARY KEY (`id_article`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `Articles`
--

INSERT INTO `Articles` (`id_article`, `id_membre_article`, `lien_video_article`, `lien_miniature_article`, `titre_article`, `texte_article`, `date_postage_article`, `vote_article`) VALUES
(1, 1, 'IDSMUISSwpQ', 'http://placehold.it/320x180/007dbb/FFFFFF', 'Hack ''n'' Slash', 'Voici un jeu', '2014-04-01', 0),
(2, 1, 'IDSMUISSwpQ', 'http://placehold.it/320x180/007dbb/FFFFFF', 'Hach ''n'' slash', 'Super jeu', '2014-04-01', 2),
(3, 1, 'tAJVnIDEf6Q', 'http://placehold.it/320x180/007dbb/FFFFFF', 'lol', 'lol', '2014-04-01', -3),
(4, 4, '6UnPKTBXXZk', 'http://www.showinweb.com/wp-content/uploads/2013/04/BS9ye-320x180.jpg', 'Musique', 'Musique pas mal trouver sur le web blablabal', '2014-04-19', 2),
(5, 4, '8vpS7TBKtJw', 'http://cdn.allsongs.tv/image/97ff6646-a4f3-43ee-a749-ba965bd5ff05/sasha-grey-italia-09jpg-320x180.jpg', 'God bless Sasha Grey!', 'God bless The Queen !', '2014-04-22', 4);

-- --------------------------------------------------------

--
-- Structure de la table `Commentaires`
--

DROP TABLE IF EXISTS `Commentaires`;
CREATE TABLE IF NOT EXISTS `Commentaires` (
  `id_commentaire` int(11) NOT NULL AUTO_INCREMENT,
  `id_membre_commentaire` int(11) NOT NULL,
  `id_article_commentaire` int(11) NOT NULL,
  `texte_commentaire` varchar(512) NOT NULL,
  `date_commentaire` date NOT NULL,
  PRIMARY KEY (`id_commentaire`),
  KEY `id_membre_commentaire` (`id_membre_commentaire`),
  KEY `id_article_commentaire` (`id_article_commentaire`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `Commentaires`
--

INSERT INTO `Commentaires` (`id_commentaire`, `id_membre_commentaire`, `id_article_commentaire`, `texte_commentaire`, `date_commentaire`) VALUES
(1, 1, 1, 'Whaou trop cool', '2014-04-14'),
(2, 1, 1, 'Salut', '2014-04-19');

-- --------------------------------------------------------

--
-- Structure de la table `ConnexionTchat`
--

DROP TABLE IF EXISTS `ConnexionTchat`;
CREATE TABLE IF NOT EXISTS `ConnexionTchat` (
  `id_membre_connexiontchat` int(11) NOT NULL AUTO_INCREMENT,
  `ip_connexiontchat` varchar(15) NOT NULL DEFAULT '',
  `timestamp_connexiontchat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `co_or_not_connexiontchat` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_membre_connexiontchat`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `ConnexionTchat`
--

INSERT INTO `ConnexionTchat` (`id_membre_connexiontchat`, `ip_connexiontchat`, `timestamp_connexiontchat`, `co_or_not_connexiontchat`) VALUES
(1, '127.0.1.1', '2014-04-22 16:34:07', 1),
(2, '127.0.1.1', '2014-04-22 16:34:31', 1),
(3, '127.0.1.1', '2014-04-22 16:33:47', 1),
(4, '127.0.1.1', '2014-04-22 16:30:42', 1);

-- --------------------------------------------------------

--
-- Structure de la table `Membres`
--

DROP TABLE IF EXISTS `Membres`;
CREATE TABLE IF NOT EXISTS `Membres` (
  `id_membre` int(11) NOT NULL AUTO_INCREMENT,
  `pseudo_membre` varchar(250) NOT NULL,
  `password_membre` varchar(250) NOT NULL,
  `email_membre` varchar(250) NOT NULL,
  `description_membre` varchar(250) NOT NULL DEFAULT 'Aucune',
  `sexe_membre` char(1) NOT NULL DEFAULT 'M',
  `date_inscription_membre` date NOT NULL,
  `rang_membre` int(11) NOT NULL DEFAULT '2',
  PRIMARY KEY (`id_membre`),
  KEY `rang_membre` (`rang_membre`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `Membres`
--

INSERT INTO `Membres` (`id_membre`, `pseudo_membre`, `password_membre`, `email_membre`, `description_membre`, `sexe_membre`, `date_inscription_membre`, `rang_membre`) VALUES
(1, 'Kaldoran', 'ok', 'lol@hotmail.fr', 'Je suis un avion', 'M', '2014-03-01', 5),
(2, 'Squall', 'ok', 'aucun@hotmail.fr', 'Aucune ', 'M', '2014-04-14', 2),
(3, 'Wescoeur', 'ok', 'wes@hotmail.fr', 'Aucune', 'M', '2014-04-19', 2),
(4, 'Kevin', 'ok', 'adresse@hotmail.fr', 'Aucune', 'M', '2014-04-19', 2);

-- --------------------------------------------------------

--
-- Structure de la table `Messages`
--

DROP TABLE IF EXISTS `Messages`;
CREATE TABLE IF NOT EXISTS `Messages` (
  `id_message` int(11) NOT NULL AUTO_INCREMENT,
  `id_envoyeur_message` int(11) NOT NULL,
  `id_destinataire_message` int(11) NOT NULL,
  `msg_message` varchar(512) NOT NULL,
  `date_message` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_message`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

-- --------------------------------------------------------

--
-- Structure de la table `Playlists`
--

DROP TABLE IF EXISTS `Playlists`;
CREATE TABLE IF NOT EXISTS `Playlists` (
  `id_playlist` int(11) NOT NULL AUTO_INCREMENT,
  `id_membre_playlist` int(11) NOT NULL,
  `id_article_playlist` int(11) NOT NULL,
  `date_playlist` date NOT NULL,
  PRIMARY KEY (`id_playlist`,`id_membre_playlist`,`id_article_playlist`),
  KEY `id_membre_playlist` (`id_membre_playlist`),
  KEY `id_article_playlist` (`id_article_playlist`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `Playlists`
--

INSERT INTO `Playlists` (`id_playlist`, `id_membre_playlist`, `id_article_playlist`, `date_playlist`) VALUES
(1, 1, 1, '2014-04-14'),
(2, 1, 2, '2014-04-14'),
(3, 1, 4, '2014-04-19');

-- --------------------------------------------------------

--
-- Structure de la table `Rangs`
--

DROP TABLE IF EXISTS `Rangs`;
CREATE TABLE IF NOT EXISTS `Rangs` (
  `id_rang` int(11) NOT NULL AUTO_INCREMENT,
  `nom_rang` varchar(64) NOT NULL,
  PRIMARY KEY (`id_rang`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `Rangs`
--

INSERT INTO `Rangs` (`id_rang`, `nom_rang`) VALUES
(0, 'Banni'),
(2, 'Membre'),
(3, 'Moderateur'),
(4, 'Administrateur'),
(5, 'Dieu');

-- --------------------------------------------------------

--
-- Structure de la table `Votes`
--

DROP TABLE IF EXISTS `Votes`;
CREATE TABLE IF NOT EXISTS `Votes` (
  `id_vote` int(11) NOT NULL AUTO_INCREMENT,
  `id_membre_vote` int(11) NOT NULL,
  `id_article_vote` int(11) NOT NULL,
  `date_vote` date NOT NULL,
  PRIMARY KEY (`id_vote`),
  KEY `id_membre_vote` (`id_membre_vote`),
  KEY `id_article_vote` (`id_article_vote`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

--
-- Contenu de la table `Votes`
--

INSERT INTO `Votes` (`id_vote`, `id_membre_vote`, `id_article_vote`, `date_vote`) VALUES
(6, 1, 4, '2014-04-20'),
(7, 1, 1, '2014-04-20'),
(8, 1, 2, '2014-04-20'),
(9, 1, 3, '2014-04-20'),
(10, 3, 3, '2014-04-20'),
(11, 4, 1, '2014-04-22'),
(12, 4, 2, '2014-04-22'),
(13, 4, 4, '2014-04-22'),
(14, 4, 5, '2014-04-22'),
(15, 2, 5, '2014-04-22'),
(16, 3, 5, '2014-04-22'),
(17, 1, 5, '2014-04-22'),
(18, 2, 3, '2014-04-22');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
