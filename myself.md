## 模块资源表

|module|ControllerLayer|ManagementLayer|ServiceLoader|DataLayer|
|-----------------|-----------------|-----------------|-----------------|--------------|
|用户注销账号|UserController#deleteUser|||UserMapper#softDeleteUser|
|添加好友|UserRelationController#addRela<br>tion|||UserRelationMapper#addFriend|
|获取一个群的聊天记录|GroupMessageController#getGrou<br>pMessagesByGroupId||||
|创建群聊|GroupInfoController#createGrou<br>p||||
|发送私信|PrivateMessageController#sendP<br>rivateMessage||||
|删除好友|UserRelationController#deleteR<br>elation|||UserRelationMapper#deleteFrien<br>d,PrivateMessageMapper#logical<br>lyDeletePrivateMessageById|
|模糊搜索群|GroupInfoController#searchGrou<br>psByFuzzy||||
|用户退出群聊或者被踢出群|GroupMemberController#deleteGr<br>oupApplication||||
|获取群信息|GroupInfoController#getGroupBy<br>Id||||
|获取一个用户的他的全部好友|UserController#getUsersByIds||||
|用户加入和创建的所有群信息|GroupMemberController#getGroup<br>UserJoinByOwnerId||||
|界山群聊|GroupInfoController#deleteGrou<br>pById||||
|注册|UserController#userRegister|||UserMapper#insertUser|
|获取用户所有的聊天记录|PrivateMessageController#getCh<br>atRecords||||
|登录|UserController#userLogin|||UserMapper#selectByUserId|
|添加好友时候搜索|UserController#searchUserLikeA<br>ndNotFriend||||
|发送群信息|GroupMessageController#sendGro<br>upMessage|||GroupMessageMapper#insertGroup<br>Message|
|上传文件|MinioController#uploadMinioFil<br>e||||
|获取一个群所有聊天记录||||GroupMessageMapper#getGroupMes<br>sageByGroupId|
|模糊查询好友||||UserMapper#selectByUserIdByLik<br>e|
|发送好友私信||||PrivateMessageMapper#insertPri<br>vateMessage|
|获取用户所有好友信息||||UserMapper#selectFriendUsersBy<br>UserId|
|建群||||GroupInfoMapper#insertGroupInf<br>o|
|获取好友双方所有聊天记录||||PrivateMessageMapper#getAllAct<br>iveMessages|
|群主解散群||||GroupMessageMapper#deleteGroup<br>MessageByGroupId|
|获取群聊||||GroupInfoMapper#selectByGroupI<br>d|
|更新群||||GroupInfoMapper#updateByGroupI<br>d|
|获取用户的所有群信息||||GroupMemberMapper#getGroupMemb<br>ers|
|用户退群||||GroupMessageMapper#updateGroup<br>MessageByUserIdAndGroupId|
|需要检查双方好友状态||||UserRelationMapper#selectUserR<br>elationIsDeleted|
|用户添加群聊||||GroupMemberMapper#insertGroupM<br>ember|
|用户退出群聊||||GroupMemberMapper#updateGroupM<br>emberIsDeleted|
## 方法详细表


|method|returnType|parameters|description|module|annoType|
|-------------|-------------|-------------|-------------|-------------|-------------|
|UserController#deleteUser|Result|String|用户注销|用户注销账号|ControllerLayer|
|UserRelationController#addRela<br>tion|Result|UserRelationDto|增加一条用户好友关系信息|添加好友|ControllerLayer|
|GroupMessageController#getGrou<br>pMessagesByGroupId|Result|String|获取一个群的的所有聊天记录|获取一个群的聊天记录|ControllerLayer|
|GroupInfoController#createGrou<br>p|Result|GroupInfoDto|groupInfo插入一条数据|创建群聊|ControllerLayer|
|PrivateMessageController#sendP<br>rivateMessage|Result|PrivateMessageDto|好友发送私信但是不是在线语音视频聊天|发送私信|ControllerLayer|
|UserRelationController#deleteR<br>elation|Result|UserRelationDto|逻辑删除一个关系信息|删除好友|ControllerLayer|
|GroupInfoController#searchGrou<br>psByFuzzy|Result|String|根据群名字模糊搜索群|模糊搜索群|ControllerLayer|
|com.chat.web.controller.GroupM<br>emberController#addGroupApplic<br>ation|Result|GroupMemberDto|条件一条groupMember信息||ControllerLayer|
|GroupMemberController#deleteGr<br>oupApplication|Result|String Integer|修改groupMember的is_deleted|用户退出群聊或者被踢出群|ControllerLayer|
|GroupInfoController#getGroupBy<br>Id|Result|Long|根据groupId获取这个群的群信息|获取群信息|ControllerLayer|
|UserController#getUsersByIds|Result|List|批量获取用户信息|获取一个用户的他的全部好友|ControllerLayer|
|GroupMemberController#getGroup<br>UserJoinByOwnerId|Result|String|获取用户的所有的群，包括创建和加入的|用户加入和创建的所有群信息|ControllerLayer|
|GroupInfoController#deleteGrou<br>pById|Result|Integer|逻辑上删除这个群|界山群聊|ControllerLayer|
|UserController#userRegister|Result|UserDto|用户注册|注册|ControllerLayer|
|PrivateMessageController#getCh<br>atRecords|Result|String String|获取两个用户额所有就信息|获取用户所有的聊天记录|ControllerLayer|
|UserController#userLogin|Result|UserDto|用户登录|登录|ControllerLayer|
|UserController#searchUserLikeA<br>ndNotFriend|Result|String|根据关键词模糊查询结果|添加好友时候搜索|ControllerLayer|
|GroupMessageController#sendGro<br>upMessage|Result|GroupMessageDto|插入一条群信息|发送群信息|ControllerLayer|
|MinioController#uploadMinioFil<br>e|Result|MultipartFile|上传文件|上传文件|ControllerLayer|
|GroupMessageMapper#getGroupMes<br>sageByGroupId|List|Integer|根据群id获取所有效聊天记录|获取一个群所有聊天记录|DataLayer|
|UserMapper#selectByUserIdByLik<br>e|List|String|根据userId进行模糊查询|模糊查询好友|DataLayer|
|PrivateMessageMapper#insertPri<br>vateMessage|int|PrivateMessage|插入一条privateMessage信息|发送好友私信|DataLayer|
|com.repository.mapper.GroupMes<br>sageMapper#getGroupAllMembersB<br>yGroupId|List|Integer|根据groupId获取一个群组所有的用户||DataLayer|
|UserMapper#selectFriendUsersBy<br>UserId|List|String|根据userId或者这个用户的所有的好友信息|获取用户所有好友信息|DataLayer|
|GroupInfoMapper#insertGroupInf<br>o|int|GroupInfo|创建群聊|建群|DataLayer|
|PrivateMessageMapper#getAllAct<br>iveMessages|List|String String|获取好友双方的所有正常的聊天记录|获取好友双方所有聊天记录|DataLayer|
|GroupMessageMapper#deleteGroup<br>MessageByGroupId|int|Integer|跟你groupId删除这个群的全部信息|群主解散群|DataLayer|
|UserMapper#softDeleteUser|int|String|逻辑删除一条user数据|用户注销账号|DataLayer|
|UserRelationMapper#deleteFrien<br>d|int|String String|把好友的is_deleted修改为删除状态|删除好友|DataLayer|
|GroupInfoMapper#selectByGroupI<br>d|GroupInfo|Long|根据group_id  获取群信息|获取群聊|DataLayer|
|UserRelationMapper#addFriend|int|String String String|插入一条好友关系数据|添加好友|DataLayer|
|GroupInfoMapper#updateByGroupI<br>d|int|GroupInfo|更新群信息|更新群|DataLayer|
|GroupMemberMapper#getGroupMemb<br>ers|List|String|根据userId获取这个人所有加入的群的信息|获取用户的所有群信息|DataLayer|
|PrivateMessageMapper#logically<br>DeletePrivateMessageById|int|String String|逻辑删除一条privateMessage信息，一般来说要区分一下双方|删除好友|DataLayer|
|GroupMessageMapper#insertGroup<br>Message|int|GroupMessage|插入一条群groupMessage记录|发送群信息|DataLayer|
|GroupMessageMapper#updateGroup<br>MessageByUserIdAndGroupId|int|String Integer|逻辑删除某个用户在某个群所有groupMessage|用户退群|DataLayer|
|UserMapper#insertUser|int|User|插入一条user数据|注册|DataLayer|
|UserMapper#selectByUserId|User|String|根据user_id  获取用户信息|登录|DataLayer|
|UserRelationMapper#selectUserR<br>elationIsDeleted|int|String String|查询双方的好友关系|需要检查双方好友状态|DataLayer|
|GroupMemberMapper#insertGroupM<br>ember|int|Long String Integer|插入一条groupMerber数据|用户添加群聊|DataLayer|
|GroupMemberMapper#updateGroupM<br>emberIsDeleted|int|Long String|修改groupMember的is_deleted为删除状态|用户退出群聊|DataLayer|
