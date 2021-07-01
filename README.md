# seckill

## **接口**

### 登录

Path: /user/login

Method: POST

Body-Type: urlencoded

Parameter: [phone, password]



### 发布

Path: /share/publish

Method: POST

Body-Type: urlencoded

Parameter: [title, description]



### 获得我的发布

Path: /share/allmypublish

Method: GET

Body-Type: null

Parameter: null



### 获得单条发布

Path: /share/getpublish

Method: GET

Body-Type: null

Parameter: [id]



### 发布时上传图片

Path: /filetransfer/newShareImg

Method: POST

Body-Type: form-data

Parameter: [file, shareRecordId]



### 编辑时上传图片

Path: /filetransfer/addShareImg

Method: POST

Body-Type: form-data

Parameter: [file, shareRecordId]



### 下载图片

Path: /filetransfer/download

Method: GET

Body-Type: null

Parameter: [recordid, filename]



### 删除发布

Path: /share/deletepublish

Method: GET

Body-Type: null

Parameter: [id]



### 关注用户

Path: /user/follow

Method: GET

Body-Type: null

Parameter: [id]

解释：id为被关注用户的id



### 取消关注用户

Path: /user/unfollow

Method: GET

Body-Type: null

Parameter: [id]

解释：id为被关注用户的id



### 获取本用户正在关注的用户列表

Path: /user/getfollowing

Method: GET

Body-Type: null

Parameter: []



### 根据Nickname模糊查询用户

Path: /user/searchuser

Method: GET

Body-Type: null

Parameter: [keyword]



### 根据用户ID获得用户详情

Path:/user/getuserinfo

Method: GET

Body-Type: null

Parameter: [id]

------



# 更新

## 接口（加粗为更新内容）

### 关注用户

Path: /user/follow

Method: GET

Body-Type: null

Parameter: [id]

解释：id为被关注用户的id



### 取消关注用户

Path: /user/unfollow

Method: GET

Body-Type: null

Parameter: [id]

解释：id为被关注用户的id



### 获取本用户正在关注的用户列表

Path: /user/getfollowing

Method: GET

Body-Type: null

Parameter: []



### 根据Nickname模糊查询用户

Path: /user/searchuser

Method: GET

Body-Type: null

Parameter: [keyword]



### 根据用户ID获得用户详情

Path:/user/getuserinfo

Method: GET

Body-Type: null

Parameter: [id]



## 数据库

```sql
DROP TABLE IF EXISTS `share_records_img_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `share_records_img_relation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `share_record_id` varchar(50) NOT NULL,
  `file_name` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

