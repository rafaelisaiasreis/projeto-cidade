CREATE TABLE IF NOT EXISTS control(
	id integer primary key auto_increment,
    data_importacao date not null,
    situacao tinyint(1)
);