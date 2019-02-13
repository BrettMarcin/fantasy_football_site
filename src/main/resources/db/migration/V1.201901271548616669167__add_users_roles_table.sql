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