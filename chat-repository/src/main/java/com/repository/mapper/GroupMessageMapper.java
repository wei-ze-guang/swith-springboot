package com.repository.mapper;

import com.chat.common.model.GroupMessage;
import me.doudan.doc.annotation.DataLayer;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 群消息的数据库操作接口
 */
@Mapper
public interface GroupMessageMapper {

    /**
     * 发送一条群消息
     *
     * @param message 要插入的群消息对象
     * @return 插入影响的行数
     */
    @DataLayer(value = "插入一条群groupMessage记录",module = "发送群信息")
    @Insert("INSERT INTO group_message (id, group_id, user_id, message_type, source_uri, content, created_at, updated_at, is_deleted, status) " +
            "VALUES (#{id}, #{groupId}, #{userId}, #{messageType}, #{sourceUri}, #{content}, #{createdAt}, #{updatedAt}, #{isDeleted}, #{status})")
    int insertGroupMessage(GroupMessage message);

    /**
     * 逻辑删除群消息（将 is_deleted 置为 1）
     *
     * @param userId 消息 用户
     * @return 更新影响的行数
     */
    @DataLayer(value = "逻辑删除某个用户在某个群所有groupMessage",module = "用户退群")
    @Update("UPDATE group_message SET is_deleted = 1 WHERE user_id = #{userId} and group_id = #{groupId}")
    int updateGroupMessageByUserIdAndGroupId(@Param("userId") String userId, @Param("GroupId") Integer groupId);

    /**
     * 删除某个群的所有群信息
     * @param groupId 群id
     * @return 影响行数
     */
    @DataLayer(value = "跟你groupId删除这个群的全部信息",module = "群主解散群")
    @Select("update group_message set is_deleted = 1 where group_id = #{groupId}")
    int deleteGroupMessageByGroupId(@Param("groupId") Integer groupId);

    /**
     * 根据groupId获取这个群所有的有效聊天记录
     * @param groupId 群id
     * @return 数组
     */
    @DataLayer(value = "根据群id获取所有效聊天记录",module = "获取一个群所有聊天记录")
    @Select("select id,group_id,user_id,message_type,source_uri,content,created_at,is_deleted from  group_message where group_id = #{groupId} and is_deleted  = 0")
    List<GroupMessage> getGroupMessageByGroupId(Integer groupId);

    @DataLayer(value = "根据groupId获取一个群组所有的用户")
    @Select("select group_member.group_id from group_member where group_id = #{groupId}")
    List<Integer> getGroupAllMembersByGroupId(Integer groupId);
}
