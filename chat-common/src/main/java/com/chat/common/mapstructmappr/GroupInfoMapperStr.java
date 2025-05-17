package com.chat.common.mapstructmappr;

import com.chat.common.dto.GroupInfoDto;
import com.chat.common.model.GroupInfo;
import com.chat.common.vo.GroupInfoVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupInfoMapperStr {

    GroupInfoVo toGroupInfoVo(GroupInfo groupInfo);
    GroupInfo  toGroupInfo(GroupInfoDto groupInfoDto);
    GroupInfoDto toGroupInfoDto(GroupInfo groupInfo);

    List<GroupInfoDto> toGroupInfoDtoList(List<GroupInfo> groupInfoList);
    List<GroupInfo> toGroupInfoList(List<GroupInfoDto> groupInfoDtoList);
    List<GroupInfoVo> toGroupInfoVoList(List<GroupInfo> groupInfoList);
}
