package com.nowcoder.seckill.controller;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.common.ResponseModel;
import com.nowcoder.seckill.common.Toolbox;
import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.service.FileService;
import com.nowcoder.seckill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.io.*;

@Controller
@RequestMapping("/file")
@CrossOrigin(origins = "${nowcoder.web.path}", allowedHeaders = "*", allowCredentials = "true")
public class FileTransferController implements ErrorCode {
    @Autowired
    private FileService fileService;

    @Value("${file.size-limit}")
    private Float fileSizeLimit;

    @RequestMapping(value="/uploadPage")
    public String uploadPage(){
        return "uploadPage";
    }

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel upload(@RequestParam("file") MultipartFile file, String shareRecordId, HttpSession session) {
        if (shareRecordId == null || shareRecordId.isEmpty()) {
            throw new BusinessException(PARAMETER_ERROR, "参数异常！");
        }
        Float fileSize = Float.parseFloat(String.valueOf(file.getSize())) / 1024;

        if (fileSize > fileSizeLimit) {
            throw new BusinessException(FILE_SIZE_LIMIT, "文件体积超出限制！");
        }
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new BusinessException(USER_NOT_LOGIN, "请先登录！");
        }
        fileService.save(file, shareRecordId, user.getId());
        return new ResponseModel();
    }

    @RequestMapping(path = "/download", method = RequestMethod.GET)
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
