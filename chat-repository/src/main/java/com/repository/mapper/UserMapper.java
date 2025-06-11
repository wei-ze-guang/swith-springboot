package com.repository.mapper;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.model.User;
import me.doudan.doc.annotation.DataLayer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    // 根据 用户user_id查询数据  is_delete = 0
    // 现在登录使用的是这个
    @DataLayer(value = "根据user_id  获取用户信息",module = ModuleConstant.MODULE_LOGIN)
    @Select("SELECT user_id,password,avatar,gender,nick_name,signature FROM `user` WHERE user_id = #{userId} AND is_deleted = 0")
    User selectByUserId(String userId);


    // spring security专用
    @DataLayer(value = "根据user_id  获取用户信息",module = ModuleConstant.MODULE_LOGIN)
    @Select("SELECT user_id,password,avatar,gender,nick_name,signature FROM `user` WHERE user_id = #{userId} AND is_deleted = 0")
    User selectByUserIdUserToSpringSecurity(String userId);

    /**
     * 这个可能暂时使用不到
     * @param userId
     * @return
     */
    @DataLayer(value = "根据userId获取用户的个人信息，拿出来后再检查密码",module = ModuleConstant.MODULE_LOGIN)
    @Select("SELECT user_id,password,avatar,nick_name,signature FROM `user` WHERE user_id = #{userId} AND  password=#{password} AND is_deleted = 0")
    User selectByUserIdAndPassword(String userId);

    @DataLayer(value = "查询一个用户的信息除了密码",module = ModuleConstant.MODULE_FIND_ONE_USER_INFO)
    @Select("SELECT user_id,avatar,signature,gender,nick_name FROM `user` WHERE user_id = #{userId} AND is_deleted = 0")
    User selectOneByUserId(String userId);

    // 插入的时候先 用于注册 ，头像使用默认的，但是需要加进去，created_at的的话数据库自己生成，is_delete数据库有默认的
    @Insert("INSERT INTO `user` (user_id, password) " +
            "VALUES (#{userId}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")  // 使用自增主键
    @DataLayer(value = "插入一条user数据",module = ModuleConstant.MODULE_REGISTER)
    int insertUser(User user);

    // 更新用户信息
    @Update("UPDATE `user` SET password = #{password}, avatar = #{avatar}, updated_at = #{updatedAt}, gender = #{gender}, " +
            "nick_name = #{nickName}, signature = #{signature} WHERE user_id = #{userId} AND is_deleted = 0")
    @DataLayer(value = "更新用户信息",module = ModuleConstant.MODULE_FIND_ONE_USER_INFO)
    int updateUser(User user);

    // 用户注销账号
    @Update("UPDATE `user` SET is_deleted = 1 WHERE user_id = #{userId}")
    @DataLayer(value = "逻辑删除一条user数据",module = ModuleConstant.MODULE_USER_EXIT)
    int softDeleteUser(String userId);

    @Select("select  user_relation.friend_user_id,user.avatar,user.nick_name,user.gender,user.signature from user_relation join user on user_relation.friend_user_id = user.user_id where user_relation.user_id "+
            "= #{userId} and user_relation.is_deleted = 0 and user.is_deleted = 0")
    @DataLayer(value = "根据userId或者这个用户的所有的好友信息",module = ModuleConstant.MODULE_FIND_USER_ALL_FRIEND_INFO)
    List<User> selectFriendUsersByUserId(String userId);

    @Select("  SELECT user_id,avatar,signature,gender,nick_name FROM user" +
            "  WHERE user_id LIKE CONCAT('%', #{keyword}, '%')" +
            "    AND is_deleted = 0")
    @DataLayer(value = "根据userId进行模糊查询",module = ModuleConstant.MODULE_FUZZY_QUERY_USER_INFO_BY_KEY_WORD)
    List<User> selectByUserIdByLike(String userId);
}
