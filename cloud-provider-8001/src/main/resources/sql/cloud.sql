create table t_cloud_payment (
  id varchar(32),
  code varchar(100),
  amount numeric,
  primary key (id)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;