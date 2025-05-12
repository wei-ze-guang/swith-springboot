# 方法查询表
| 功能      | 控制层 | 服务 | 服务子层                      | 数据层 |
|---------|-----|----|---------------------------|-----|
| 注册      |     |    |                |   insertUser   |
| 注销      |     |    |  |      softDeleteUser
| 登录      |     |    |  |   selectByUserIdAndPassword  |    
| 创建群     |     |    |  |  insertGroupInfo | 
| 解散群     |     |    |  |  logicalDeleteByGroupId  | 
| 模糊搜群    |     |    |  |  searchByGroupName | 
| 获取一个群信息 |     |    |  |  selectByGroupId| 
| 批量获取群信息 |     |    |  |  selectByGroupIds | 
| 模糊搜群    |     |    |  |  searchByGroupName | 
| 模糊搜群    |     |    |  |  searchByGroupName | 