
CREATE TABLE a_topic_tag (
  id INT PRIMARY KEY,
  name_en VARCHAR(31) NOT NULL,
  name_ja VARCHAR(15) NOT NULL,
  update_timestamp timestamp default current_timestamp on update current_timestamp,
  insert_timestamp timestamp default current_timestamp,
  delete_flag TINYINT(1) NOT NULL DEFAULT 0
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO a_topic_tag (id,name_en,name_ja) values ('1','test','テスト');
INSERT INTO a_topic_tag (id,name_en,name_ja) values ('2','news','時事');
INSERT INTO a_topic_tag (id,name_en,name_ja) values ('3','society','社会');
INSERT INTO a_topic_tag (id,name_en,name_ja) values ('4','country','国');
