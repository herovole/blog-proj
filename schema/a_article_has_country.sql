
CREATE TABLE a_article_has_country (
  id INT PRIMARY KEY,
  article_id INT not null,
  iso_2 CHAR(2) not null,
  update_timestamp timestamp default current_timestamp on update current_timestamp,
  insert_timestamp timestamp default current_timestamp,
  delete_flag TINYINT(1) NOT NULL DEFAULT 0,

  FOREIGN KEY (article_id) REFERENCES a_article(id) ON DELETE CASCADE,
  FOREIGN KEY (iso_2) REFERENCES m_country(iso_2) ON DELETE CASCADE

) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
