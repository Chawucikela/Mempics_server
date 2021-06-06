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



------



# 更新

## 接口（加粗为更新内容）

### 发布时上传图片

Path: **/filetransfer/newShareImg**

Method: POST

Body-Type: form-data

Parameter: [file, shareRecordId]



### 编辑时上传图片

Path: **/filetransfer/addShareImg**

Method: POST

Body-Type: form-data

Parameter: [file, shareRecordId]



### 下载图片

Path: **/filetransfer/download**

Method: GET

Body-Type: null

Parameter: [recordid, filename]

