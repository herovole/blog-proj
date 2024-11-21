
CREATE TABLE a_article_has_editor (
  id INT PRIMARY KEY,
  article_id INT not null,
  editor_id INT not null,
  insert_timestamp timestamp default current_timestamp,

  FOREIGN KEY (article_id) REFERENCES a_article(id) ON DELETE CASCADE,
  FOREIGN KEY (editor_id) REFERENCES m_editor(id) ON DELETE CASCADE

) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
