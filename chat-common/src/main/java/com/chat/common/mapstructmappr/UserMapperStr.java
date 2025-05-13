package com.chat.common.mapstructmappr;


import com.chat.common.dto.UserDto;
import com.chat.common.model.User;
import com.chat.common.vo.UserVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapperStr {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    UserVo toUserVo(User user);


    // 批量处理
    List<UserDto> toDtoList(List<User> entityList);
    List<User> toEntityList(List<UserDto> dtoList);
    List<UserVo> toVOList(List<User> entityList);
}
