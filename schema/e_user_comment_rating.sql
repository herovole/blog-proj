
CREATE TABLE e_user_comment_rating (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  comment_serial_number BIGINT NOT NULL,

  public_user_id BIGINT NOT NULL,
  aton BIGINT,

  rating TINYINT(1) NOT NULL,

  update_timestamp timestamp default current_timestamp on update current_timestamp,
  insert_timestamp timestamp default current_timestamp,
  delete_flag TINYINT(1) NOT NULL DEFAULT 0,

  FOREIGN KEY (comment_serial_number) REFERENCES e_user_comment(id) ON DELETE CASCADE,
  FOREIGN KEY (public_user_id) REFERENCES e_public_user(id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
