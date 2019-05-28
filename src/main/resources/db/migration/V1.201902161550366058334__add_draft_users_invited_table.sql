drop table if exists draft_users_invited;

Create table draft_users_invited (
	users_name VARCHAR(100) not null,
	draft_id int not null,
	PRIMARY KEY (users_name, draft_id),
	CONSTRAINT draft_user_accepted_ibfk_3 FOREIGN KEY (users_name) REFERENCES users(user_name),
	CONSTRAINT draft_user_accepted_ibfk_4 FOREIGN KEY (draft_id) REFERENCES draft(id)
);
