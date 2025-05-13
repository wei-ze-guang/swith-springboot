package com.chat.service.controllerservice.impl;

import com.chat.common.dto.GroupInfoDto;
import com.chat.common.model.GroupInfo;
import com.chat.common.vo.GroupInfoVo;
import com.chat.common.utils.Result;
import com.chat.service.controllerservice.GroupInfoControllerService;
import com.repository.mapper.GroupInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class GroupInfoControllerServiceImpl implements GroupInfoControllerService {

    @Autowired
    private GroupInfoMapper groupInfoMapper;
    /**
     * 根据groupIP获取这个群的信息
     * 1 查询数据库返回结果
     *
     * @param groupId
     * @return Result
     */
    @Override
    public Result findGroupInfoByGroupId(Long groupId) {
        try{
            GroupInfo groupInfo =  groupInfoMapper.selectByGroupId(groupId);
            if(groupInfo != null){
                GroupInfoVo groupInfoOo = new GroupInfoVo();


                return Result.OK(groupInfoOo);
            }
            return Result.FAIL("找不到群信息");
        }catch (Exception e){
                log.error("findGroupInfoByGroupId error", e);
                return Result.FAIL("找不到群信息");
        }
    }

    /**
     * 根据关键词查群群信息
     * 1 查询数据库返回结果
     *
     * @param keyword
     * @return
     */
    @Override
    public Result findGroupsByKeyword(String keyword) {
        try{
            List<GroupInfo> groupInfos = groupInfoMapper.searchByGroupName(keyword);
            return Result.OK(Objects.requireNonNullElse(groupInfos,new ArrayList<GroupInfo>()));
        }catch (Exception e){
            log.error("findGroupsByKeyword error", e);
            return Result.FAIL(new ArrayList<GroupInfo>());
        }
    }

    /**
     * 创建群聊
     * 1 插入数据库返回结果
     *
     * @param groupInfoDto
     * @return
     */
    @Override
    public Result addGroupInfo(GroupInfoDto groupInfoDto) {
        try{
            GroupInfo groupInfo = new GroupInfo();
            groupInfo.setGroupName(groupInfo.getGroupName());
            groupInfo.setGroupId(groupInfo.getGroupId());
            groupInfoMapper.insertGroupInfo(groupInfo);
            return Result.OK(groupInfo);
        }catch (Exception e){
            return Result.FAIL("无法创建群聊");
        }
    }

    /**
     * 解散群聊
     * 1 逻辑上删除这个群信息
     *
     * @param groupId
     * @return
     */
    @Override
    public Result updateGroupInfo(Long groupId) {
        try{
            groupInfoMapper.logicalDeleteByGroupId(groupId);
            return Result.OK("删除群聊成功");
        }catch (Exception e){
            log.error("updateGroupInfo error", e);
            return Result.FAIL("删除群聊失败");
        }
    }
}
