package com.nowcoder.seckill.service;

import com.nowcoder.seckill.entity.resultentity.ShareRecordResult;
import com.nowcoder.seckill.entity.ShareRecords;

import java.util.List;

public interface ShareRecordsService {
    ShareRecords publish(int userId, String title ,String description);

    ShareRecordResult getShareRecord(String shareRecordId);

    List<ShareRecordResult> getShareRecordsByUser(int userId);

    List<ShareRecordResult> getFriendsShareRecordsByUser(int userId);

    void deleteShareRecord(String shareRecordId);

    void rollbackShareRecord(String shareRecordId);
}
