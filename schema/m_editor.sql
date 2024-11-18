
CREATE TABLE m_editor (
  id INT PRIMARY KEY,
  name VARCHAR(63) NOT NULL,
  update_timestamp timestamp default current_timestamp on update current_timestamp,
  insert_timestamp timestamp default current_timestamp
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
