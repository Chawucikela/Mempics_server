package com.nowcoder.seckill.controller;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.common.ResponseModel;
import com.nowcoder.seckill.dao.ShareRecordsMapper;
import com.nowcoder.seckill.entity.ShareRecordsWithImg;
import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.entity.ShareRecords;
import com.nowcoder.seckill.service.OrderService;
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
        if (user == null) {
            throw new BusinessException(USER_NOT_LOGIN, "请先登录！");
        }
        ShareRecords shareRecords = shareRecordsService.publish(user.getId(), title, description);
        shareRecords.setUserId(0);
        return new ResponseModel(shareRecords);
    }

    @RequestMapping(path = "/allmypublish", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getUserShareRecords(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new BusinessException(USER_NOT_LOGIN, "请先登录！");
        }

        List<String> idList = shareRecordsService.getShareRecordsByUser(user.getId());
        return new ResponseModel(idList);
    }

    @RequestMapping(path = "/getpublish", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getpublish(@RequestParam("id") String shareRecordId, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new BusinessException(USER_NOT_LOGIN, "请先登录！");
        }

        ShareRecordsWithImg shareRecordsWithImg = shareRecordsService.getShareRecord(shareRecordId);
        return new ResponseModel(shareRecordsWithImg);
    }

    @RequestMapping(path = "/deletepublish", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel deletePublish(@RequestParam("id") String shareRecordId, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new BusinessException(USER_NOT_LOGIN, "请先登录！");
        }

        ShareRecordsWithImg shareRecordsWithImg = shareRecordsService.getShareRecord(shareRecordId);
        return new ResponseModel(shareRecordsWithImg);
    }
}
