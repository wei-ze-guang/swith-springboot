package com.chat.common.mapstructmappr;

import com.chat.common.dto.GroupMemberDto;
import com.chat.common.dto.GroupMessageDto;
import com.chat.common.model.GroupMember;
import com.chat.common.model.GroupMessage;
import com.chat.common.vo.GroupMemberVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMessageMapperStr {

    GroupMessage toGroupMessage(GroupMessageDto groupMessageDto);
    GroupMessageDto toGroupMessageDto(GroupMessage groupMessage);
    GroupMemberVo toGroupMemberVo(GroupMember groupMember);

    List<GroupMessage> toGroupMessageDtoList(List<GroupMessageDto> groupMessageDtoList);
    List<GroupMemberVo> toGroupMemberVoList(List<GroupMember> groupMemberList);
    List<GroupMemberDto> toGroupMemberList(List<GroupMember> groupMemberList);
}
