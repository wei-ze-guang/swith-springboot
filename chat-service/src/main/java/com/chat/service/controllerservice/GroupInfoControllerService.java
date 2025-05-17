package com.chat.service.controllerservice;

import com.chat.common.dto.GroupInfoDto;
import com.chat.common.model.GroupInfo;
import com.chat.common.utils.Result;


public interface GroupInfoControllerService {
    /**
     * 根据groupIP获取这个群的信息
     * 1 查询数据库返回结果
     * @param groupId
     * @return Result
     */
    public Result findGroupInfoByGroupId(Integer groupId) ;

    /**
     * 根据关键词查群群信息
     * 1 查询数据库返回结果
     * @param keyword
     * @return
     */
    public Result findGroupsByKeyword(String keyword) ;

    /**
     * 创建群聊
     * 1 插入数据库返回结果
     * @param groupInfoDto
     * @return
     */
    public Result addGroupInfo(GroupInfoDto groupInfoDto) ;

    /**
     * 解散群聊
     * 1逻辑上删除这个群信息
     * @param groupId
     * @return
     */
    public Result updateGroupInfo(Integer groupId) ;
}
