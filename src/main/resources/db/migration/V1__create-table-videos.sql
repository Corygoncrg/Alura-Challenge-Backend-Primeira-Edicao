create table videos(
    id bigint not null auto_increment,
    titulo varchar(30) not null,
    descricao varchar(300) not null,
    url varchar(100) not null,

    primary key(id)
);