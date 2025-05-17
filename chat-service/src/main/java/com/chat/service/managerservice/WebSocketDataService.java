package com.chat.service.managerservice;

import java.util.List;

public interface WebSocketDataService {

    /**
     * 获取一个群的有效群成员的userId
     * @param groupId
     * @return List<String>
     */
    List<String> getTheGroupMembersOnlyUserIdList(Integer groupId);
}
