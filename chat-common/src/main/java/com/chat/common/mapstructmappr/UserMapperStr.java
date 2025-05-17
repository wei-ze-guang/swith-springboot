package com.chat.common.mapstructmappr;


import com.chat.common.dto.UserDto;
import com.chat.common.model.User;
import com.chat.common.vo.FriendVO;
import com.chat.common.vo.UserVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapperStr {
    /**
     * 展示好友信息的时候使用到
     * @param user
     * @return
     */
    FriendVO toFriendVO(User user);

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    UserVo toUserVo(User user);


    // 批量处理
    List<UserDto> toDtoList(List<User> entityList);
    List<User> toEntityList(List<UserDto> dtoList);
    List<UserVo> toVOList(List<User> entityList);

    List<FriendVO> toFriendVOList(List<User> dtoList);
}
