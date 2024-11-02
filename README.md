# web2-ifix
Web system for IT service orders using Java, JSP, Servlets, SQL, and TailwindCSS.

### Database Schema
```sql
create database ifix;
use ifix;

create table addresses (
  address_id bigint auto_increment
  ,street varchar(50) not null
  ,number varchar(10) not null
  ,complement varchar(50)
  ,neighborhood varchar(50) not null
  ,cep varchar(10) not null
  ,city varchar(50) not null
  ,state varchar(50) not null
  ,primary key (address_id)
) engine=InnoDB default charset=utf8mb4;

create table customers (
  customer_id bigint auto_increment
  ,name varchar(50) not null
  ,phone varchar(50) not null
  ,email varchar(50) not null
  ,cpf varchar(11) not null unique
  ,active boolean not null
  ,address_id bigint not null 
  ,primary key (customer_id)
  ,foreign key (address_id)
     references addresses(address_id)
) engine=InnoDB default charset=utf8mb4;

create table payment_methods (
  id bigint auto_increment
  ,name varchar(50) not null
  ,primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table service_orders (
  service_order_id bigint auto_increment
  ,description varchar(255) not null
  ,emission_date date not null
  ,finished_date date not null
  ,value decimal(10,2) not null
  ,observation varchar(255)
  ,customer_id bigint not null
  ,payment_method_id bigint not null
  ,status varchar(50) not null
  ,primary key (service_order_id)
  ,foreign key (customer_id)
    references customers(customer_id)
  ,foreign key (payment_method_id)
    references payment_methods(id)
) engine=InnoDB default charset=utf8mb4;
