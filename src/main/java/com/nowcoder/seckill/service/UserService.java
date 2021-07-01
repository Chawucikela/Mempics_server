package com.nowcoder.seckill.service;

import com.nowcoder.seckill.entity.Relationship;
import com.nowcoder.seckill.entity.User;

import java.util.List;

public interface UserService {

    void register(User user);

    User login(String phone, String password);

    User findUserById(int id);

    User findUserdetailById(int id);

    void addRelationship(int userId, int followingUserId);

    void deleteRelationship(int userId, int followingUserId);

    List<User> getFollowingUserList(int usreId);

    List<User> getFollowerUserList(int followingUserId);

    List<User> searchByPhone(String phone);

    List<User> searchByNickname(String nickname);
}
