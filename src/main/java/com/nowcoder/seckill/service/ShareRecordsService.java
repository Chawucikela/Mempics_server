package com.nowcoder.seckill.service;

import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.entity.ShareRecords;

import java.util.List;

public interface ShareRecordsService {
    ShareRecords publish(int userId, String title ,String description);

    List<ShareRecords> getShareRecordsByUser(int userId);
}
