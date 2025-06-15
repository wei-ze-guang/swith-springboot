package com.repository.mapper;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.model.GroupInfo;
import com.chat.common.model.GroupMember;
import com.chat.common.model.GroupMessage;
import com.chat.common.model.User;
import me.doudan.doc.annotation.DataLayer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupMemberMapper {

    /**
     * 添加用户到群
     * 这里加入之前先检查一下是否已经加入过
     */
    @DataLayer(value = "插入一条groupMerber数据",module = ModuleConstant.MODULE_USER_JOIN_GROUP)
    @Insert("INSERT INTO group_member (group_id, user_id, identity) " +
            "VALUES (#{groupId}, #{userId}, #{identity})")
    int insertGroupMember(GroupMember groupMember);

    /**
     * 检查是什么关系
     * @param groupMember
     * @return
     */
    @Select("select group_member.is_deleted from group_member where group_id=#{groupId} and user_id= #{userId}")
    Integer selectGroupMemberRelation(GroupMember groupMember);

    /**
     * 更新is_deleted
     * @param groupMember
     * @return
     */
    @Update("update group_member set is_deleted = 0 where group_id=#{groupId} and user_id= #{userId}")
    int updateGroupMember(GroupMember groupMember);

    /**
     * 用户退出群（逻辑删除）
     */
    @DataLayer(value = "修改groupMember的is_deleted为删除状态",module = ModuleConstant.MODULE_USER_EXIT_GROUP)
    @Update("UPDATE group_member SET is_deleted = 1 " +
            "WHERE group_id = #{groupId} AND user_id = #{userId} AND is_deleted = 0")
    int updateGroupMemberIsDeleted(@Param("groupId") Integer groupId, @Param("userId") String userId);

    /**
     * 获取一个用户的所加入的群包括群信息
     *  group_info.user_id  是群主
     */
    @DataLayer(value = "根据userId获取这个人所有加入的群的信息",module = ModuleConstant.MODULE_FIND_USER_ALL_GROUP_JOIN_INFO)
    @Select("select group_member.group_id,group_info.avatar, group_info.user_id,group_info.avatar,group_info.group_name,group_info.notice,group_info.introduce from  group_member join group_info on group_member.group_id " +
            "= group_info.group_id where group_member.user_id = #{userId} and group_member.is_deleted = 0")
    List<GroupInfo> getGroupMembers( @Param("userId") String userId);

    /**
     * 删除一个群的所有群成员，用户注销群
     */
    @DataLayer(value = "删除一个群的所有成员",module = ModuleConstant.MODULE_DISBAND_GROUP)
    @Update("update group_member set is_deleted = 1 where  group_id = #{groupId}")
    int softDeleteOneGroupAllMember( @Param("groupId") Integer  groupId);

    /**
     * 获取一个群的所有群成员信息
     * @param groupId
     * @return
     */
    @Select("select user.user_id,user.nick_name,user.avatar from group_member join user on group_member.user_id = user.user_id\n" +
            "where group_id = #{groupId} and group_member.is_deleted = 0 and user.is_deleted = 0")
    @DataLayer(value = "获取群成员所有下信息",module = ModuleConstant.MODULE_THE_GROUP_ALL_MEMBERS_INFO)
    List<User> selectGroupAllMembersByGroupId(@Param("groupId") Integer groupId);

    /**
     * 获取一个群的所有群成员的userId
     * @param groupId
     * @return
     */
    @Select("select user_id from group_member where group_id = #{groupId} and is_deleted = 0")
    @DataLayer(value = "获取这个群的说所有用户的userId")
    List<String> selectGroupAllMembersByGroupIdOnlyUserId(@Param("groupId") Integer groupId);

    /**
     * 删除这人所有加入的群，用户注销的时候可以使用
     * @param userId
     * @param groupId
     * @return
     */
    @DataLayer(value = "删除一个用户所有加入的群，变为非群成员状态",module = ModuleConstant.MODULE_USER_EXIT)
    @Update("update group_member set  is_deleted = 1 where user_id = #{userId}")
    Integer softOneUserAllJoinGroup(@Param("userId") String userId, @Param("groupId") Integer groupId);
}
