package com.website.doa;

import com.website.domains.Notification;
import com.website.domains.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addUser(User theUser) {
        entityManager.persist( theUser );
    }

    @Override
    public List<String> getUserNames(){
        String sql = "select user_name from users";
        Query theQ = entityManager.createNativeQuery(sql);
        return (List<String>)theQ.getResultList();
    }

    @Override
    public User findByUsername(String username){
        String sql = "select * from users where user_name = :name";
        Query theQ = entityManager.createNativeQuery(sql, User.class);
        theQ.setParameter("name", username);
        return (User)theQ.getSingleResult();
    }
    @Override
    public User findById(Long id) {
        String sql = "select * from users where id = :id";
        Query theQ = entityManager.createNativeQuery(sql, User.class);
        theQ.setParameter("id", id);
        return (User)theQ.getSingleResult();
    }

    //select count(*) as count from users where name='Brett';
    @Override
    public boolean existsByEmail(String email) {
        String sql = "select count(*) as count from users where email=:email";
        Query theQ = entityManager.createNativeQuery(sql, User.class);
        theQ.setParameter("email", email);
        int result = theQ.getMaxResults();
        return result >= 1;
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "select count(*) from users where user_name=:username";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        Integer result = ((Number)theQ.getSingleResult()).intValue();
        return result >= 1;
    }

    @Override
    public List<String> getInvitedUsers(Integer id) {
        String sql = "select invited_user from draft_users_invited where draft_id=:draftID";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftID", id);
        List<String> result  = (List<String>)theQ.getResultList();
        return result;
    }

    @Override
    public List<String> getAcceptedUsers(Integer id) {
        String sql = "select accepted_user from draft_users_accepted where draft_id=:draftID";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("draftID", id);
        List<String> result  = (List<String>)theQ.getResultList();
        return result;
    }

    @Override
    public long getNumberOfNotification(String username) {
        String sql = "select count(*) from notification where user_belongs = :username and has_been_read = 0";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        BigInteger theInt = (BigInteger)theQ.getSingleResult();
        return theInt.longValue();
    }

    @Override
    public List<Object> getNotifications(String username) {
        String sql = "call select_notfication(:username)";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("username", username);
        List<Object> not = (List<Object>)theQ.getResultList();
        return not;
    }

    @Override
    public void deleteNotification(Notification not) {
        String sql = "delete from notification where content = :content and user_belongs = :theUser and draft_id = :draft";
        Query theQ = entityManager.createNativeQuery(sql);
        theQ.setParameter("content", not.getContent());
        theQ.setParameter("theUser", not.getUserBelongs());
        theQ.setParameter("draft", not.getDraftId());
//        theQ.setParameter("has_read", not.getUserBelongs());
        theQ.executeUpdate();
    }
}
