## 模块资源表

|module|ControllerLayer|ManagementLayer|ServiceLoader|DataLayer|
|-----------------|-----------------|-----------------|-----------------|--------------|
|用户注销|UserController#deleteUser|||UserMapper#softDeleteUser,Grou<br>pMessageMapper#softDeleteGroup<br>MessageByUserId|
|添加好友|UserRelationController#addRela<br>tion|||UserRelationMapper#insertUserR<br>elation|
|创建群|GroupInfoController#createGrou<br>p|||GroupInfoMapper#insertGroupInf<br>o|
|用户发送私信|PrivateMessageController#sendP<br>rivateMessage|||PrivateMessageMapper#insertPri<br>vateMessage|
|删除好友|UserRelationController#deleteR<br>elation|||UserRelationMapper#softDeleteU<br>serRelation,PrivateMessageMapp<br>er#softDeleteAllPrivateMessage<br>BothSides,UserRelationMapper#s<br>oftDeleteUserAllRelations|
|获取用户所有好友信息|UserRelationController#getUser<br>AllRelationInfo,UserController<br>#getUsersByIds|||UserMapper#selectFriendUsersBy<br>UserId|
|模糊查询群|GroupInfoController#searchGrou<br>psByFuzzy|||GroupInfoMapper#searchByGroupN<br>ame|
|用户加群|GroupMemberController#addGroup<br>Application|||GroupMemberMapper#insertGroupM<br>ember|
|获取一个群所有群成员信息|GroupMemberController#getTheGr<br>oupAllMembersInfo|||GroupMemberMapper#selectGroupA<br>llMembersByGroupId|
|用户退群|GroupMemberController#deleteGr<br>oupMember|||GroupMessageMapper#updateGroup<br>MessageByUserIdAndGroupId,Grou<br>pMemberMapper#updateGroupMembe<br>rIsDeleted|
|获取一个群信息|GroupInfoController#getGroupIn<br>foById|||GroupInfoMapper#selectByGroupI<br>d|
|获取一个群的所有聊天记录|GroupMessageController#getGrou<br>pMessagesByGroupId|||GroupMessageMapper#getGroupMes<br>sageByGroupId|
|获取一个用户的信息|UserController#getUser|||UserMapper#selectOneByUserId,U<br>serMapper#updateUser|
|获取用户所有的群信息|GroupMemberController#getGroup<br>UserJoinByOwnerId|||GroupMemberMapper#getGroupMemb<br>ers|
|解散群|GroupInfoController#deleteGrou<br>pById|||GroupMessageMapper#deleteGroup<br>MessageByGroupId,GroupInfoMapp<br>er#logicalDeleteByGroupId,Grou<br>pMemberMapper#softDeleteOneGro<br>upAllMember,GroupMessageMapper<br>#softDeleteGroupMessageByGroup<br>Id|
|注册|UserController#userRegister|||UserMapper#insertUser|
|登录|UserController#userLogin|||UserMapper#selectByUserId,User<br>Mapper#selectByUserIdAndPasswo<br>rd|
|模糊查询好友信息|UserController#searchUserLikeA<br>ndNotFriend|||UserMapper#selectByUserIdByLik<br>e|
|获取双方所有聊天记录|PrivateMessageController#getBo<br>thSidePrivateMessages|||PrivateMessageMapper#selectPri<br>vateMessageByUserIdAndToUserId|
|用户发送群信息|GroupMessageController#sendGro<br>upMessage|||GroupMessageMapper#insertGroup<br>Message|
|上传文件到minio|MinioController#uploadMinioFil<br>e||||
|用户退群:用户注销||||GroupMessageMapper#softDeleteG<br>roupMessageByGroupIdAndUserId|
|需要检查双方好友状态||||UserRelationMapper#selectUserR<br>elationIsDeleted|
|更新一个用户信息||||GroupInfoMapper#updateByGroupI<br>d|
## 方法详细表


|method|returnType|parameters|description|module|annoType|
|-------------|-------------|-------------|-------------|-------------|-------------|
|UserController#deleteUser|Result|String|用户注销|用户注销|ControllerLayer|
|UserRelationController#addRela<br>tion|Result|UserRelationDto|增加一条用户好友关系信息|添加好友|ControllerLayer|
|GroupInfoController#createGrou<br>p|Result|GroupInfoDto|groupInfo插入一条数据|创建群|ControllerLayer|
|PrivateMessageController#sendP<br>rivateMessage|Result|PrivateMessageDto|好友发送私信但是不是在线语音视频聊天|用户发送私信|ControllerLayer|
|UserRelationController#deleteR<br>elation|Result|UserRelationDto|逻辑删除一个关系信息|删除好友|ControllerLayer|
|UserRelationController#getUser<br>AllRelationInfo|Result|String|获取用户所有的好友关系|获取用户所有好友信息|ControllerLayer|
|GroupInfoController#searchGrou<br>psByFuzzy|Result|String|根据群名字模糊搜索群|模糊查询群|ControllerLayer|
|GroupMemberController#addGroup<br>Application|Result|GroupMemberDto|添加一条groupMember信息|用户加群|ControllerLayer|
|GroupMemberController#getTheGr<br>oupAllMembersInfo|Result|Integer|获取一个群的所有群成员信息|获取一个群所有群成员信息|ControllerLayer|
|GroupMemberController#deleteGr<br>oupMember|Result|String Integer|修改groupMember的is_deleted|用户退群|ControllerLayer|
|GroupInfoController#getGroupIn<br>foById|Result|Integer|根据groupId获取这个群的群信息|获取一个群信息|ControllerLayer|
|GroupMessageController#getGrou<br>pMessagesByGroupId|Result|Integer|获取一个群的的所有聊天记录|获取一个群的所有聊天记录|ControllerLayer|
|UserController#getUsersByIds|Result|List|批量获取用户信息|获取用户所有好友信息|ControllerLayer|
|UserController#getUser|Result|String|获取一个用户的所有信息除了密码|获取一个用户的信息|ControllerLayer|
|GroupMemberController#getGroup<br>UserJoinByOwnerId|Result|String|获取用户的所有的群，包括创建和加入的|获取用户所有的群信息|ControllerLayer|
|GroupInfoController#deleteGrou<br>pById|Result|Integer|逻辑上删除这个群|解散群|ControllerLayer|
|UserController#userRegister|Result|UserDto|用户注册|注册|ControllerLayer|
|UserController#userLogin|Result|UserDto|用户登录|登录|ControllerLayer|
|UserController#searchUserLikeA<br>ndNotFriend|Result|String|根据关键词模糊查询结果|模糊查询好友信息|ControllerLayer|
|PrivateMessageController#getBo<br>thSidePrivateMessages|Result|String String|获取两个用户额所有就信息|获取双方所有聊天记录|ControllerLayer|
|GroupMessageController#sendGro<br>upMessage|Result|GroupMessageDto|插入一条群信息|用户发送群信息|ControllerLayer|
|MinioController#uploadMinioFil<br>e|Result|MultipartFile|上传文件|上传文件到minio|ControllerLayer|
|GroupMessageMapper#softDeleteG<br>roupMessageByGroupIdAndUserId|int|Integer String|用户退出群聊或者被踢出群|用户退群:用户注销|DataLayer|
|GroupMemberMapper#insertGroupM<br>ember|int|GroupMember|插入一条groupMerber数据|用户加群|DataLayer|
|GroupInfoMapper#insertGroupInf<br>o|int|GroupInfo|创建群聊|创建群|DataLayer|
|GroupMessageMapper#deleteGroup<br>MessageByGroupId|int|Integer|根据groupId删除这个群的全部信息|解散群|DataLayer|
|GroupInfoMapper#searchByGroupN<br>ame|List|String|模糊查群群|模糊查询群|DataLayer|
|GroupMessageMapper#updateGroup<br>MessageByUserIdAndGroupId|int|String Integer|逻辑删除某个用户在某个群所有groupMessage|用户退群|DataLayer|
|GroupInfoMapper#logicalDeleteB<br>yGroupId|int|Integer|逻辑删除群|解散群|DataLayer|
|GroupMemberMapper#softDeleteOn<br>eGroupAllMember|int|Integer|删除一个群的所有成员|解散群|DataLayer|
|UserMapper#selectByUserId|User|String|根据user_id  获取用户信息|登录|DataLayer|
|UserRelationMapper#selectUserR<br>elationIsDeleted|List|String String|查询双方的好友关系|需要检查双方好友状态|DataLayer|
|GroupMessageMapper#getGroupMes<br>sageByGroupId|List|Integer|根据群id获取所有效聊天记录|获取一个群的所有聊天记录|DataLayer|
|PrivateMessageMapper#selectPri<br>vateMessageByUserIdAndToUserId|List|String String|获取好友双方的所有正常的聊天记录|获取双方所有聊天记录|DataLayer|
|UserMapper#selectByUserIdByLik<br>e|List|String|根据userId进行模糊查询|模糊查询好友信息|DataLayer|
|PrivateMessageMapper#insertPri<br>vateMessage|int|PrivateMessage|插入一条privateMessage信息|用户发送私信|DataLayer|
|com.repository.mapper.GroupMes<br>sageMapper#getGroupAllMembersB<br>yGroupId|List|Integer|根据groupId获取一个群组所有的用户||DataLayer|
|UserMapper#selectFriendUsersBy<br>UserId|List|String|根据userId或者这个用户的所有的好友信息|获取用户所有好友信息|DataLayer|
|UserRelationMapper#softDeleteU<br>serRelation|int|UserRelation|把好友的is_deleted修改为删除状态|删除好友|DataLayer|
|com.repository.mapper.GroupMem<br>berMapper#selectGroupAllMember<br>sByGroupIdOnlyUserId|List|Integer|获取这个群的说所有用户的userId||DataLayer|
|GroupMemberMapper#selectGroupA<br>llMembersByGroupId|List|Integer|获取群成员所有下信息|获取一个群所有群成员信息|DataLayer|
|UserMapper#softDeleteUser|int|String|逻辑删除一条user数据|用户注销|DataLayer|
|GroupInfoMapper#selectByGroupI<br>d|GroupInfo|Integer|根据group_id  获取群信息|获取一个群信息|DataLayer|
|UserMapper#selectOneByUserId|User|String|查询一个用户的信息除了密码|获取一个用户的信息|DataLayer|
|GroupInfoMapper#updateByGroupI<br>d|int|GroupInfo|更新群信息|更新一个用户信息|DataLayer|
|GroupMemberMapper#getGroupMemb<br>ers|List|String|根据userId获取这个人所有加入的群的信息|获取用户所有的群信息|DataLayer|
|GroupMessageMapper#softDeleteG<br>roupMessageByUserId|int|String|删除一个人的所有群的聊天记录|用户注销|DataLayer|
|UserMapper#updateUser|int|User|更新用户信息|获取一个用户的信息|DataLayer|
|GroupMessageMapper#insertGroup<br>Message|int|GroupMessage|插入一条群groupMessage记录|用户发送群信息|DataLayer|
|UserMapper#insertUser|int|User|插入一条user数据|注册|DataLayer|
|GroupMessageMapper#softDeleteG<br>roupMessageByGroupId|int|Integer|删除一个群的所有聊天记录|解散群|DataLayer|
|PrivateMessageMapper#softDelet<br>eAllPrivateMessageBothSides|int|String String|逻辑删除一条privateMessage信息，一般来说要区分一下双方|删除好友|DataLayer|
|UserMapper#selectByUserIdAndPa<br>ssword|User|String|根据userId获取用户的个人信息，拿出来后再检查密码|登录|DataLayer|
|UserRelationMapper#insertUserR<br>elation|int|UserRelation|插入一条好友关系数据|添加好友|DataLayer|
|UserRelationMapper#softDeleteU<br>serAllRelations|int|int|逻辑上删除一个人的全部好友|删除好友|DataLayer|
|GroupMemberMapper#updateGroupM<br>emberIsDeleted|int|Integer String|修改groupMember的is_deleted为删除状态|用户退群|DataLayer|
