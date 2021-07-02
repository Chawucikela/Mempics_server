package com.nowcoder.seckill.service.impl;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.common.Toolbox;
import com.nowcoder.seckill.dao.SRIRelationMapper;
import com.nowcoder.seckill.dao.SerialNumberMapper;
import com.nowcoder.seckill.dao.ShareRecordsMapper;
import com.nowcoder.seckill.dao.UserMapper;
import com.nowcoder.seckill.entity.*;
import com.nowcoder.seckill.entity.resultentity.ShareRecordDetailedResult;
import com.nowcoder.seckill.entity.resultentity.ShareRecordResult;
import com.nowcoder.seckill.entity.resultentity.ShareRecordSimplifiedResult;
import com.nowcoder.seckill.service.FileService;
import com.nowcoder.seckill.service.ShareRecordsService;
import com.nowcoder.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ShareRecordsServiceImpl implements ShareRecordsService, ErrorCode{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShareRecordsMapper shareRecordsMapper;

    @Autowired
    private SerialNumberMapper serialNumberMapper;

    @Autowired
    private SRIRelationMapper sriRelationMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    /**
     * 格式：日期 + 流水
     * 示例：20210123000000000001
     *
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String generateOrderID() {
        StringBuilder sb = new StringBuilder();

        // 拼入日期
        sb.append(Toolbox.format(new Date(), "yyyyMMdd"));

        // 获取流水号
        SerialNumber serial = serialNumberMapper.selectByPrimaryKey("share_records_serial");
        Integer value = serial.getValue();

        // 更新流水号
        serial.setValue(value + serial.getStep());
        serialNumberMapper.updateByPrimaryKey(serial);

        // 拼入流水号
        String prefix = "000000000000".substring(value.toString().length());
        sb.append(prefix).append(value);

        return sb.toString();
    }

    @Transactional
    public ShareRecords publish(int userId, String title ,String description) {
        // 校验用户
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new BusinessException(PARAMETER_ERROR, "指定的用户不存在！");
        }

        if (title == null
                || title.isEmpty()
                || description == null
                || description.isEmpty()) {
            throw new BusinessException(PARAMETER_ERROR, "参数不能为空！");
        }

        ShareRecords shareRecords = new ShareRecords();
        shareRecords.setId(this.generateOrderID());
        shareRecords.setUserId(userId);
        shareRecords.setTitle(title);
        shareRecords.setDescription(description);
        shareRecords.setShareTime(new Timestamp(System.currentTimeMillis()));

        shareRecordsMapper.insert(shareRecords);

        return shareRecords;
    }

    @Transactional
    public ShareRecordResult getShareRecord(String shareRecordId) {
        ShareRecordResult shareRecord = shareRecordsMapper.selectByPrimaryKeyDetailed(shareRecordId);
        if (shareRecord == null) {
            throw new BusinessException(RECORD_NOT_FOUND, "找不到指定条目！");
        }

        ((ShareRecordDetailedResult)shareRecord).setFileNames(sriRelationMapper
                .selectFilenameByShareRecordId(((ShareRecordDetailedResult)shareRecord).getId()));

        return shareRecord;
    }

    @Transactional
    public List<ShareRecordResult> getShareRecordsByUser(int userId) {
        List<ShareRecordResult> resultSet = shareRecordsMapper.selectByUserIdSimplified(userId);
        for (ShareRecordResult shareRecords : resultSet) {
            ((ShareRecordSimplifiedResult)shareRecords).setFileName(sriRelationMapper
                    .selectOneFilenameByShareRecordId(((ShareRecordSimplifiedResult) shareRecords).getId()));
        }
        return resultSet;
    }

    @Transactional
    public List<ShareRecordResult> getFriendsShareRecordsByUser(int userId) {
        List<ShareRecordResult> resultSet = shareRecordsMapper.selectFriendsSRByUserIdSimplified(userId);
        for (ShareRecordResult shareRecords : resultSet) {
            ((ShareRecordSimplifiedResult)shareRecords).setFileName(sriRelationMapper
                    .selectOneFilenameByShareRecordId(((ShareRecordSimplifiedResult) shareRecords).getId()));
        }
        return resultSet;
    }

    @Transactional
    public void deleteShareRecord(String shareRecordId) {
        boolean delDirResult = fileService.deleteShareImgDir(shareRecordId);
        if (delDirResult == false) {
            throw new BusinessException(FILE_DELETE_FAILURE, "文件路径删除失败！");
        }
        int delRelationResult = sriRelationMapper.deleteByShareRecordId(shareRecordId);
        if (delRelationResult == 0) {
            throw new BusinessException(RECORD_NOT_FOUND, "文件关系记录删除失败！");
        }
        int delDataResult = shareRecordsMapper.deleteByPrimaryKey(shareRecordId);
        if (delDataResult == 0) {
            throw new BusinessException(RECORD_NOT_FOUND, "记录删除失败！");
        }
    }

    @Transactional
    public void rollbackShareRecord(String shareRecordId) {
        boolean delDirResult = fileService.deleteShareImgDir(shareRecordId);
        int delRelationResult = sriRelationMapper.deleteByShareRecordId(shareRecordId);
        int delDataResult = shareRecordsMapper.deleteByPrimaryKey(shareRecordId);
        System.out.println("id:" + shareRecordId + " 发布记录已回滚");
    }
}
