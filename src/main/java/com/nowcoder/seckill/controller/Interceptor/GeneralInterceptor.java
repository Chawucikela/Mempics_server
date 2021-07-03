package com.nowcoder.seckill.controller.Interceptor;

import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Component
public class GeneralInterceptor implements HandlerInterceptor, ErrorCode {

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        String ip = getIpAddr(request);
        if (user == null) {
            System.out.println("[IP: " + ip + " || User: NOT_LOGIN" + "] TIME: " + System.currentTimeMillis() + " || " + "REQUEST PATH: "
                    + request.getServletPath());
        }
        else {
            System.out.println("[IP: " + ip + " || User: " + user.getId() + "] TIME: " + System.currentTimeMillis() + " || " + "REQUEST PATH: "
                    + request.getServletPath());
        }

        return true;
    }


}
