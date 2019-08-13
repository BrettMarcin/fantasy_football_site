drop table if exists draft_users_invited;

Create table draft_users_invited (
	invited_user VARCHAR(100) not null,
	draft_id int not null,
	PRIMARY KEY (invited_user, draft_id),
	CONSTRAINT draft_user_invited_ibfk_1 FOREIGN KEY (invited_user) REFERENCES users(user_name),
	CONSTRAINT draft_user_invited_ibfk_2 FOREIGN KEY (draft_id) REFERENCES draft(id)
	  ON DELETE CASCADE
	  ON UPDATE CASCADE
);