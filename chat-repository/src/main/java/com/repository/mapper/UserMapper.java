package com.repository.mapper;

import com.chat.common.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // 根据 用户user_id查询数据  is_delete = 0
    @Select("SELECT user_id,avatar,gender,nick_name,signature FROM `user` WHERE user_id = #{userId} AND is_deleted = 0")
    User selectByUserId(String userId);

    // 根据 用户user_id查询数据  is_delete = 0
    @Select("SELECT user_id,avatar,gender,nick_name,signature FROM `user` WHERE user_id = #{userId} AND is_deleted = 0")

    // 用于登录
    @Select("SELECT user_id,password FROM `user` WHERE user_id = #{userId} AND  password=#{password} AND is_deleted = 0")
    User selectByUserIdAndPassword(String userId,String password);

    // 插入的时候先 头像使用默认的，但是需要加进去，created_at的的话数据库自己生成，is_delete数据库有默认的
    @Insert("INSERT INTO `user` (user_id, password, avatar, gender) " +
            "VALUES (#{userId}, #{password}, #{avatar}, #{gender})")
    @Options(useGeneratedKeys = true, keyProperty = "id")  // 使用自增主键
    int insertUser(User user);

    // 更新用户信息
    @Update("UPDATE `user` SET password = #{password}, avatar = #{avatar}, updated_at = #{updatedAt}, gender = #{gender}, " +
            "nick_name = #{nickName}, signature = #{signature} WHERE user_id = #{userId} AND is_deleted = 0")
    int updateUser(User user);

    // 用户注销账号
    @Update("UPDATE `user` SET is_deleted = 1 WHERE user_id = #{userId}")
    int softDeleteUser(String userId);
}
