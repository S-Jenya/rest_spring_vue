package com.stp.rest_spring_vue.repository;

import com.stp.rest_spring_vue.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.name = ?1 where u.id_user = ?2")
    void updUser(String userName, Long userId);

    @Query("select u from User u")
    List<User> findAllByCustomQuery();

    @Query("select u from User u where u.id_user = ?1")
    User getUserInfo(Long userId);

    Optional<User> findByName(String s);


    @Query("select u from User u where u.name = ?1")
    User findByNameCustomQuery(String s);
}
