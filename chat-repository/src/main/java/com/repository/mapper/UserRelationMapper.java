package com.repository.mapper;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.model.UserRelation;
import me.doudan.doc.annotation.DataLayer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRelationMapper {

    /**
     * 添加好友关系（单向）  添加之前看一下是否有已经有了
     */
    @DataLayer(value = "插入一条好友关系数据",module = ModuleConstant.MODULE_ADD_FRIEND)
    @Insert("INSERT INTO user_relation (user_id, friend_user_id, note_name) " +
            "VALUES (#{userId}, #{friendUserId} ,0)")
    int insertUserRelation(UserRelation userRelation);

    /**
     * 检查用户和这个群的关系
     * 这个出来的两个值一样的才不矛盾
     *
     */
    @Select("select is_deleted from user_relation where user_id = #{userId} and friend_user_id = #{friendUserId}\n" +
            "union select is_deleted from user_relation where user_id = #{friendUserId} and friend_user_id = #{userId}")
    List<Integer> selectUserRelationByUserIdAndFriendUserId(UserRelation userRelation);

    /**
     * 更新这两个用户的关系
     * @param userRelation
     * @return
     */
    @Update("update user_relation set is_deleted = 0 where (user_id=#{userId} and friend_user_id = #{friendUserId})" +
            "or (user_id=#{friendUserId} and friend_user_id = #{userId})")
    int updateUserRelation(UserRelation userRelation);

    /**
     * 逻辑删除指定两个好友关系（单向）
     * 这里直接更新两条数据
     */
    @DataLayer(value = "把好友的is_deleted修改为删除状态",module = ModuleConstant.MODULE_DELETE_FRIEND)
    @Update("UPDATE user_relation SET is_deleted = 1 " +
            "WHERE (user_id = #{userId} AND friend_user_id = #{friendUserId} AND is_deleted = 0 )" +
            "or (user_id = #{friendUserId} AND friend_user_id = #{userId} AND is_deleted = 0 )")
    int softDeleteUserRelation(UserRelation userRelation);

    //TODO  这里可以优化走索引，分为两次删除
    @DataLayer(value = "逻辑上删除一个人的全部好友",module = ModuleConstant.MODULE_DELETE_FRIEND)
    @Update("update user_relation set is_deleted = 1 where user_id = #{userId} or friend_user_id = #{userId}")
    Integer softDeleteUserAllRelations(@Param("userId") int userId);

    /**
     * 查看好友双方的关系，先判断返回的数量，如果没有就是没有任何关系，如果有 长度为2才是好友，否则已经不是好友
     * @param userId
     * @param friendUserId
     * @return
     */
    @DataLayer(value = "查询双方的好友关系",module = "需要检查双方好友状态")
    @Select("select  is_deleted from user_relation where user_id = #{userId} and friend_user_id = #{friendUserId} \n"+
    "union all"+ " select  is_deleted from user_relation where user_id = #{friendUserId} and friend_user_id = #{userId}  ")
    List<Integer> selectUserRelationIsDeleted(@Param("userId") String userId, @Param("friendUserId") String friendUserId);


}
