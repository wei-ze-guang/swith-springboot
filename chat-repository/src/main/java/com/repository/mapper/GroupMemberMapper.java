package com.repository.mapper;

import com.chat.common.model.GroupMember;
import me.doudan.doc.annotation.DataLayer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupMemberMapper {

    /**
     * 添加用户到群
     */
    @DataLayer(value = "插入一条groupMerber数据",module = "用户添加群聊")
    @Insert("INSERT INTO group_member (group_id, user_id, identity, is_deleted) " +
            "VALUES (#{groupId}, #{userId}, #{identity}, #{isDeleted})")
    int insertGroupMember(@Param("groupId") Long groupId, @Param("userId") String userId, @Param("identity") Integer identity);

    /**
     * 用户退出群（逻辑删除）
     */
    @DataLayer(value = "修改groupMember的is_deleted为删除状态",module = "用户退出群聊")
    @Update("UPDATE group_member SET is_deleted = 1 " +
            "WHERE group_id = #{groupId} AND user_id = #{userId} AND is_deleted = 0")
    int updateGroupMemberIsDeleted(@Param("groupId") Long groupId, @Param("userId") String userId);

    /**
     * 获取一个用户的所加入的群
     */
    @DataLayer(value = "根据userId获取这个人所有加入的群的信息",module = "获取用户的所有群信息")
    @Select("select * from  group_member join group_info on group_member.group_id " +
            "= group_info.group_id where group_member.user_id = #{userId} and group_member.is_deleted = 0")
    List<GroupMember> getGroupMembers( @Param("userId") String userId);
}
