drop table if exists draft;

Create table draft (
	id int AUTO_INCREMENT PRIMARY KEY,
	created_at timestamp default current_timestamp,
	draft_started tinyint(1) NOT NULL,
	is_public tinyint(1) NOT NULL,
	user_id int,
	KEY user_id (user_id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

-- drop table if exists users_roles;

-- Create table users_roles (
-- 	  user_id int,
--     role_id int,
--     CONSTRAINT `Constr_Membership_User_fk`
--         FOREIGN KEY `User_fk` (`user_id`) REFERENCES `users` (`id`)
--         ON DELETE CASCADE ON UPDATE CASCADE,
--     CONSTRAINT `Constr_Membership_Role_fk`
--         FOREIGN KEY `Role_fk` (`role_id`) REFERENCES `app_role` (`id`)
--         ON DELETE CASCADE ON UPDATE CASCADE
-- );

-- create INDEX role_ind on users(role_id);
-- alter table users ADD CONSTRAINT `fk_role` FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE;