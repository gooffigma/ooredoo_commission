-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 30, 2025 at 10:30 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ooredoo_commission`
--

-- --------------------------------------------------------

--
-- Table structure for table `commission_rates`
--

CREATE TABLE `commission_rates` (
  `id` int(11) NOT NULL,
  `sales_threshold` varchar(50) DEFAULT NULL,
  `rate` decimal(5,2) DEFAULT NULL,
  `bonus` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `commission_rates`
--

INSERT INTO `commission_rates` (`id`, `sales_threshold`, `rate`, `bonus`) VALUES
(1, '>25000', 12.00, 300.00),
(2, '15000-25000', 8.00, 150.00),
(3, '<15000', 5.00, 0.00),
(4, 'CRS>80', 0.00, 200.00),
(5, 'CRS60-80', 0.00, 100.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `commission_rates`
--
ALTER TABLE `commission_rates`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `commission_rates`
--
ALTER TABLE `commission_rates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
