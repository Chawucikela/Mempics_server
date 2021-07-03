package com.nowcoder.seckill.controller.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.common.ResponseModel;
import com.nowcoder.seckill.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class GeneralInterceptor implements HandlerInterceptor, ErrorCode {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            System.out.println("[User: NOT_LOGIN" + "] TIME: " + System.currentTimeMillis() + " || " + "REQUEST PATH: "
                    + request.getServletPath());
        }
        else {
            System.out.println("[User: " + user.getId() + "] TIME: " + System.currentTimeMillis() + " || " + "REQUEST PATH: "
                    + request.getServletPath());
        }

        return true;
    }
}
