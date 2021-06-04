package com.nowcoder.seckill.controller;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.common.ResponseModel;
import com.nowcoder.seckill.dao.ShareRecordsMapper;
import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.service.OrderService;
import com.nowcoder.seckill.service.ShareRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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
            throw new BusinessException(PARAMETER_ERROR, "请先登录！");
        }
        shareRecordsService.publish(user.getId(), title, description);
        return new ResponseModel();
    }
}
