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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Random;
import java.io.*;

@Controller
@RequestMapping("/file")
@CrossOrigin(origins = "${nowcoder.web.path}", allowedHeaders = "*", allowCredentials = "true")
public class UploadController implements ErrorCode {
    @Autowired
    private FileService fileService;

    @RequestMapping(value="/uploadPage")
    public String uploadPage(){
        return "uploadPage";
    }

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel upload(@RequestParam("file") MultipartFile file) {
        fileService.save(file);
        return new ResponseModel();
    }
}
