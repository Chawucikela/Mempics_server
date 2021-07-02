package com.nowcoder.seckill.dao;

import com.nowcoder.seckill.entity.Relationship;
import com.nowcoder.seckill.entity.resultentity.UserResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RelationshipMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table relationship
     *
     * @mbg.generated Wed Jun 09 16:33:55 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table relationship
     *
     * @mbg.generated Wed Jun 09 16:33:55 CST 2021
     */
    int insert(Relationship record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table relationship
     *
     * @mbg.generated Wed Jun 09 16:33:55 CST 2021
     */
    Relationship selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table relationship
     *
     * @mbg.generated Wed Jun 09 16:33:55 CST 2021
     */
    List<Relationship> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table relationship
     *
     * @mbg.generated Wed Jun 09 16:33:55 CST 2021
     */
    int updateByPrimaryKey(Relationship record);

    int deleteByUserIds(Integer userId, Integer followingUserId);

    List<UserResult> selectFollowingUser(Integer userId);

    List<UserResult> selectFollowerUser(Integer followingUserId);

    Relationship selectByUserIds(Integer userId, Integer followingUserId);
}