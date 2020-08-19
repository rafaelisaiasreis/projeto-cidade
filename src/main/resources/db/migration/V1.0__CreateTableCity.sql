CREATE TABLE IF NOT EXISTS city(
ibge_id bigint primary key,
uf varchar(2),
name varchar(60),
capital tinyint(1),
lon double,
lat double,
no_accents varchar(60),
alternative_names varchar(60),
microregion varchar(60),
mesoregion varchar(60)
);