drop table if exists draft_users_accepted;

Create table draft_users_accepted (
	accepted_user VARCHAR(100) not null,
	draft_id int not null,
	PRIMARY KEY (accepted_user, draft_id),
	CONSTRAINT draft_user_accepted_ibfk_1 FOREIGN KEY (accepted_user) REFERENCES users(user_name),
	CONSTRAINT draft_user_accepted_ibfk_2 FOREIGN KEY (draft_id) REFERENCES draft(id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);
