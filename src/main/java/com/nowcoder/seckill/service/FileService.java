package com.nowcoder.seckill.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileService {

    void save(MultipartFile file, String shareRecordId, int userId);

    String[] getFileNameList(String shareRecordId);

    void getFile(HttpServletRequest request, HttpServletResponse response, String shareRecordId, String fileName);
}