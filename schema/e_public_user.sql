
CREATE TABLE e_public_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  uu_id CHAR(36) not null,
  banned_until timestamp default null,

  update_timestamp timestamp default current_timestamp on update current_timestamp,
  insert_timestamp timestamp default current_timestamp

) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
