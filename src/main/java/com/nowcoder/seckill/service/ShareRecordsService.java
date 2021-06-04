package com.nowcoder.seckill.service;

import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.entity.ShareRecords;

public interface ShareRecordsService {
    void publish(int userId, String title ,String description);
}
