CREATE TABLE IF NOT EXISTS city(
	id bigint primary key auto_increment,
	ibge_id bigint unique not null,
	uf varchar(2) not null,
	name varchar(60) not null,
	capital tinyint(1),
	lon double not null,
	lat double not null,
	no_accents varchar(60) not null,
	alternative_names varchar(60),
	microregion varchar(60) not null,
	mesoregion varchar(60) not null
);