package com.vol.chatbot.dao;

import com.vol.chatbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserDao extends JpaRepository<User, Long> {
    User findBySignature(String signature);

    Set<User> getAllByIdNotNull();

    User getById(Long id);

    @Query("select chatId from User")
    Set<Long> getAllUsersChatId();

    @Query("select chatId from User where upper(userFirstName) in :firstNameSet")
    Set<Long> getAllUsersChatIdByFirstNameSet(@Param("firstNameSet") Set<String> firstName);

    @Query("select chatId from User where id in :userIdSet")
    Set<Long> getAllUsersChatIdByIdSet(@Param("userIdSet") Set<Long> id);

    @Query("select u from User u order by u.totalResult desc ")
    List<User> getAllSort();


}
