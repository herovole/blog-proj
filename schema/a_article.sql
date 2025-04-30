
CREATE TABLE a_article (
  id BIGINT PRIMARY KEY,
  title VARCHAR(127),
  text TEXT,
  image_name VARCHAR(63),
  source_title VARCHAR(127),
  source_page VARCHAR(255),
  source_date DATE,
  is_published TINYINT(1) NOT NULL DEFAULT 0,
  registration_timestamp timestamp default current_timestamp,
  update_timestamp timestamp default current_timestamp on update current_timestamp,
  insert_timestamp timestamp default current_timestamp,
  delete_flag TINYINT(1) NOT NULL DEFAULT 0,

  INDEX idx_article_title (title),
  INDEX idx_article_timestamp (registration_timestamp)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
