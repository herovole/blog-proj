
CREATE TABLE e_user_comment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  comment_id INT not null,
  article_id BIGINT not null,
  handle_name VARCHAR(63) not null,
  comment_text TEXT not null,
  is_hidden TINYINT(1) NOT NULL DEFAULT 0,
  referring_comment_ids VARCHAR(127),

  daily_user_id CHAR(9),
  user_id BIGINT NOT NULL,
  aton BIGINT,

  update_timestamp timestamp default current_timestamp on update current_timestamp,
  insert_timestamp timestamp default current_timestamp,
  delete_flag TINYINT(1) NOT NULL DEFAULT 0,

  FOREIGN KEY (article_id) REFERENCES a_article(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES e_public_user(id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
