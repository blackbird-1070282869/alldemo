package com.test.alldemo.service;

public interface UserService {
    String getVerifyHash(Integer sid, Integer userId);

    int addUserCount(Integer userId);

    boolean getUserIsBanned(Integer userId);
}
