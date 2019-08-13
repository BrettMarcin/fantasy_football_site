drop table if exists picks;

Create table picks (
  user_name_picks VARCHAR(100) not null,
  draft_id_picks int not null,
  player_id_picks int DEFAULT 1,
  is_used tinyint(1) NOT NULL,
  round int NOT NULL,
  pick_number int NOT NULL,
  PRIMARY KEY (user_name_picks, draft_id_picks, player_id_picks, round, pick_number),
  CONSTRAINT picks_ibfk_1 FOREIGN KEY (user_name_picks) REFERENCES users(user_name),
  CONSTRAINT picks_ibfk_2 FOREIGN KEY (draft_id_picks) REFERENCES draft(id),
  CONSTRAINT picks_ibfk_3 FOREIGN KEY (player_id_picks) REFERENCES player(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);