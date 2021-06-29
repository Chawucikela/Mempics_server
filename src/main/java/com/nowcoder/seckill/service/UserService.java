package com.nowcoder.seckill.service;

import com.nowcoder.seckill.entity.Relationship;
import com.nowcoder.seckill.entity.User;

import java.util.List;

public interface UserService {

    void register(User user);

    User login(String phone, String password);

    User findUserById(int id);

    void addRelationship(int userId, int followingUserId);

    void deleteRelationship(int userId, int followingUserId);

    List<Relationship> getFollowingUserList(int usreId);

    List<Relationship> getFollowerUserList(int followingUserId);
}
