package com.nowcoder.seckill.entity;

import java.util.List;

public class ShareRecordsWithImg {

    private ShareRecords shareRecords;

    private List<String> fileNameList;

    public ShareRecords getShareRecords() {
        return shareRecords;
    }

    public void setShareRecords(ShareRecords shareRecords) {
        this.shareRecords = shareRecords;
    }

    public List<String> getFileNameList() {
        return fileNameList;
    }

    public void setFileNameList(List<String> fileNameList) {
        this.fileNameList = fileNameList;
    }
}
