package com.chat.service.managerservice.impl;

import com.chat.common.constant.RedisCacheConstant;
import com.chat.service.managerservice.WebSocketDataService;
import com.repository.mapper.GroupMemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WebSocketDataServiceImpl implements WebSocketDataService {
    @Autowired
    private GroupMemberMapper groupMemberMapper;
    /**
     * 获取一个群的有效群成员的userId
     *
     * @param groupId
     * @return List<String>
     */
    @Override
    @Cacheable(value = RedisCacheConstant.CACHE_NAME,key = "#groupId")
    public List<String> getTheGroupMembersOnlyUserIdList(Integer groupId) {
        return groupMemberMapper.selectGroupAllMembersByGroupIdOnlyUserId(groupId);
    }
}
