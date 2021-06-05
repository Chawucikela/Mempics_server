package com.nowcoder.seckill.controller;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.common.ResponseModel;
import com.nowcoder.seckill.entity.ShareRecords;
import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/download")
@CrossOrigin(origins = "${nowcoder.web.path}", allowedHeaders = "*", allowCredentials = "true")
public class DownloadController implements ErrorCode {

    @Autowired
    private FileService fileService;

    @RequestMapping(path = "/getfile", method = RequestMethod.GET)
    @ResponseBody
    public void getFile(HttpServletRequest request, HttpServletResponse response,
                                 HttpSession session,
                                 @RequestParam("recordid") String shareRecordId,
                                 @RequestParam("filename") String fileName) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new BusinessException(USER_NOT_LOGIN, "请先登录！");
        }
        fileService.getFile(request, response, shareRecordId, fileName);
        return;
    }
}
