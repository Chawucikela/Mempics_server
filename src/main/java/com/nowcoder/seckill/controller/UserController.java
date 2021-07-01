package com.nowcoder.seckill.controller;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.common.ResponseModel;
import com.nowcoder.seckill.common.Toolbox;
import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "${nowcoder.web.path}", allowedHeaders = "*", allowCredentials = "true")
public class UserController implements ErrorCode {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private String generateOTP() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @RequestMapping(path = "/otp/{phone}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getOTP(@PathVariable("phone") String phone, HttpSession session) {
        // 生成OTP
        String otp = this.generateOTP();
        // 绑定OTP
        session.setAttribute(phone, otp);
        // 发送OTP
        logger.info("[牛客网] 尊敬的{}您好, 您的注册验证码是{}, 请注意查收!", phone, otp);

        return new ResponseModel();
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel register(String otp, User user, HttpSession session) {
        // 验证OTPd
        String realOTP = (String) session.getAttribute(user.getPhone());
        if (StringUtils.isEmpty(otp)
                || StringUtils.isEmpty(realOTP)
                || !StringUtils.equals(otp, realOTP)) {
            throw new BusinessException(PARAMETER_ERROR, "验证码不正确！");
        }

        // 加密处理
        user.setPassword(Toolbox.md5(user.getPassword()));

        // 注册用户
        userService.register(user);

        return new ResponseModel();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel login(String phone, String password, HttpSession session) {
        if (StringUtils.isEmpty(phone)
                || StringUtils.isEmpty(password)) {
            throw new BusinessException(PARAMETER_ERROR, "参数不合法！");
        }

        String md5pwd = Toolbox.md5(password);
        User user = userService.login(phone, md5pwd);
        session.setAttribute("loginUser", user);

        return new ResponseModel(user);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel logout(HttpSession session) {
        session.invalidate();
        return new ResponseModel();
    }

    @RequestMapping(path = "/status", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getUser(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        return new ResponseModel(user);
    }

    @RequestMapping(path = "/follow", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel follow(@RequestParam("id") int followingUserId, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new BusinessException(USER_NOT_LOGIN, "请先登录！");
        }
        userService.addRelationship(user.getId(), followingUserId);
        return new ResponseModel();
    }

    @RequestMapping(path = "/unfollow", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel unfollow(@RequestParam("id") int followingUserId, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new BusinessException(USER_NOT_LOGIN, "请先登录！");
        }
        userService.deleteRelationship(user.getId(), followingUserId);
        return new ResponseModel();
    }

    @RequestMapping(path = "/getfollowing", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getFollowingUsers(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new BusinessException(USER_NOT_LOGIN, "请先登录！");
        }
        List<User> resultSet =  userService.getFollowingUserList(user.getId());
        return new ResponseModel(resultSet);
    }

    @RequestMapping(path = "/getfollower", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel getFollowerUsers(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new BusinessException(USER_NOT_LOGIN, "请先登录！");
        }
        List<User> resultSet =  userService.getFollowerUserList(user.getId());
        return new ResponseModel(resultSet);
    }

    @RequestMapping(path = "/searchuser", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel searchUser(@RequestParam("keyword") String keyword, HttpSession session) {
        List<User> resultSet = userService.searchByNickname(keyword);
        return new ResponseModel(resultSet);
    }

    @RequestMapping(path = "/getuserinfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel searchUser(@RequestParam("id") int id, HttpSession session) {
        User user = userService.findUserdetailById(id);
        return new ResponseModel(user);
    }
}
