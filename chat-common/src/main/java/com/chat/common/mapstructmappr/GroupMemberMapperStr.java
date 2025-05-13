package com.chat.common.mapstructmappr;

import com.chat.common.dto.GroupMemberDto;
import com.chat.common.model.GroupMember;
import com.chat.common.vo.GroupMemberVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMemberMapperStr {

    GroupMemberVo toGroupMemberVo(GroupMember groupMember);
    GroupMember toGroupMember(GroupMemberDto groupMemberDto);
    GroupMemberDto toGroupMemberDto(GroupMember groupMember);

    List<GroupMemberVo> toGroupMemberVoList(List<GroupMember> groupMemberList);
    List<GroupMemberDto> toGroupMemberDtoList(List<GroupMember> groupMemberList);
    List<GroupMember> toGroupMemberList(List<GroupMemberDto> groupMemberDtoList);
}
