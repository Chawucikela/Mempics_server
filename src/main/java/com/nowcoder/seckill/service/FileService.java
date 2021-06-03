package com.nowcoder.seckill.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void save(MultipartFile file);
}