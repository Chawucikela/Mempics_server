# seckill

## **接口**

### 登录

Path: /user/login

Method: POST

Body-Type: urlencoded

Parameter: [phone, password]



### 发布动态

Path: /share/publish

Method: POST

Body-Type: urlencoded

Parameter: [title, description]



### 获得我的动态

Path: /share/allmypublish

Method: GET

Body-Type: null

Parameter: null



### 获得其他用户的动态

Path: /share/allhispublish

Method: GET

Body-Type: null

Parameter: [uid]



### 获得关注用户的动态

Path: /share/friendspublish

Method: GET

Body-Type: null

Parameter: null



### 获得单条动态

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



### 删除动态

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

Parameter: null


### 获取正在关注本用户的用户列表

Path: /user/getfollower

Method: GET

Body-Type: null

Parameter: null


### 根据Phone, Username, Nickname模糊查询用户

Path: /user/searchuser

Method: GET

Body-Type: null

Parameter: [keyword]



### 根据用户ID获得用户详情

Path:/user/getuserinfo

Method: GET

Body-Type: null

Parameter: [id]



### 上传头像图片

Path: /filetransfer/uploadprofilepic

Method: POST

Body-Type: form-data

Parameter: [file]



### 下载头像

Path: /filetransfer/downloadprofilepic

Method: GET

Body-Type: null

Parameter: [uid]

------



# 更新

## 接口（加粗为更新内容）



### 获得其他用户的动态

Path: /share/allhispublish

Method: GET

Body-Type: null

Parameter: [uid]



### 获得关注用户的动态

Path: /share/friendspublish

Method: GET

Body-Type: null

Parameter: null



## 数据库

### 新增表 `share_records_img_relation`
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



### 修改表 `user_info`
```sql
DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) NOT NULL DEFAULT '',
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(100) NOT NULL DEFAULT '',
  `nickname` varchar(100) NOT NULL DEFAULT '',
  `gender` tinyint NOT NULL DEFAULT '0',
  `age` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
```

