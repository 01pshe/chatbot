package com.vol.chatbot.userRepository;

import com.vol.chatbot.model.UserInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserInfoRepositoryImpl implements UserInfoRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<UserInfo> getAll() {
    return entityManager.createNamedQuery(UserInfo.ALL_SORTED, UserInfo.class).getResultList();
  }
}
