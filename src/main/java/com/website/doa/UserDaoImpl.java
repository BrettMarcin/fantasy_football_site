package com.website.doa;

import com.website.domains.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addUser(User theUser) {
        entityManager.persist( theUser );
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
        return result == 1;
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "select count(*) as count from users where username=:username";
        Query theQ = entityManager.createNativeQuery(sql, User.class);
        theQ.setParameter("username", username);
        int result = theQ.getMaxResults();
        return result == 1;
    }
}
