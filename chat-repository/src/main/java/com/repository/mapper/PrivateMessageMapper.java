package com.repository.mapper;
import com.chat.common.model.PrivateMessage;
import me.doudan.doc.annotation.DataLayer;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 私信消息的 Mapper 接口
 */
@Mapper
public interface PrivateMessageMapper {

    /**
     * 插入私信消息
     * @param message 私信消息实体
     * @return 插入成功的条数
     */
    @DataLayer(value = "插入一条privateMessage信息",module = "发送好友私信")
    @Insert("INSERT INTO private_message (id, user_id, to_user_id, message_type, source_uri, content, created_at, updated_at, status, is_deleted) " +
            "VALUES (#{id}, #{userId}, #{toUserId}, #{messageType}, #{sourceUri}, #{content}, #{createdAt}, #{updatedAt}, #{status}, #{isDeleted})")
    int insertPrivateMessage(PrivateMessage message);

    /**
     * 逻辑删除一条消息（将 is_deleted 设置为 1）
     * @param userId 发送方
     * @param toUserid 接收方
     * @return 更新条数
     */
    @DataLayer(value = "逻辑删除一条privateMessage信息，一般来说要区分一下双方",module = "删除好友")
    @Update("UPDATE private_message SET is_deleted = 1 WHERE user_id = #{userId} and to_user_id = #{toUserid}")
    int logicallyDeletePrivateMessageById(@Param("userId") String userId, @Param("toUserid") String toUserid );

    /**
     * 查询某用户的所有未删除的消息（发送或接收
     * @param userId 当前用户 ID
     * @return 消息列表
     */
    @DataLayer(value = "获取好友双方的所有正常的聊天记录",module = "获取好友双方所有聊天记录")
    @Select("SELECT id,user_id,to_user_id,message_type,source_uri,created_at,updated_at,status,is_deleted FROM private_message WHERE is_deleted = 0 AND (user_id = #{userId} AND to_user_id = #{toUserId})"
    + "union  SELECT id,user_id,to_user_id,message_type,source_uri,created_at,updated_at,status,is_deleted from private_message WHERE is_deleted = 0 AND (user_id = #{toUserId} AND to_user_id = #{userId})")
    List<PrivateMessage> getAllActiveMessages(@Param("userId") String userId, @Param("toUserid") String toUserid);
}
