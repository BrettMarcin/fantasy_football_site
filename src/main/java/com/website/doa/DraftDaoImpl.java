package com.website.doa;

import com.website.domains.Draft;
import com.website.domains.Picks;
import com.website.domains.Player;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public class DraftDaoImpl implements DraftDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public long getDraftCreatedByUser(String username) {
        String sql = "select count(*) from draft where user_created = :username";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        BigInteger theInt = (BigInteger)theQ.getSingleResult();
        return theInt.longValue();
    }

    @Override
    public long countNumberOfDrafts() {
        String sql = "select count(id) from draft";
        Query theQ = entityManager.createNativeQuery(sql);
        BigInteger theInt = (BigInteger)theQ.getSingleResult();
        return theInt.longValue();
    }

    @Override
    public List<BigInteger> getDraftsThatAreMoreThanADay() {
        String sql = "select id from draft where id in (select id from draft where was_running = ('no' or was_running = 'paused') and created_at < (now() - interval  24 hour )) or id in (select id from draft where was_running = 'ended' and ended_at < (now() - interval  1 hour ))";
        Query theQ = entityManager.createNativeQuery(sql);
        List<BigInteger> idList = (List<BigInteger>)theQ.getResultList();
        return idList;
    }
    @Override
    public void removeDrafts(List<BigInteger> ids) {
        String sql = "delete from draft where id in :ids";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("ids", ids);
        theQ.executeUpdate();
    }

    @Override
    public void createDraft(Draft draft) {
        // create the draft it self
        StringBuilder sb = new StringBuilder();
        sb.append("insert into draft(draft_started, is_public, user_created) ") ;
        sb.append("VALUES (:draft_started, :is_public, :user_id)");

        Query theQ = entityManager.createNativeQuery(sb.toString());
        theQ.setParameter("draft_started", false);
        theQ.setParameter("is_public", draft.isPublic());
        theQ.setParameter("user_id", draft.getUserCreated().getUsername());
        theQ.executeUpdate();
    }

    @Override
    public void addToInvitedUser(String username, Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into draft_users_invited(invited_user, draft_id) ") ;
        sb.append("values (:userInvited, :draftId)");
        Query theQ = entityManager.createNativeQuery(sb.toString());
        theQ.setParameter("userInvited", username);
        theQ.setParameter("draftId", id);
        theQ.executeUpdate();
    }

    @Override
    public Integer getCreatedDraftId(String username) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id from draft where user_created = :username order by created_at DESC limit 1") ;
        Query theQ = entityManager.createNativeQuery(sb.toString());
        theQ.setParameter("username", username);
        Integer id = (Integer) theQ.getSingleResult();
        return id;
    }

    @Override
    public List<Object> getDraft(int draftId) {
        String sql = "select dua.accepted_user,i.invited_user,d.* from draft as d left join draft_users_invited i on d.id = i.draft_id left join draft_users_accepted dua on d.id = dua.draft_id where d.id = :theId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("theId", draftId);
        List<Object> idList = (List<Object>)theQ.getResultList();

        return idList;
    }

    @Override
    public List<Object> getPublicDrafts() {
        String sql = "select * from draft where is_public = true";
        Query theQ = entityManager.createNativeQuery(sql);
        List<Object> draftList = (List<Object>)theQ.getResultList();
        return draftList;
    }

    @Override
    public List<Object> getPublicDrafts(String username) {
        String sql = "select * from draft where is_public = true and user_created != :username";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        List<Object> draftList = (List<Object>)theQ.getResultList();
        return draftList;
    }

    @Override
    public List<Object> getInvitedDrafts(String username) {
        String sql = "select * from draft where id in (select draft_id from draft_users_invited where invited_user = :user)";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("user", username);
        List<Object> draftList = (List<Object>)theQ.getResultList();
        return draftList;
    }

    @Override
    public List<Object> getDraftsCreated(String username) {
        String sql = "select * from draft where user_created = :username";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        List<Object> draftList = (List<Object>)theQ.getResultList();
        return draftList;
    }

    @Override
    public boolean checkIfUserWasInvited(String username, int draftId) {
        String sql = "select count(*) from draft where id in (select draft_id from draft_users_invited where invited_user = :username and draft_id = :draftId) and draft_started = 0";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        theQ.setParameter("draftId", draftId);
        Integer result = ((Number)theQ.getSingleResult()).intValue();
        return result >= 1;
    }

    @Override
    public void removeInvited(String username, int draftId) {
        String sql = "delete from draft_users_invited where invited_user = :username and draft_id = :draftId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        theQ.setParameter("draftId", draftId);
        theQ.executeUpdate();
    }

    @Override
    public void addToAcceptedUsers(String username, int draftId) {
        String sql = "insert into  draft_users_accepted (accepted_user, draft_id) VALUES (:username, :draftId);";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        theQ.setParameter("draftId", draftId);
        theQ.executeUpdate();
    }

    @Override
    public boolean checkIfDraftOwner(String username, int draftId) {
        String sql = "select count(*) from draft where user_created = :username and id = :draftId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        theQ.setParameter("draftId", draftId);
        Integer result = ((Number)theQ.getSingleResult()).intValue();
        return result == 1;
    }

    @Override
    public boolean checkIfDraftHasStarted(int draftId) {
        String sql = "select count(*) from draft where id = :draftId and draft_started = 1";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        Integer result = ((Number)theQ.getSingleResult()).intValue();
        return result == 1;
    }

    @Override
    public void inviteUser(String username, int draftId) {
        String sql = "insert into draft_users_invited (invited_user, draft_id) values (:username, :draftId)";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        theQ.setParameter("draftId", draftId);
        theQ.executeUpdate();
    }

    @Override
    public void startDraft(int draftId) {
        String sql = "update  draft set draft_started = 1,was_running = 'running' where id = :draftId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        theQ.executeUpdate();
    }

    @Override
    public void insertPick(Picks pick) {
        String sql = "insert into picks(user_name_picks, draft_id_picks, is_used, round, pick_number) values (:user_name, :draftId, 0, :round, :pickNumber)";
        Query theQ = entityManager.createNativeQuery(sql);
        // (:user_name, :draftId, 0, :round, :pickNumber)";
        theQ.setParameter("user_name", pick.getUsername());
        theQ.setParameter("draftId", pick.getDraftId());
        theQ.setParameter("round", pick.getRound());
        theQ.setParameter("pickNumber", pick.getPickNumber());
        theQ.executeUpdate();
    }

    @Override
    public List<Object> getPicks(int draftId) {
        String sql = "select * from picks where draft_id_picks = :draftId and is_used = 0 order by round ASC, pick_number ASC";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        List<Object> pickList = (List<Object>)theQ.getResultList();
        return pickList;
    }

    @Override
    public List<Object> getPlayersRemaining(int draftId) {
        String sql = "SELECT * FROM player WHERE id NOT IN (SELECT player_id_picks FROM picks WHERE draft_id_picks = :draftId AND is_used = 1) AND id > 1;";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        List<Object> pickList = (List<Object>)theQ.getResultList();
        return pickList;
    }

    @Override
    public void checkDraftsThatWereRunning() {
        String sql = "update draft set was_running = 'pause' where was_running = 'running'";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.executeUpdate();
    }

    @Override
    public void resumeDraft(int draftId) {
        String sql = "update draft set was_running = 'running' where id = :draftId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        theQ.executeUpdate();
    }

    @Override
    public void draftPlayer(int playerId, Picks pick) {
        String sql = "update picks set player_id_picks = :playerId , is_used = 1 where draft_id_picks = :draftId and user_name_picks = :username and round = :round and pick_number = :pickNum";
        Query theQ = entityManager.createNativeQuery(sql);
        //where draft_id_picks = :draftId and user_name_picks = :username and round = :round and pick_number = :pickNum
        theQ.setParameter("draftId", pick.getDraftId());
        theQ.setParameter("round", pick.getRound());
        theQ.setParameter("pickNum", pick.getPickNumber());
        theQ.setParameter("username", pick.getUsername());
        theQ.setParameter("playerId", pick.getThePlayer().getId());
        theQ.executeUpdate();
    }

    @Override
    public Object getMostRecentPicksDraft(int draftId) {
        String sql = "select * from picks where pick_number = (select min(pick_number) from picks where round = (select MIN(round) from picks where is_used = 0 and draft_id_picks = :draftId) and draft_id_picks = :draftId and is_used = 0) and round = (select MIN(round) from picks where is_used = 0 and draft_id_picks = :draftId) and draft_id_picks = :draftId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        Object pickList = (Object)theQ.getSingleResult();
        return pickList;
    }

    @Override
    public List<Object> getPickHistory(int draftId) {
        String sql = "select * from picks left join player p on picks.player_id_picks = p.id where is_used = 1 and draft_id_picks = :draftId order by round desc";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        List<Object> pickList = (List<Object>)theQ.getResultList();
        return pickList;
    }

    @Override
    public String getDraftOwner(int draftId) {
        String sql = "select user_created from draft where id = :draftId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        String userOwner = (String)theQ.getSingleResult();
        return userOwner;
    }

    @Override
    public List<Object> getPlayersTeamDrafted(int draftId, String username) {
        String sql = "select * from player where id in (select player_id_picks from picks where user_name_picks = :username and draft_id_picks = :draftId  and player_id_picks != 1) order by rank_player";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        theQ.setParameter("username", username);
        List<Object> pickList = (List<Object>)theQ.getResultList();
        return pickList;
    }


//    public Object ge(int draftId) {
//        String sql = "select * from picks where pick_number = (select min(pick_number) from picks where round = (select MIN(round) from picks where is_used = 0 and draft_id_picks = :draftId) and draft_id_picks = :draftId) and draft_id_picks = :draftId and is_used = 0 and round = (select MIN(round) from picks where is_used = 0 and draft_id_picks = :draftId)";
//        Query theQ = entityManager.createNativeQuery(sql);
//        theQ.setParameter("draftId", draftId);
//        Object thePick = (Object)theQ.getResultList();
//        return thePick;
//    }

    @Override
    public Object[] getLowestRankedPlayer(int draftId) {
        String sql = "SELECT * FROM player WHERE rank_player = (SELECT min(rank_player) FROM player WHERE id NOT IN (SELECT player_id_picks FROM picks WHERE draft_id_picks = :draftId AND is_used = 1) AND id != 1)";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        Object[] thePick = (Object[])theQ.getSingleResult();
        return thePick;
    }//select count(*) from picks where draft_id_picks = 11 and is_used = 0; checkPicksRemaining

    @Override
    public long checkPicksRemaining(int draftId) {
        String sql = "select count(*) from picks where draft_id_picks = :draftId and is_used = 0";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        BigInteger theInt = (BigInteger)theQ.getSingleResult();
        return theInt.longValue();
    } //endDraft
    @Override
    public void endDraft(int draftId) {
        String sql = "update draft set was_running = 'end', ended_at = current_timestamp where id = :draftId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        theQ.executeUpdate();
    }//select id from draft where was_running = 'running'

    @Override
    public List<Integer> getDraftRunning() {
        String sql = "select id from draft where was_running = 'running'";
        Query theQ = entityManager.createNativeQuery(sql);
        List<Integer> listOfIds = (List<Integer>)theQ.getResultList();
        return listOfIds;
    }


    //    public List<Draft> getDraftAvailability() {
//        String sql = "select dua.accepted_user,i.invited_user,d.* from draft as d left join draft_users_invited i on d.id = i.draft_id left join draft_users_accepted dua on d.id = dua.draft_id where d.id = :theId";
//        Query theQ = entityManager.createNativeQuery(sql);
//        theQ.setParameter("theId", draftId);
//        List<Object> idList = (List<Object>)theQ.getResultList();
//
//        return idList;
//    }
}
