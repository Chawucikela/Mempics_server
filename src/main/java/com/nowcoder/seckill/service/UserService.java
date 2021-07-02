package com.nowcoder.seckill.service;

import com.nowcoder.seckill.entity.Relationship;
import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.entity.resultentity.UserResult;

import java.util.List;

public interface UserService {

    void register(User user);

    User login(String phone, String password);

    User findUserById(int id);

    UserResult findUserDetailById(int id);

    void addRelationship(int userId, int followingUserId);

    void deleteRelationship(int userId, int followingUserId);

    List<UserResult> getFollowingUserList(int usreId);

    List<UserResult> getFollowerUserList(int followingUserId);

    List<UserResult> searchByPhone(String phone);

    List<UserResult> searchByNickname(String nickname);

    List<UserResult> searchByUsername(String username);
}
