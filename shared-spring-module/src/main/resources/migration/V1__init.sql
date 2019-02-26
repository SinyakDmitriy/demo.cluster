create table user_table (
  id INT(11) NOT NULL AUTO_INCREMENT,
  ref_id INT(11) NOT NULL,
  email varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) not null,
  PRIMARY KEY (id),
  UNIQUE KEY ref_id_unique (ref_id),
  UNIQUE KEY email_unique (email)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;