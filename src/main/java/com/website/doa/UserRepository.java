package com.website.doa;

import com.website.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(nativeQuery = true, value="select  * from users where user_name = :username limit 1")
    User findByUsername(@Param("username") String username);

//    @Query("select * from users where id = :id limit 1")
//    User findById(@Param("id") Long id);

    @Query(nativeQuery = true, value="select count(*) as count from users where email=:email limit 1")
    long existsByEmail(@Param("email") String email);

    @Query(nativeQuery = true, value="select count(*) as count from users where user_name=:username")
    long existsByUsername(@Param("username") String username);

//    @Query("SELECT a FROM Article a WHERE a.title=:title and a.category=:category")
//    List<Article> fetchArticles(@Param("title") String title, @Param("category") String category);
}
