package com.website.doa;

import com.website.domains.Draft;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
public class DraftDaoImpl implements DraftDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public long getDraftCreatedByUser(int userId) {
        String sql = "select count(id) from draft where user_id = :userId";
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
        entityManager.persist( draft );
    }
}
