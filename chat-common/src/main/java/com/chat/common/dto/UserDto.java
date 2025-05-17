package com.chat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author 86199
 */
@Data
@Schema(description = "用户注册 Dto")
public class UserDto {
    @NotEmpty(message = "用户注册的userId不能为空")
    @Schema(description = "用户唯一标识，通常为用户名或手机号", example = "user_12345")
    private String userId;

    @Schema(description = "用户密码（应加密传输）", example = "123456")
    @NotEmpty(message = "密码不能为空")
    private String password;

}
