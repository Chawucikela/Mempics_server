package com.nowcoder.seckill.entity;

import java.sql.Timestamp;

public class ShareRecords {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column share_records.id
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column share_records.user_id
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column share_records.title
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column share_records.description
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column share_records.share_time
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    private Timestamp shareTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column share_records.id
     *
     * @return the value of share_records.id
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column share_records.id
     *
     * @param id the value for share_records.id
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column share_records.user_id
     *
     * @return the value of share_records.user_id
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column share_records.user_id
     *
     * @param userId the value for share_records.user_id
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column share_records.title
     *
     * @return the value of share_records.title
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column share_records.title
     *
     * @param title the value for share_records.title
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column share_records.description
     *
     * @return the value of share_records.description
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column share_records.description
     *
     * @param description the value for share_records.description
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column share_records.share_time
     *
     * @return the value of share_records.share_time
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    public Timestamp getShareTime() {
        return shareTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column share_records.share_time
     *
     * @param shareTime the value for share_records.share_time
     *
     * @mbg.generated Fri Jun 04 12:46:02 CST 2021
     */
    public void setShareTime(Timestamp shareTime) {
        this.shareTime = shareTime;
    }
}