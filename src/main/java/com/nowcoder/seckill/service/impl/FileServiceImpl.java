package com.nowcoder.seckill.service.impl;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.component.ObjectValidator;
import com.nowcoder.seckill.service.FileService;
import com.nowcoder.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService, ErrorCode {
    @Autowired
    private ObjectValidator validator;

    @Value("${file.directory}")
    private String fileDirectory;

    public void save(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(FILE_UPLOAD_FAILURE, "空文件！");
        }

        try {
            //System.out.println(file.getOriginalFilename());
            File fileDir = new File(fileDirectory);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            file.transferTo(new File(fileDirectory + file.getOriginalFilename()));
        } catch (IOException e) {
            throw new BusinessException(FILE_UPLOAD_FAILURE, "上传失败！");
        }
    }
}
