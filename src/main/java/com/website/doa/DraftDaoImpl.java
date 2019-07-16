package com.website.doa;

import com.website.domains.Draft;
import com.website.domains.Picks;
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
    public long getDraftCreatedByUser(int userId) {
        String sql = "select count(id) from draft where user_created = :userId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("userId", userId);
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
        String sql = "SELECT id FROM draft WHERE draft_started = 0 and created_at > timestampadd(hour, 24, now())";
        Query theQ = entityManager.createNativeQuery(sql);
        List<BigInteger> idList = (List<BigInteger>)theQ.getResultList();
        return idList;
    }

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

    public void addToInvitedUser(String username, Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into draft_users_invited(invited_user, draft_id) ") ;
        sb.append("values (:userInvited, :draftId)");
        Query theQ = entityManager.createNativeQuery(sb.toString());
        theQ.setParameter("userInvited", username);
        theQ.setParameter("draftId", id);
        theQ.executeUpdate();
    }

    public Integer getCreatedDraftId(String username) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id from draft where user_created = :username order by created_at DESC limit 1") ;
        Query theQ = entityManager.createNativeQuery(sb.toString());
        theQ.setParameter("username", username);
        Integer id = (Integer) theQ.getSingleResult();
        return id;
    }

    public List<Object> getDraft(int draftId) {
        String sql = "select dua.accepted_user,i.invited_user,d.* from draft as d left join draft_users_invited i on d.id = i.draft_id left join draft_users_accepted dua on d.id = dua.draft_id where d.id = :theId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("theId", draftId);
        List<Object> idList = (List<Object>)theQ.getResultList();

        return idList;
    }

    public List<Object> getPublicDrafts() {
        String sql = "select * from draft where is_public = true";
        Query theQ = entityManager.createNativeQuery(sql);
        List<Object> draftList = (List<Object>)theQ.getResultList();
        return draftList;
    }

    public List<Object> getPublicDrafts(String username) {
        String sql = "select * from draft where is_public = true and user_created != :username";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        List<Object> draftList = (List<Object>)theQ.getResultList();
        return draftList;
    }

    public List<Object> getInvitedDrafts(String username) {
        String sql = "select * from draft where id in (select draft_id from draft_users_invited where invited_user = :user)";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("user", username);
        List<Object> draftList = (List<Object>)theQ.getResultList();
        return draftList;
    }

    public List<Object> getDraftsCreated(String username) {
        String sql = "select * from draft where user_created = :username";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        List<Object> draftList = (List<Object>)theQ.getResultList();
        return draftList;
    }

    public boolean checkIfUserWasInvited(String username, int draftId) {
        String sql = "select count(*) from draft where id in (select draft_id from draft_users_invited where invited_user = :username and draft_id = :draftId) and draft_started = 0";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        theQ.setParameter("draftId", draftId);
        Integer result = ((Number)theQ.getSingleResult()).intValue();
        return result >= 1;
    }

    public void removeInvited(String username, int draftId) {
        String sql = "delete from draft_users_invited where invited_user = :username and draft_id = :draftId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        theQ.setParameter("draftId", draftId);
        theQ.executeUpdate();
    }

    public void addToAcceptedUsers(String username, int draftId) {
        String sql = "insert into  draft_users_accepted (accepted_user, draft_id) VALUES (:username, :draftId);";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        theQ.setParameter("draftId", draftId);
        theQ.executeUpdate();
    }

    public boolean checkIfDraftOwner(String username, int draftId) {
        String sql = "select count(*) from draft where user_created = :username and id = :draftId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        theQ.setParameter("draftId", draftId);
        Integer result = ((Number)theQ.getSingleResult()).intValue();
        return result == 1;
    }

    public boolean checkIfDraftHasStarted(int draftId) {
        String sql = "select count(*) from draft where id = :draftId and draft_started = 1";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        Integer result = ((Number)theQ.getSingleResult()).intValue();
        return result == 1;
    }

    public void inviteUser(String username, int draftId) {
        String sql = "insert into draft_users_invited (invited_user, draft_id) values (:username, :draftId)";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        theQ.setParameter("draftId", draftId);
        theQ.executeUpdate();
    }

    public void startDraft(int draftId) {
        String sql = "update  draft set draft_started = 1 where id = :draftId";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        theQ.executeUpdate();
    }

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

    public List<Object> getPicks(int draftId) {
        String sql = "select * from picks where draft_id_picks = :draftId and is_used = 0 order by round ASC;";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftId", draftId);
        List<Object> pickList = (List<Object>)theQ.getResultList();
        return pickList;
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
