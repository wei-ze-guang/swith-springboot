package com.repository.mapper;

import com.chat.common.model.GroupInfo;
import me.doudan.doc.annotation.DataLayer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupInfoMapper {

    /**
     * 用于创建群，只插入 groupId,groupName,user_id
     * @param groupInfo
     * @return
     */
    @DataLayer(value = "创建群聊",module = "建群")
    @Insert("INSERT INTO group_info (group_id, user_id, group_name) " +
            "VALUES (#{groupId}, #{userId}, #{groupName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertGroupInfo(GroupInfo groupInfo);

    /**
     * 根据group_id  获取群信息
     * @param groupId
     * @return
     */
    @DataLayer(value = "根据group_id  获取群信息",module = "获取群聊")
    @Select("SELECT group_id,group_name,user_id,introduce,avatar,created_at,notice,is_deleted FROM group_info WHERE group_id = #{groupId} AND is_deleted = 0")
    GroupInfo selectByGroupId(@Param("groupId") Long groupId);

    /**
     * 更新群信息
     * @param groupInfo
     * @return
     */
    @DataLayer(value = "更新群信息",module = "更新群")
    @Update("UPDATE group_info SET group_name = #{groupName}, notice = #{notice}, introduce = #{introduce}, avatar = #{avatar}, updated_at = NOW() " +
            "WHERE group_id = #{groupId} AND is_deleted = 0")
    int updateByGroupId(GroupInfo groupInfo);

    /**
     * 逻辑删除群
     * @param groupId
     * @return
     */
    @Delete("UPDATE group_info SET is_deleted = 1 WHERE group_id = #{groupId}")
    int logicalDeleteByGroupId(@Param("groupId") Long groupId);


    // 模糊查询群名,只返回group_id,group_name,avatar
    @Select("SELECT group_id,group_name,avatar FROM group_info " +
            "WHERE  is_deleted = 0 AND group_name LIKE CONCAT('%', #{keyword}, '%') ")
    List<GroupInfo> searchByGroupName(@Param("keyword") String keyword);

    // 根据 groupId 批量查询
    @Select({
            "<script>",
            "SELECT * FROM group_info",
            "WHERE is_deleted = 0",
            "AND group_id IN",
            "<foreach collection='groupIds' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<GroupInfo> selectByGroupIds(@Param("groupIds") List<Long> groupIds);

}