create table drink_list(
 id int not null auto_increment,
 name varchar(255) not null,
 primary key(id)
 );
 
 create table adress_list(
 id int not null auto_increment,
 encount_date timestamp not null,
 drink_id varchar(255) not null ,
 address varchar(255) not null,
 primary key(id)
 );