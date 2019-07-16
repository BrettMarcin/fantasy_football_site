delimiter #

create trigger delete_rows_after_draft_delete_trig after delete on draft
for each row
  begin
    delete from draft_users_accepted where draft_id = old.id;
    delete from draft_users_invited where draft_id = old.id;
    delete from notification where draft_id = old.id;
  end#

CREATE TRIGGER delete_invited_users_when_draft_start_trig BEFORE UPDATE ON draft
FOR EACH ROW
  BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE invited_user_value VARCHAR(300);
    DECLARE cur2 CURSOR FOR select invited_user from draft_users_invited where draft_id = new.id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


    open cur2;
    IF NEW.draft_started <> OLD.draft_started THEN
      my_loop: loop
        FETCH cur2 into invited_user_value;
        if done then
          leave my_loop;
        end if;
        delete from draft_users_invited where invited_user = invited_user_value and draft_id = NEW.id;
      end loop;
    END IF;
    close cur2;
  end#

delimiter ;

# delimiter #
#
#
# delimiter ;