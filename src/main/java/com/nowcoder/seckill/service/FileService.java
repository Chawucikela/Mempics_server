package com.nowcoder.seckill.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public interface FileService {

    void saveShareImg(MultipartFile file, String shareRecordId, int userId);

    void saveProfilePic(MultipartFile file, int userId);

    String[] getFileNameList(String shareRecordId);

    void getShareImg(HttpServletRequest request, HttpServletResponse response, String shareRecordId, String fileName);

    void getProfilePicImg(HttpServletRequest request, HttpServletResponse response, int userId);

    boolean deleteShareImgDir(String shareRecordId);
}