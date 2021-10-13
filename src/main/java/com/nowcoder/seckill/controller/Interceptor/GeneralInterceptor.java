package com.nowcoder.seckill.controller.Interceptor;

import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.common.Toolbox;
import com.nowcoder.seckill.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Component
public class GeneralInterceptor implements HandlerInterceptor, ErrorCode {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        String ip = Toolbox.getIpAddr(request);
        if (user == null) {
            System.out.println("[IP: " + ip + " || UserId: NOT_LOGIN" + "] TIME: " + new Timestamp(System.currentTimeMillis()) + " " +
                                       "|| " + "REQUEST PATH: "
                    + request.getServletPath() + "]");
        }
        else {
            System.out.println("[IP: " + ip + " || UserId: " + user.getId() + "] TIME: " + new Timestamp(System.currentTimeMillis()) + " || " + "REQUEST PATH: "
                    + request.getServletPath() + "]");
        }
        return true;
    }


}
