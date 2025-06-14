package com.repository.mapper;

import com.chat.common.constant.ModuleConstant;
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
    @DataLayer(value = "创建群聊",module = ModuleConstant.MODULE_CREATE_GROUP)
    @Insert("INSERT INTO group_info (group_id, user_id, group_name) " +
            "VALUES (#{groupId}, #{userId}, #{groupName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertGroupInfo(GroupInfo groupInfo);

    /**
     * 根据group_id  获取群信息
     * @param groupId
     * @return
     */
    @DataLayer(value = "根据group_id  获取群信息",module = ModuleConstant.MODULE_FIND_ONE_GROUP_INFO)
    @Select("SELECT group_id,group_name,user_id,introduce,avatar,created_at,notice,is_deleted FROM group_info WHERE group_id = #{groupId} AND is_deleted = 0")
    GroupInfo selectByGroupId(@Param("groupId") Integer groupId);

    /**
     * 更新群信息
     * @param groupInfo
     * @return
     */
    @DataLayer(value = "更新群信息",module = ModuleConstant.MODULE_UPDATE_ONE_USER_INFO)
    @Update("UPDATE group_info SET group_name = #{groupName}, notice = #{notice}, introduce = #{introduce}, avatar = #{avatar}, updated_at = NOW() " +
            "WHERE group_id = #{groupId} AND is_deleted = 0")
    int updateByGroupId(GroupInfo groupInfo);

    /**
     * 逻辑删除群
     * @param groupId
     * @return
     */
    @DataLayer(value = "逻辑删除群",module = ModuleConstant.MODULE_DISBAND_GROUP)
    @Delete("UPDATE group_info SET is_deleted = 1 WHERE group_id = #{groupId}")
    int logicalDeleteByGroupId(@Param("groupId") Integer groupId);


    // 模糊查询群名,只返回group_id,group_name,avatar
    @Select("SELECT group_id,user_id,group_name,avatar,introduce,notice FROM group_info " +
            "WHERE  is_deleted = 0 AND group_name LIKE CONCAT('%', #{keyword}, '%') ")
    @DataLayer(value = "模糊查群群",module = ModuleConstant.MODULE_FUZZY_QUERY_GROUP_INFO_BY_WORD)
    List<GroupInfo> searchByGroupName(@Param("keyword") String keyword);


    /**
     * 删除一个用户所有的群，用在用户退出群聊
     */
    @DataLayer(value = "删除一个用户所有自己创建群",module = ModuleConstant.MODULE_USER_EXIT)
    @Update("update group_info set is_deleted = 1 where user_id = #{userId}")
    Integer softOneUserAllOwnerGroup(@Param("userId") String userId);

}