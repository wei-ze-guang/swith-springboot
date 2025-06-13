package com.chat.common.utils;

import cn.hutool.captcha.LineCaptcha;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 整个类还需要进行错误处理和 字符串变为常量而不是直接写死
 */
@Component
public class CaptchaUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成一个验证码并且村存储在redis
     * @return 返回一个Map 包括键uuid,img
     */
    public Map captchaGenerateSaveRedis(){
        // 创建验证码：宽 130，高 48，验证码长度 5，干扰线数量 20
        String uuid = UUID.randomUUID().toString();

        // 生成验证码
        LineCaptcha captcha = new LineCaptcha(130, 48, 5, 20);
        String code = captcha.getCode().toLowerCase();

        // 保存验证码到 Redis（或开发阶段用 Map 替代也可）
        stringRedisTemplate.opsForValue().set("CAPTCHA:" + uuid, code, 2, TimeUnit.MINUTES);

        // 将图片转为 Base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        captcha.write(baos);
        String base64Img = Base64.getEncoder().encodeToString(baos.toByteArray());

        Map<String, String> data = new HashMap<>();

        data.put("uuid", uuid);
        data.put("img", "data:image/png;base64," + base64Img);

        return data;
    }
    /**
     * 校验验证码是否正确
     * @param uuid 前端传过来的验证码唯一标识
     * @param code 用户输入的验证码（不区分大小写）
     * @return 是否匹配
     */
    public boolean isValidateCaptcha(String uuid, String code) {
        if (StringUtils.isBlank(uuid) || StringUtils.isBlank(code)) {
            return false;
        }

        String key = "CAPTCHA:" + uuid;
        String realCode = stringRedisTemplate.opsForValue().get(key);
        if (realCode != null && code.trim().equalsIgnoreCase(realCode)) {
            stringRedisTemplate.delete(key);
            return true;
        }

        return false;
    }

}
