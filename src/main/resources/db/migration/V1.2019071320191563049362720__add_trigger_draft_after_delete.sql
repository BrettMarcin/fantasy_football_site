delimiter #

create trigger delete_picks_after_delete_draft_trig before delete on draft
for each row
  begin
    delete from picks where draft_id_picks = old.id;
  end#

delimiter ;