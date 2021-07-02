package com.nowcoder.seckill.entity.resultentity;

import java.sql.Timestamp;
import java.util.List;

public class ShareRecordDetailedResult extends ShareRecordResult{
    private String id;

    private Integer userId;

    private String title;

    private String description;

    private Timestamp shareTime;

    private List<String> fileNames;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getShareTime() {
        return shareTime;
    }

    public void setShareTime(Timestamp shareTime) {
        this.shareTime = shareTime;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }
}
