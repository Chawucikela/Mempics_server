package com.nowcoder.seckill.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public interface FileService {

    void save(MultipartFile file, String shareRecordId, int userId);

    String[] getFileNameList(String shareRecordId);

    void getFile(HttpServletRequest request, HttpServletResponse response, String shareRecordId, String fileName);

    boolean deleteShareImgDir(String shareRecordId);
}