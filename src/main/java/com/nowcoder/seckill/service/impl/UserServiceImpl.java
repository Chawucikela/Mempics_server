package com.nowcoder.seckill.service.impl;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.component.ObjectValidator;
import com.nowcoder.seckill.dao.RelationshipMapper;
import com.nowcoder.seckill.dao.UserMapper;
import com.nowcoder.seckill.entity.Relationship;
import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.entity.resultentity.UserResult;
import com.nowcoder.seckill.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
	public void addRelationship(int userId, int targetUserId) {
		if (userId == targetUserId) {
			throw new BusinessException(UNDEFINED_ERROR, "不能关注自己！");
		}
		User user = userMapper.selectByPrimaryKey(targetUserId);
		if (user == null) {
			throw new BusinessException(USER_NOT_FOUND, "用户不存在！");
		}
		Relationship existRelationship = relationshipMapper.selectByUserIds(userId, targetUserId);
		if (existRelationship != null) {
			throw new BusinessException(UNDEFINED_ERROR, "已关注该用户！");
		}
		Relationship relationship = new Relationship();
		relationship.setUserId(userId);
		relationship.setFollowingUserId(targetUserId);
		relationship.setFollowingTime(new Timestamp(System.currentTimeMillis()));
		relationshipMapper.insert(relationship);
	}
	
	@Transactional
	public void deleteRelationship(int userId, int targetUserId) {
		int result = relationshipMapper.deleteByUserIds(userId, targetUserId);
		if (result == 0) {
			throw new BusinessException(UNDEFINED_ERROR, "未找到关系！");
		}
	}
	
	/**
	 * Relationship state codes
	 * 0: stranger(not following each other);
	 * 1: following(user is following target user);
	 * 2: follower(user is followed by target user);
	 * 3: friend(following each other);
	 */
	public Integer getRelationshipState(int userId, int targetUserId) {
		if (userId == targetUserId) {
			throw new BusinessException(UNDEFINED_ERROR, "参数不合法！");
		}
		User user = userMapper.selectByPrimaryKey(targetUserId);
		if (user == null) {
			throw new BusinessException(USER_NOT_FOUND, "用户不存在！");
		}
		int followingState, beingFollowedState;//关注状态、被关注状态
		
		Relationship followingRelationship = relationshipMapper.selectByUserIds(userId, targetUserId);
		Relationship beingFollowedRelationship = relationshipMapper.selectByUserIds(targetUserId, userId);
		
		followingState = followingRelationship != null ? 1 : 0;
		beingFollowedState = beingFollowedRelationship != null ? 1 : 0;
		
		if (followingState == 0 & beingFollowedState == 0){
			return 0;
		}else if (followingState == 1 & beingFollowedState == 0){
			return 1;
		}else if (followingState == 0){
			return 2;
		}else {
			return 3;
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
	
	public List<UserResult> getFollowerUserList(int userId) {
//        List<UserResult> resultSet= new ArrayList<UserResult>();
		List<UserResult> resultSet = relationshipMapper.selectFollowerUser(userId);
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
