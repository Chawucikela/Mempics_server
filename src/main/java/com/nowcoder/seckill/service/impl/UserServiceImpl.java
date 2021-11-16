package com.nowcoder.seckill.service.impl;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.component.ObjectValidator;
import com.nowcoder.seckill.dao.RelationshipMapper;
import com.nowcoder.seckill.dao.UserMapper;
import com.nowcoder.seckill.entity.Relationship;
import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.entity.resultentity.UserResult;
import com.nowcoder.seckill.entity.resultentity.UserSimplifiedResult;
import com.nowcoder.seckill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService, ErrorCode {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Autowired
    private ObjectValidator validator;

    @Transactional
    public void register(User user) {
        if (user == null) {
            throw new BusinessException(PARAMETER_ERROR, "参数不能为空！");
        }

        Map<String, String> result = validator.validate(user);
        if (result != null && result.size() > 0) {
            throw new BusinessException(PARAMETER_ERROR,
                    StringUtils.join(result.values().toArray(), ", ") + "！");
        }

        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(PARAMETER_ERROR, "该手机号或用户名已注册！");
        }
    }

    public User login(String phone, String password) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(PARAMETER_ERROR, "参数不合法！");
        }

        User user = userMapper.selectByPhone(phone);
        if (user == null || !StringUtils.equals(password, user.getPassword())) {
            throw new BusinessException(USER_LOGIN_FAILURE, "账号或密码错误！");
        }


        return user;
    }

    public User findUserById(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new BusinessException(USER_NOT_FOUND, "找不到用户！");
        }
        return user;
    }

    public UserResult findUserDetailById(int id) {
        UserResult user = userMapper.selectUserDetailByPrimaryKey(id);
        if (user == null) {
            throw new BusinessException(USER_NOT_FOUND, "找不到用户！");
        }
        return user;
    }

    @Transactional
    public void addRelationship(int userId, int followingUserId) {
        if (userId == followingUserId) {
            throw new BusinessException(UNDEFINED_ERROR, "不能关注自己！");
        }
        User user = userMapper.selectByPrimaryKey(followingUserId);
        if (user == null) {
            throw new BusinessException(USER_NOT_FOUND, "找不到用户！");
        }
        Relationship existRelationship = relationshipMapper.selectByUserIds(userId, followingUserId);
        if (existRelationship != null) {
            throw new BusinessException(UNDEFINED_ERROR, "已关注！");
        }
        Relationship relationship = new Relationship();
        relationship.setUserId(userId);
        relationship.setFollowingUserId(followingUserId);
        relationship.setFollowingTime(new Timestamp(System.currentTimeMillis()));
        relationshipMapper.insert(relationship);
    }

    @Transactional
    public void deleteRelationship(int userId, int followingUserId) {
        int result = relationshipMapper.deleteByUserIds(userId, followingUserId);
        if (result == 0) {
            throw new BusinessException(UNDEFINED_ERROR, "未找到关系！");
        }
    }

    public List<UserResult> getFollowingUserList(int userId) {
//        List<UserResult> resultSet= new ArrayList<UserResult>();
        List<UserResult> resultSet = relationshipMapper.selectFollowingUser(userId);
//        for (Relationship relationship : relationships) {
//            UserResult user = userMapper.selectByPrimaryKeySimplified(relationship.getFollowingUserId());
//            resultSet.add(user);
//        }
        return resultSet;
    }

    public List<UserResult> getFollowerUserList(int followingUserId) {
//        List<UserResult> resultSet= new ArrayList<UserResult>();
        List<UserResult> resultSet = relationshipMapper.selectFollowerUser(followingUserId);
//        for (Relationship relationship : relationships) {
//            UserResult user = userMapper.selectByPrimaryKeySimplified(relationship.getUserId());
//            resultSet.add(user);
//            System.out.println(user.getClass().equals(UserSimplifiedResult.class));
//        }
        return resultSet;
    }

    public List<UserResult> searchByPhone(String phone) {
        List<UserResult> resultSet = userMapper.searchByPhoneSimplified(phone);
        return resultSet;
    }

    public List<UserResult> searchByNickname(String nickname) {
        List<UserResult> resultSet = userMapper.searchByNicknameSimplified(nickname);
        return resultSet;
    }

    public List<UserResult> searchByUsername(String username) {
        List<UserResult> resultSet = userMapper.searchByUsernameSimplified(username);
        return resultSet;
    }

}
