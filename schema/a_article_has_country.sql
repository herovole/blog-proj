
CREATE TABLE a_article_has_country (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  article_id BIGINT not null,
  iso_2 CHAR(2) not null,
  insert_timestamp timestamp default current_timestamp,

  FOREIGN KEY (article_id) REFERENCES a_article(id) ON DELETE CASCADE,
  FOREIGN KEY (iso_2) REFERENCES m_country(iso_2) ON DELETE CASCADE

) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
