
CREATE TABLE a_article_has_topic_tag (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  article_id BIGINT not null,
  topic_tag_id INT not null,
  insert_timestamp timestamp default current_timestamp,

  FOREIGN KEY (article_id) REFERENCES a_article(id) ON DELETE CASCADE,
  FOREIGN KEY (topic_tag_id) REFERENCES a_topic_tag(id) ON DELETE CASCADE

) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
