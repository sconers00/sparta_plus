/*테스트코드용 테이블 제작 sql입니다. 자동으로 테스트테이블 안지워서 drop table if exist {table} cascade 써주세요*/
/*테스트 사용시 주석을 풀어서 사용해주심 됩니다 - 운용시 주석 풀려있으면 warn 여럿 뜹니다*/
/*drop table if exists users cascade;
drop table if exists products cascade;
drop table if exists orders cascade;
drop table if exists reports cascade;
drop table if exists reviews cascade;

  create table users(
  id Bigint auto_increment primary key,
  email varchar(30) not_null,
  name varchar(30) not_null,
  nickname varchar(30) unique not_null,
  password varchar(255) not_null,
  phone varchar(30) unique not_null
  userRole varchar(30) not_null
  idDeleted tinyint(1),
  createdAt timestamp,
  updatedAt timestamp
  );

  create table products(
  id Bigint auto_increment primary key,
  category varchar(30) not_null,
  name varchar(30) notnull,
  content varchar(255),
  price int not_null,
  quantity int not_null,
  user_id Bigint not_null,
  isDeleted tinyint(1),
  createdAt timestamp,
  updatedAt timestamp,
  Foreign key (user_id) references user(id)
  );

  create table orders(
  id Bigint auto_increment primary key,
  paymentMethod varchar(30) not_null,
  quantity int not_null,
  totalPrice int not_null,
  address varchar(255) not_null,
  orderStatus varchar(30) not_null,
  user_id Bigint not_null,
  product_id Bigint not_null,
  createdAt timestamp,
  updatedAt timestamp,
  Foreign key (user_id) references user(id),
  Foreign key (product_id) references product(id)
  );

  create table reports(
  id Bigint auto_increment primary key,
  content varchar(255) not_null,
  reportType varchar(30) not_null,
  reporter_id Bigint not_null,
  product_id Bigint not_null,
  createdAt timestamp,
  updatedAt timestamp,
  Foreign key reporter_id references user(id),
  Foreign key product_id references product(id)
  );

  create table reivew(
  id Bigint auto_increment primary key
  content varchar(255),
  score int not_null,
  user_id Bigint not_null,
  order_id Bigint not_null,
  product_id Bigint not_null,
  createdAt timestamp,
  updatedAt timestamp,
  Foreign key order_id references order(id)
  );
*/
