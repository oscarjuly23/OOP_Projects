-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-05-2020 a las 18:18:42
-- Versión del servidor: 10.1.25-MariaDB
-- Versión de PHP: 7.1.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `minder`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `chats`
--

CREATE TABLE `chats` (
  `id` int(11) NOT NULL,
  `id_match` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `likes`
--

CREATE TABLE `likes` (
  `id` int(11) NOT NULL,
  `user_from` varchar(30) NOT NULL,
  `user_to` varchar(30) NOT NULL,
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `logins`
--

CREATE TABLE `logins` (
  `id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `last_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `logins`
--

INSERT INTO `logins` (`id`, `username`, `last_date`) VALUES
(256, 'carles.torru', '2020-05-22 16:17:56'),
(257, 'jan.fite', '2020-05-22 16:18:03'),
(258, 'm.prats', '2020-05-22 16:18:09'),
(259, 'o.julian', '2020-05-22 16:18:20'),
(260, 'v.valles', '2020-05-22 16:18:25');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `matchs`
--

CREATE TABLE `matchs` (
  `id` int(11) NOT NULL,
  `username1` varchar(30) NOT NULL,
  `username2` varchar(30) NOT NULL,
  `isMatch` tinyint(1) DEFAULT '1',
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Disparadores `matchs`
--
DELIMITER $$
CREATE TRIGGER `newchat` AFTER INSERT ON `matchs` FOR EACH ROW BEGIN
	INSERT INTO chats values(NULL,NEW.id);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensajes`
--

CREATE TABLE `mensajes` (
  `id` int(11) NOT NULL,
  `user_from` varchar(30) NOT NULL,
  `user_to` varchar(30) NOT NULL,
  `id_chat` int(11) NOT NULL,
  `mensaje` varchar(255) NOT NULL,
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `username` varchar(30) NOT NULL,
  `edat` int(11) NOT NULL,
  `esPremium` tinyint(1) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lang` varchar(5) DEFAULT NULL,
  `img` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`username`, `edat`, `esPremium`, `email`, `password`, `description`, `lang`, `img`) VALUES
('carles.torru', 22, 1, 'carlestorru@salle.edu', '123456aA', 'Nada', 'Java', NULL),
('jan.fite', 21, 1, 'janfite@salle.edu', '123456aA', 'Pieza de puzzle', 'Java', NULL),
('m.prats', 21, 0, 'mprats@salle.edu', '123456aA', 'Bug & Debug', 'Java', NULL),
('o.julian', 22, 0, 'oscarjuly@salle.edu', '123456aA', 'Arce y rosa', 'Java', NULL),
('v.valles', 22, 0, 'vvalles@salle.edu', '123456aA', 'Sushi', 'Java', NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `chats`
--
ALTER TABLE `chats`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_id_match` (`id_match`);

--
-- Indices de la tabla `likes`
--
ALTER TABLE `likes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_user_from` (`user_from`),
  ADD KEY `fk_user_to` (`user_to`);

--
-- Indices de la tabla `logins`
--
ALTER TABLE `logins`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_user_login` (`username`);

--
-- Indices de la tabla `matchs`
--
ALTER TABLE `matchs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_username1` (`username1`),
  ADD KEY `fk_username2` (`username2`);

--
-- Indices de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `chats`
--
ALTER TABLE `chats`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT de la tabla `likes`
--
ALTER TABLE `likes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT de la tabla `logins`
--
ALTER TABLE `logins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=261;
--
-- AUTO_INCREMENT de la tabla `matchs`
--
ALTER TABLE `matchs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `chats`
--
ALTER TABLE `chats`
  ADD CONSTRAINT `fk_id_match` FOREIGN KEY (`id_match`) REFERENCES `matchs` (`id`);

--
-- Filtros para la tabla `likes`
--
ALTER TABLE `likes`
  ADD CONSTRAINT `fk_user_from` FOREIGN KEY (`user_from`) REFERENCES `users` (`username`),
  ADD CONSTRAINT `fk_user_to` FOREIGN KEY (`user_to`) REFERENCES `users` (`username`);

--
-- Filtros para la tabla `logins`
--
ALTER TABLE `logins`
  ADD CONSTRAINT `fk_user_login` FOREIGN KEY (`username`) REFERENCES `users` (`username`);

--
-- Filtros para la tabla `matchs`
--
ALTER TABLE `matchs`
  ADD CONSTRAINT `fk_username1` FOREIGN KEY (`username1`) REFERENCES `users` (`username`),
  ADD CONSTRAINT `fk_username2` FOREIGN KEY (`username2`) REFERENCES `users` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
