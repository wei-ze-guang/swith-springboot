package com.chat.common.mapstructmappr;

import com.chat.common.dto.PrivateMessageDto;
import com.chat.common.model.PrivateMessage;
import com.chat.common.vo.PrivateMessageVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrivateMessageMapperStr {

    PrivateMessageVo toPrivateMessageVo(PrivateMessage privateMessage);
    PrivateMessage toPrivateMessage(PrivateMessageDto privateMessageDto);
    PrivateMessageDto toPrivateMessageDto(PrivateMessage privateMessage);

    List<PrivateMessageVo> toPrivateMessageVoList(List<PrivateMessage> privateMessage);
    List<PrivateMessageDto> toPrivateMessageDtoList(List<PrivateMessage> privateMessage);
    List<PrivateMessage> toPrivateMessageList(List<PrivateMessageDto> privateMessageDto);
}
