package com.repository.mapper;

import com.chat.common.model.User;
import me.doudan.doc.annotation.DataLayer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    // 根据 用户user_id查询数据  is_delete = 0
    // 现在登录使用的是这个
    @DataLayer(value = "根据user_id  获取用户信息",module = "登录")
    @Select("SELECT user_id,avatar,gender,nick_name,signature FROM `user` WHERE user_id = #{userId} AND is_deleted = 0")
    User selectByUserId(String userId);


    @Select("SELECT user_id,password,avatar,nick_name,signature FROM `user` WHERE user_id = #{userId} AND  password=#{password} AND is_deleted = 0")
    User selectByUserIdAndPassword(String userId);

    // 插入的时候先 用于注册 ，头像使用默认的，但是需要加进去，created_at的的话数据库自己生成，is_delete数据库有默认的
    @Insert("INSERT INTO `user` (user_id, password) " +
            "VALUES (#{userId}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")  // 使用自增主键
    @DataLayer(value = "插入一条user数据",module = "注册")
    int insertUser(User user);

    // 更新用户信息
    @Update("UPDATE `user` SET password = #{password}, avatar = #{avatar}, updated_at = #{updatedAt}, gender = #{gender}, " +
            "nick_name = #{nickName}, signature = #{signature} WHERE user_id = #{userId} AND is_deleted = 0")
    int updateUser(User user);

    // 用户注销账号
    @Update("UPDATE `user` SET is_deleted = 1 WHERE user_id = #{userId}")
    @DataLayer(value = "逻辑删除一条user数据",module = "用户注销账号")
    int softDeleteUser(String userId);

    @Select("select  user.user_id,user.avatar,user.nick_name,user.gender,user.signature from user_relation join user on user_relation.user_id = user.id where user_relation.user_id "+
            "= #{userId} and user_relation.is_deleted = 0 and user.is_deleted = 0")
    @DataLayer(value = "根据userId或者这个用户的所有的好友信息",module = "获取用户所有好友信息")
    List<User> selectFriendUsersByUserId(String userId);

    @Select("  SELECT user_id,avatar,signature,gender,nick_name FROM user" +
            "  WHERE user_id LIKE CONCAT('%', #{keyword}, '%')" +
            "    AND is_deleted = 0")
    @DataLayer(value = "根据userId进行模糊查询",module = "模糊查询好友")
    List<User> selectByUserIdByLike(String userId);
}
