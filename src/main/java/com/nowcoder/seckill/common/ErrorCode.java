package com.nowcoder.seckill.common;

public interface ErrorCode {

    // 通用异常
    int UNDEFINED_ERROR = 0;
    int PARAMETER_ERROR = 1;

    // 用户异常
    int USER_NOT_LOGIN = 101;
    int USER_LOGIN_FAILURE = 102;

    // 业务异常
    int STOCK_NOT_ENOUGH = 201;
    int RECORD_NOT_FOUND = 404;

    //
    int FILE_UPLOAD_FAILURE = 301;
    int FILE_SIZE_LIMIT = 302;
    int FILE_TYPE_ERROR = 303;
    int FILE_DOWNLOAD_FAILURE = 304;
    int FILE_NOT_FOUND = 305;
    int FILE_DELETE_FAILURE = 306;

}
