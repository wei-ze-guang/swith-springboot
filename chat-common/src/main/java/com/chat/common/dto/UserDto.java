package com.chat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户注册 Dto")
public class UserDto {
    @Schema(description = "用户唯一标识，通常为用户名或手机号", example = "user_12345")
    private String userId;

    @Schema(description = "用户密码（应加密传输）", example = "123456")
    private String password;

}
