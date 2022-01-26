package com.nowcoder.seckill.controller;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.common.ResponseModel;
import com.nowcoder.seckill.entity.resultentity.ShareRecordResult;
import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.entity.ShareRecords;
import com.nowcoder.seckill.service.ShareRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/share")
@CrossOrigin(origins = "${nowcoder.web.path}", allowedHeaders = "*", allowCredentials = "true")
public class ShareRecordsController implements ErrorCode {

    @Autowired
    private ShareRecordsService shareRecordsService;

    @RequestMapping(path = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel publish(HttpSession session, String title ,String description) {
        User user = (User) session.getAttribute("loginUser");

        ShareRecords shareRecords = shareRecordsService.publish(user.getId(), title, description);
        shareRecords.setUserId(0);
        return new ResponseModel(shareRecords);
    }

    @RequestMapping(path = "/allmypublish", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getUserShareRecords(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");

        List<ShareRecordResult> resultSet = shareRecordsService.getShareRecordsByUser(user.getId());
        return new ResponseModel(resultSet);
    }

    @RequestMapping(path = "/allhispublish", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getOtherUserShareRecords(@RequestParam("uid") int userId, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");

        List<ShareRecordResult> resultSet = shareRecordsService.getShareRecordsByUser(userId);
        return new ResponseModel(resultSet);
    }

    @RequestMapping(path = "/friendspublish", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getFriendsShareRecords(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");

        List<ShareRecordResult> resultSet = shareRecordsService.getFriendsShareRecordsByUser(user.getId());
        return new ResponseModel(resultSet);
    }

    @RequestMapping(path = "/getpublish", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getPublish(@RequestParam("id") String shareRecordId, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");

        ShareRecordResult shareRecord = shareRecordsService.getShareRecord(shareRecordId);
        return new ResponseModel(shareRecord);
    }

    @RequestMapping(path = "/deletepublish", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel deletePublish(@RequestParam("id") String shareRecordId, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");

        shareRecordsService.deleteShareRecord(shareRecordId);
        return new ResponseModel();
    }
}
