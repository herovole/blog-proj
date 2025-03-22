
CREATE TABLE e_article_access (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  article_id BIGINT,
  public_user_id BIGINT,
  aton BIGINT,
  access_timestamp Timestamp default current_timestamp,

  FOREIGN KEY (article_id) REFERENCES a_article(id) ON DELETE CASCADE,
  FOREIGN KEY (public_user_id) REFERENCES e_public_user(id) ON DELETE CASCADE,
  INDEX idx_article_access_timestamp (access_timestamp)

) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
