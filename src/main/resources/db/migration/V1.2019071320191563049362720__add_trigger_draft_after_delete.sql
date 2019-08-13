delimiter #

create trigger delete_picks_after_delete_draft_trig before delete on draft
for each row
  begin
    delete from picks where draft_id_picks = old.id;
  end#

delimiter ;

delimiter #

create procedure select_notfication(IN user char(20))
  begin
    select * from notification where user_belongs = user;
    update notification set has_been_read = 1 where user_belongs = user;
  end#

delimiter ;