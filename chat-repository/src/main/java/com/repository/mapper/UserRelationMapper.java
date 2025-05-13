package com.repository.mapper;

import me.doudan.doc.annotation.DataLayer;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserRelationMapper {

    /**
     * 添加好友关系（单向）
     */
    @DataLayer(value = "插入一条好友关系数据",module = "添加好友")
    @Insert("INSERT INTO user_relation (user_id, friend_user_id, note_name, is_deleted) " +
            "VALUES (#{userId}, #{friendUserId}, #{noteName}, 0)")
    int addFriend(@Param("userId") String userId, @Param("friendUserId") String friendUserId, @Param("noteName") String noteName);

    /**
     * 逻辑删除好友关系（单向）
     */
    @DataLayer(value = "把好友的is_deleted修改为删除状态",module = "删除好友")
    @Update("UPDATE user_relation SET is_deleted = 1, updated_at = NOW() " +
            "WHERE user_id = #{userId} AND friend_user_id = #{friendUserId} AND is_deleted = 0")
    int deleteFriend(@Param("userId") String userId, @Param("friendUserId") String friendUserId);

    /**
     * 查看好友双方的关系，先判断返回的数量，如果没有就是没有任何关系，如果有 is_deleted = 0就是好友，否则已经不是好友
     * @param userId
     * @param friendUserId
     * @return
     */
    @DataLayer(value = "查询双方的好友关系",module = "需要检查双方好友状态")
    @Select("select  is_deleted from user_relation where user_id = #{userId} and friend_user_id = #{friendUserId}")
    int selectUserRelationIsDeleted(@Param("userId") String userId, @Param("friendUserId") String friendUserId);
}
