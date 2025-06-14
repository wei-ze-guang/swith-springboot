package com.repository.mapper;
import com.chat.common.constant.ModuleConstant;
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
    @DataLayer(value = "插入一条privateMessage信息",module = ModuleConstant.MODULE_SEND_PRIVATE_MESSAGE)
    @Insert("INSERT INTO private_message (id, user_id, to_user_id, message_type, source_uri, content, status, is_deleted) " +
            "VALUES (#{id}, #{userId}, #{toUserId}, #{messageType}, #{sourceUri}, #{content}, #{status}, #{isDeleted})")
    int insertPrivateMessage(PrivateMessage message);

    /**
     * 逻辑删除好友双方所有私信消息（将 is_deleted 设置为 1）
     * @param userId 发送方
     * @param toUserid 接收方
     * @return 更新条数
     */
    @DataLayer(value = "逻辑删除一条privateMessage信息，一般来说要区分一下双方",module = ModuleConstant.MODULE_DELETE_FRIEND)
    @Update("UPDATE private_message SET is_deleted = 1 WHERE (user_id = #{userId} and to_user_id = #{toUserid}) or  (to_user_id = #{userId} and user_id = #{toUserid})")
    int softDeleteAllPrivateMessageBothSides(@Param("userId") String userId, @Param("toUserid") String toUserid );

    /**
     * 查询双方的所有未删除的消息（发送或接收
     * @param userId 当前用户 ID
     * @return 消息列表
     */
    @DataLayer(value = "获取好友双方的所有正常的聊天记录",module = ModuleConstant.MODULE_FIND_ALL_BOTH_SIDES_PRIVATE_MESSAGE)
    @Select("SELECT id,user_id,to_user_id,private_message.content,message_type,source_uri,created_at,updated_at,status,is_deleted FROM private_message WHERE is_deleted = 0 AND (user_id = #{userId} AND to_user_id = #{toUserId})"
    + "union  SELECT id,user_id,to_user_id,private_message.content,message_type,source_uri,created_at,updated_at,status,is_deleted from private_message WHERE is_deleted = 0 AND (user_id = #{toUserId} AND to_user_id = #{userId})")
    List<PrivateMessage> selectPrivateMessageByUserIdAndToUserId(@Param("userId") String userId, @Param("toUserId") String toUserId);


    /**
     * 删除一个用户的所有私信信息
     * @param userId
     * @return
     */
    @DataLayer(value = "删除一个用户的所有私聊信息",module = ModuleConstant.MODULE_USER_EXIT)
    @Update("update  private_message set is_deleted = 1 where user_id = #{userId} or to_user_id = #{userId}")
    Integer softDeleteOneUserAllPrivateMessage(@Param("userId") String userId);
}
