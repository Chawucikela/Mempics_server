package com.nowcoder.seckill.service;

import com.nowcoder.seckill.entity.ShareRecordsWithImg;
import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.entity.ShareRecords;

import java.util.List;

public interface ShareRecordsService {
    ShareRecords publish(int userId, String title ,String description);

    ShareRecordsWithImg getShareRecord(String shareRecordId);

    List<String> getShareRecordsByUser(int userId);

    void deleteShareRecord(String shareRecordId);
}
