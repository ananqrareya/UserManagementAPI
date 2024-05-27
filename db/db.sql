create database useracount; 
use useracount; 
create table user(
id int auto_increment primary key , 
first_name varchar(255) not null ,
last_name varchar(255) not null ,
email varchar(255) unique not null ,
phone_number varchar(255) not null ,
username varchar(255) unique not null ,
passwords varchar(255) not null , 
role_id int ,
created_at timestamp default current_timestamp , 
last_modified timestamp default current_timestamp on update current_timestamp, 
is_active boolean default true ,
last_loggedin datetime ,
is_deleted boolean default false,
 verification_token varchar(255),
 token_expiration_date timestamp,
constraint user_ROLE_FK foreign key(role_id) references roles(role_id) on update cascade on delete cascade	
 )engine=InnoDB auto_increment=1 default charset=latin1;

 

create table role(
role_id int auto_increment primary key ,
role_name varchar(255) unique not null )engine=InnoDB auto_increment=1 default charset=latin1;
 
  


create table customer(
user_id int primary key  ,
balance decimal(10,2) not null default 0.00, 
constraint CUSTOMER_FK foreign key(user_id)references user(user_id) on update cascade on delete cascade)
engine=InnoDB auto_increment=1 default charset=latin1;

create table address( 
add_id int auto_increment primary key , 
city varchar(255) not null ,
region varchar(255) , 
street varchar(255), 
building_no int 
)engine=InnoDB auto_increment=1 default charset=latin1;

create table customer_address(
user_id int, 
add_id int, 
constraint COUSTOMER_FK foreign key(user_id)references customer(user_id) on update cascade on delete cascade,
constraint ADRESS_FK foreign key(add_id)references address(add_id) on update cascade on delete cascade,
primary key(user_id,add_id));

 create table admin(
user_id int primary key , 
power_description varchar(255) not null default "FULL_ACCESS",
constraint ADMIN_USER foreign key(user_id)references user(user_id)on update cascade on delete cascade) 
engine=InnoDB auto_increment=1 default charset=latin1;
