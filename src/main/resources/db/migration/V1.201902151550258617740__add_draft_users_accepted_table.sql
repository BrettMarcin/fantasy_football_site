drop table if exists draft_users_accepted;

Create table draft_users_accepted (
	users_name VARCHAR(100) not null,
	draft_id int not null,
	PRIMARY KEY (users_name, draft_id),
	CONSTRAINT draft_user_accepted_ibfk_1 FOREIGN KEY (users_name) REFERENCES users(user_name),
	CONSTRAINT draft_user_accepted_ibfk_2 FOREIGN KEY (draft_id) REFERENCES draft(id)
);
