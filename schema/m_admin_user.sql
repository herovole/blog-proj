
CREATE TABLE m_admin_user (
  id INT PRIMARY KEY,
  name VARCHAR(63) NOT NULL,
  email VARCHAR(63) NOT NULL,
  role CHAR(3) NOT NULL,
  credential_encode VARCHAR(255) NOT NULL,
  verification_code CHAR(6) NULL,
  verification_code_expiry timestamp NULL,
  access_token_aton BIGINT NULL,
  access_token VARCHAR(255) NULL,
  access_token_expiry timestamp NULL,
  update_timestamp timestamp default current_timestamp on update current_timestamp,
  insert_timestamp timestamp default current_timestamp
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
