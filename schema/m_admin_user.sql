
CREATE TABLE m_admin_user (
  id INT PRIMARY KEY,
  name VARCHAR(63) NOT NULL,
  role CHAR(3) NOT NULL,
  credential_encode VARCHAR(127) NOT NULL,
  access_token_aton BIGINT NOT NULL,
  access_token VARCHAR(127) NOT NULL,
  access_token_expiry timestamp NOT NULL,
  update_timestamp timestamp default current_timestamp on update current_timestamp,
  insert_timestamp timestamp default current_timestamp
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
