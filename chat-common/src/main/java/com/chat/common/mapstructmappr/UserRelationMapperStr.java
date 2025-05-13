package com.chat.common.mapstructmappr;

import com.chat.common.dto.UserRelationDto;
import com.chat.common.model.UserRelation;
import com.chat.common.vo.UserRelationVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRelationMapperStr {

    UserRelation toUserRelation(UserRelationDto userRelationDto);
    UserRelationDto toUserRelationDto(UserRelation userRelation);
    UserRelationVo toUserRelationVo(UserRelation userRelation);

    List<UserRelationVo> toUserRelationVoList(List<UserRelation> userRelationList);
    List<UserRelationDto> toUserRelationDtoList(List<UserRelation> userRelationList);
    List<UserRelation> toUserRelationList(List<UserRelationDto> userRelationDtoList);
}
