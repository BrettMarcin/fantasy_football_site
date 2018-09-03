package com.website.Doa;

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
        String sql = "select * from user where name = :name";
        Query theQ = entityManager.createNativeQuery(sql, User.class);
        theQ.setParameter("name", username);
        return (User)theQ.getSingleResult();
    }
}
