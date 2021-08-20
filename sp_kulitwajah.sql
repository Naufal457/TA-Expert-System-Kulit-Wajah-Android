# Host: localhost  (Version 5.5.5-10.4.17-MariaDB)
# Date: 2021-07-13 10:43:42
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "gejala"
#

DROP TABLE IF EXISTS `gejala`;
CREATE TABLE `gejala` (
  `id_gejala` int(11) NOT NULL AUTO_INCREMENT,
  `kode_gejala` varchar(5) NOT NULL,
  `nama_gejala` text NOT NULL,
  `nilai_cf` float NOT NULL,
  PRIMARY KEY (`id_gejala`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;

#
# Data for table "gejala"
#

INSERT INTO `gejala` VALUES (4,'01','Muncul bintik-bintik atau bercak',0.8),(5,'02','Terasa nyeri',0.6),(6,'03','Bintik atau bercak berwarna gelap',0.8),(7,'04','Bintik sewarna dengan kulit',0.6),(12,'05','Bentuknya semakin membesar saat berkeringat',0.8),(17,'06','Muncul dikedua sisi wajah',0.8),(18,'07','Muncul dibagian mata',0.8),(19,'08','Muncul pori pori terbuka seperti komedo putih',0.8),(20,'09','Muncul pori-pori tertutup seperti komedo hitam',0.8),(21,'10','Muncul papul (bintik merah kecil)',0.8),(22,'11','mengalami peradangan pada area tersebut',0.8),(23,'12','mengalami hiperpigmentasi atau perbedaan warna kulit dari sebelumnya',0.6),(24,'13','sebelumnya pernah memakai obat-obatan kontrasepsi',0.8),(25,'14','bintik ataupun bercak berada di pipi kanan dan kiri',0.8),(26,'15','Muncul peradangan kering pada area tersebut',0.6),(27,'16','Bentuk bintik maupun bercak semakin membesar',0.6),(28,'17','berbentuk bulat dan tegas',0.8),(29,'18','muncul benjolan berbentuk bintik putih',0.6),(30,'19','mudah iritasi saat bergesekan',0.8),(31,'20','bintik atau bercak berwarna kemerahan',0.6),(32,'21','pelebaran pembuluh darah dibagian wajah',0.8),(33,'22','kulit wajah terasa kering dan kasar',0.6),(34,'23','kulit wajah berminyak dan sensitif',0.6),(35,'24','hangat ketika diraba',0.8),(36,'25','bintik atau bercak berwarna kekuningan',0.6),(37,'26','mempunyai riwayat kolesterol tinggi',0.8),(38,'27','terdapat plak warna kuning pada kelopak mata',0.8),(39,'28','bintik atau bercak berwarna coklat',0.6),(40,'29','bintik atau bercak berwarna coklat kehitaman',0.8),(41,'30','mudah berdarah',0.6),(42,'31','lesi luka tidak kunjung sembuh',0.8),(43,'32','membesar dan meluas seiring waktu',0.6),(44,'33','batas pada bagian tersebut tidak tegas dan permukaan tidak rata',0.8),(45,'34','Bentuk tepi tidak teratur',0.8);

#
# Structure for table "pengguna"
#

DROP TABLE IF EXISTS `pengguna`;
CREATE TABLE `pengguna` (
  `id_pengguna` int(11) NOT NULL AUTO_INCREMENT,
  `nama_lengkap` varchar(30) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `level` enum('Admin','User') NOT NULL,
  PRIMARY KEY (`id_pengguna`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

#
# Data for table "pengguna"
#

INSERT INTO `pengguna` VALUES (1,'Administrator','admin','admin','Admin'),(5,'zaky','zaky','zaky','User'),(6,'nando','nando','nando','User'),(7,'azmi','azmi','biji','User'),(9,'meisya','ica','ica','User'),(10,'naufal','naufal','naufal','User'),(11,'mubarak','mubarak','mubarak','User'),(12,'ujang','ujang','ujang','User'),(13,'wira','wira','12','User');

#
# Structure for table "penyakit"
#

DROP TABLE IF EXISTS `penyakit`;
CREATE TABLE `penyakit` (
  `id_penyakit` int(11) NOT NULL AUTO_INCREMENT,
  `kode_penyakit` varchar(5) NOT NULL,
  `nama_penyakit` varchar(50) NOT NULL,
  `deskripsi` text NOT NULL,
  `solusi` text NOT NULL,
  PRIMARY KEY (`id_penyakit`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

#
# Data for table "penyakit"
#

INSERT INTO `penyakit` VALUES (3,'01','Acne Vulgaris','adalah penyakit jerawat yang berasal dari faktor hormon','jangan menyingkirkan jerawat dengan memencetnya'),(4,'02','Melasma','yu','ya'),(5,'03','Nevus Melanositik','yu','yuha'),(6,'G04','Syringoma','iio','eee'),(7,'P05','Milia','o','o'),(8,'P06','Rosacea','yyy','yyy'),(9,'P07','Xanthelasma','uuuu','uuuu'),(10,'P08','Keratosis Seboroik','--','--'),(11,'9','Basalioma','bisa','bisa'),(12,'10','melanoma','bsa','bisa');

#
# Structure for table "aturan"
#

DROP TABLE IF EXISTS `aturan`;
CREATE TABLE `aturan` (
  `id_aturan` int(11) NOT NULL AUTO_INCREMENT,
  `id_penyakit` int(11) NOT NULL,
  `id_gejala` int(11) NOT NULL,
  PRIMARY KEY (`id_aturan`) USING BTREE,
  KEY `id_penyakit` (`id_penyakit`),
  KEY `id_gejala` (`id_gejala`),
  CONSTRAINT `aturan_ibfk_1` FOREIGN KEY (`id_penyakit`) REFERENCES `penyakit` (`id_penyakit`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `aturan_ibfk_2` FOREIGN KEY (`id_gejala`) REFERENCES `gejala` (`id_gejala`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=latin1;

#
# Data for table "aturan"
#

INSERT INTO `aturan` VALUES (40,3,4),(41,3,5),(42,3,19),(43,3,20),(44,3,21),(45,4,4),(46,4,6),(47,4,22),(48,4,23),(49,4,24),(50,4,25),(51,5,4),(52,5,6),(53,5,27),(54,5,28),(55,6,4),(56,6,7),(57,6,12),(58,6,17),(59,6,18),(60,7,4),(61,7,7),(62,7,17),(63,7,18),(64,7,29),(65,7,30),(66,8,4),(67,8,17),(68,8,31),(69,8,33),(70,8,34),(71,8,35),(72,9,4),(73,9,18),(74,9,35),(75,9,37),(76,9,38),(77,10,4),(78,10,6),(79,10,39),(80,10,44),(81,11,4),(82,11,6),(83,11,40),(84,11,41),(85,11,42),(86,11,45),(87,12,4),(88,12,6),(89,12,39),(90,12,40),(91,12,43),(92,12,44),(93,12,45);

#
# Structure for table "profil"
#

DROP TABLE IF EXISTS `profil`;
CREATE TABLE `profil` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text DEFAULT NULL,
  `picture` text DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

#
# Data for table "profil"
#


#
# Structure for table "riwayat"
#

DROP TABLE IF EXISTS `riwayat`;
CREATE TABLE `riwayat` (
  `id_riwayat` int(11) NOT NULL AUTO_INCREMENT,
  `id_pengguna` int(11) DEFAULT NULL,
  `tanggal` date NOT NULL,
  `id_penyakit` int(11) DEFAULT NULL,
  `metode` enum('Forward Chaining','Certainty Factor') NOT NULL,
  `nilai` float DEFAULT NULL,
  PRIMARY KEY (`id_riwayat`),
  KEY `id_penyakit` (`id_penyakit`),
  KEY `id_pengguna` (`id_pengguna`),
  CONSTRAINT `riwayat_ibfk_1` FOREIGN KEY (`id_penyakit`) REFERENCES `penyakit` (`id_penyakit`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `riwayat_ibfk_2` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4;

#
# Data for table "riwayat"
#

INSERT INTO `riwayat` VALUES (17,5,'2021-03-17',3,'Certainty Factor',100),(18,5,'2021-03-22',3,'Certainty Factor',86.94),(19,5,'2021-04-05',3,'Forward Chaining',NULL),(20,5,'2021-04-05',3,'Certainty Factor',88.48),(21,5,'2021-04-12',3,'Forward Chaining',NULL),(22,5,'2021-04-12',3,'Certainty Factor',88.48),(23,5,'2021-05-10',3,'Certainty Factor',88.48),(25,9,'2021-06-04',3,'Forward Chaining',NULL),(44,10,'2021-07-09',3,'Certainty Factor',97.41),(45,10,'2021-07-10',3,'Certainty Factor',93.38),(46,10,'2021-07-10',4,'Certainty Factor',95.5),(47,10,'2021-07-10',5,'Certainty Factor',95.69),(48,10,'2021-07-10',6,'Certainty Factor',97.37),(49,10,'2021-07-10',7,'Certainty Factor',98.63),(50,10,'2021-07-10',8,'Certainty Factor',97.93),(51,10,'2021-07-10',9,'Certainty Factor',96.56),(52,10,'2021-07-10',10,'Certainty Factor',93.77),(53,10,'2021-07-10',11,'Certainty Factor',99.19),(54,10,'2021-07-10',12,'Certainty Factor',98.04),(55,10,'2021-07-10',3,'Certainty Factor',96.56),(56,10,'2021-07-10',5,'Certainty Factor',94.94),(57,10,'2021-07-12',4,'Certainty Factor',99.13);
