Create table notification (
	content VARCHAR(300) NOT NULL,
	user_belongs VARCHAR(100) NOT NULL,
	draft_id int,
  has_been_read tinyint(1) NOT NULL
);


delimiter #

create trigger notification_after_ins_trig after insert on draft_users_invited
for each row
  begin
    DECLARE x VARCHAR(300);
    SET x = CONCAT_WS(' ','User: ',(select user_created from draft where id = new.draft_id), ' has invited you to his draft');
    insert into notification (content, user_belongs, draft_id, has_been_read) values (x, new.invited_user, new.draft_id, false);
  end#

create trigger notification_update_accepted_user_trig after insert on draft_users_accepted
for each row
  begin
    DECLARE x VARCHAR(300);
    DECLARE user_created_og VARCHAR(100);
    SET x = CONCAT_WS(' ','User: ',(select user_name from users where user_name = new.accepted_user), ' has accepted your draft invite');
    SET user_created_og = CONCAT_WS('', (select user_created from draft where id = new.draft_id));
    insert into notification (content, user_belongs, draft_id, has_been_read) values (x, user_created_og, new.draft_id, false);
  end#

create trigger notification_after_delete_users_trig after delete on users
for each row
  begin
    delete from notification where user_belongs = old.user_name;
  end#

delimiter ;

delimiter #



CREATE TRIGGER notification_update_draft_start_trig BEFORE UPDATE ON draft
FOR EACH ROW
  BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE my_value VARCHAR(300);
    DECLARE invited_user_value VARCHAR(300);
    DECLARE cur1 CURSOR FOR select accepted_user from draft_users_accepted where draft_id = new.id;
    DECLARE cur2 CURSOR FOR select invited_user from draft_users_invited where draft_id = new.id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


    open cur1;
    IF NEW.draft_started != OLD.draft_started THEN
      my_loop: loop
        FETCH cur1 into my_value;
        if done then
          leave my_loop;
        end if;
        insert into notification (content, user_belongs, draft_id, has_been_read) values (CONCAT_WS(' ','A Draft has been started, the one that user: ',(select user_created from draft where id = NEW.id), ' invited you'), my_value, new.id, false);
      end loop;
    END IF;
    close cur1;
    -- Remove invited users
    open cur2;
    IF NEW.draft_started <> OLD.draft_started THEN
      my_loop: loop
        FETCH cur2 into invited_user_value;
        if done then
          leave my_loop;
        end if;
        delete from draft_users_invited where invited_user = invited_user_value and draft_id =  NEW.id;
      end loop;
    END IF;
    close cur2;
  END#

delimiter ;


