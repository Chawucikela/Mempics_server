package com.nowcoder.seckill.controller;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.common.ResponseModel;
import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.service.FileService;
import com.nowcoder.seckill.service.ShareRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/filetransfer")
@CrossOrigin(origins = "${nowcoder.web.path}", allowedHeaders = "*", allowCredentials = "true")
public class FileTransferController implements ErrorCode {
    @Autowired
    private FileService fileService;

    @Autowired
    private ShareRecordsService shareRecordsService;

    @Value("${file.size-limit}")
    private Float fileSizeLimit;

    @RequestMapping(value="/newShareImg", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel newImgToShareRecord(@RequestParam("file") MultipartFile file, String shareRecordId, HttpSession session) {
        if (shareRecordId == null || shareRecordId.isEmpty()) {
            shareRecordsService.deleteShareRecord(shareRecordId);
            throw new BusinessException(PARAMETER_ERROR, "参数异常！");
        }
        Float fileSize = Float.parseFloat(String.valueOf(file.getSize())) / 1024;

        if (fileSize > fileSizeLimit) {
            shareRecordsService.rollbackShareRecord(shareRecordId);
            throw new BusinessException(FILE_SIZE_LIMIT, "文件体积超出限制！");
        }
        User user = (User) session.getAttribute("loginUser");

        try {
            
            fileService.saveShareImg(file, shareRecordId, user.getId());
        } catch (Exception e) {
            shareRecordsService.rollbackShareRecord(shareRecordId);
            throw e;
        }

        return new ResponseModel();
    }

    @RequestMapping(value="/uploadprofilepic", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel uploadProfilePic(@RequestParam("file") MultipartFile file, HttpSession session) {

        Float fileSize = Float.parseFloat(String.valueOf(file.getSize())) / 1024;

        if (fileSize > fileSizeLimit) {
            throw new BusinessException(FILE_SIZE_LIMIT, "文件体积超出限制！");
        }
        User user = (User) session.getAttribute("loginUser");

        try {
            fileService.saveProfilePic(file, user.getId());
        } catch (Exception e) {
            throw e;
        }

        return new ResponseModel();
    }

    @RequestMapping(value="/addShareImg", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel addImgToShareRecord(@RequestParam("file") MultipartFile file, String shareRecordId, HttpSession session) {
        if (shareRecordId == null || shareRecordId.isEmpty()) {
            throw new BusinessException(PARAMETER_ERROR, "参数异常！");
        }
        Float fileSize = Float.parseFloat(String.valueOf(file.getSize())) / 1024;

        if (fileSize > fileSizeLimit) {
            throw new BusinessException(FILE_SIZE_LIMIT, "文件体积超出限制！");
        }
        User user = (User) session.getAttribute("loginUser");

        try {
            fileService.saveShareImg(file, shareRecordId, user.getId());
        } catch (Exception e) {
            throw e;
        }

        return new ResponseModel();
    }

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void getShareImg(HttpServletRequest request, HttpServletResponse response,
                        HttpSession session,
                        @RequestParam("recordid") String shareRecordId,
                        @RequestParam("filename") String fileName) {
        fileService.getShareImg(request, response, shareRecordId, fileName);
        return;
    }

    @RequestMapping(path = "/downloadprofilepic", method = RequestMethod.GET)
    @ResponseBody
    public void getProfilePic(@RequestParam("uid") int id, HttpServletRequest request, HttpServletResponse response,
                        HttpSession session) {
        fileService.getProfilePicImg(request, response, id);
        return;
    }
}
