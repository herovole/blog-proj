
CREATE TABLE a_source_comment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  comment_id BIGINT not null,
  article_id BIGINT not null,
  comment_text TEXT,
  iso_2 CHAR(2),
  is_hidden TINYINT(1) NOT NULL DEFAULT 0,
  referring_comment_ids VARCHAR(127),

  update_timestamp timestamp default current_timestamp on update current_timestamp,
  insert_timestamp timestamp default current_timestamp,
  delete_flag TINYINT(1) NOT NULL DEFAULT 0,

  FOREIGN KEY (article_id) REFERENCES a_article(id) ON DELETE CASCADE,
  FOREIGN KEY (iso_2) REFERENCES m_country(iso_2) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
