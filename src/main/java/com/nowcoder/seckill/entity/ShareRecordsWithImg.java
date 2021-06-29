package com.nowcoder.seckill.entity;

import java.util.List;

public class ShareRecordsWithImg {

    private ShareRecords shareRecords;

    private List<SRIRelation> fileNameList;

    public ShareRecords getShareRecords() {
        return shareRecords;
    }

    public void setShareRecords(ShareRecords shareRecords) {
        this.shareRecords = shareRecords;
    }

    public List<SRIRelation> getFileNameList() {
        return fileNameList;
    }

    public void setFileNameList(List<SRIRelation> fileNameList) {
        this.fileNameList = fileNameList;
    }
}
