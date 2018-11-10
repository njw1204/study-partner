-- phpMyAdmin SQL Dump
-- version 2.11.9.5
-- http://www.phpmyadmin.net
--
-- 호스트: localhost
-- 처리한 시간: 18-11-10 12:56
-- 서버 버전: 5.5.59
-- PHP 버전: 5.6.38

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 데이터베이스: `apk`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `studypartner_study_apply`
--

CREATE TABLE IF NOT EXISTS `studypartner_study_apply` (
  `id` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `study_no` int(11) NOT NULL,
  `msg` varchar(500) COLLATE utf8mb4_bin NOT NULL,
  KEY `study_no` (`study_no`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- 테이블 구조 `studypartner_study_list`
--

CREATE TABLE IF NOT EXISTS `studypartner_study_list` (
  `study_no` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `kind` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `area` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `school` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `contact` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `info` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL,
  `cnt` int(11) NOT NULL DEFAULT '0',
  `notice` varchar(1000) COLLATE utf8mb4_bin NOT NULL DEFAULT '공지사항이 없습니다.',
  PRIMARY KEY (`study_no`),
  UNIQUE KEY `title` (`title`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin AUTO_INCREMENT=25 ;

-- --------------------------------------------------------

--
-- 테이블 구조 `studypartner_user_info`
--

CREATE TABLE IF NOT EXISTS `studypartner_user_info` (
  `id` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `pw` char(64) COLLATE utf8mb4_bin NOT NULL,
  `school` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `nick` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- 테이블 구조 `studypartner_user_study`
--

CREATE TABLE IF NOT EXISTS `studypartner_user_study` (
  `study_no` int(11) NOT NULL,
  `id` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `msg` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL,
  `staff` tinyint(1) NOT NULL DEFAULT '0',
  KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
