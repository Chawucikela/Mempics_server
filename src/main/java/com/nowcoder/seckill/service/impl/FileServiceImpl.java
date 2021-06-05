package com.nowcoder.seckill.service.impl;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.common.Toolbox;
import com.nowcoder.seckill.component.ObjectValidator;
import com.nowcoder.seckill.dao.SerialNumberMapper;
import com.nowcoder.seckill.dao.ShareRecordsMapper;
import com.nowcoder.seckill.entity.SerialNumber;
import com.nowcoder.seckill.entity.ShareRecords;
import com.nowcoder.seckill.entity.User;
import com.nowcoder.seckill.service.FileService;
import com.nowcoder.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService, ErrorCode {
    @Autowired
    private ObjectValidator validator;

    @Autowired
    private UserService userService;

    @Autowired
    private SerialNumberMapper serialNumberMapper;

    @Autowired
    private ShareRecordsMapper shareRecordsMapper;

    @Value("${file.directory}")
    private String fileDirectory;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String generateFileNameSerial() {
        StringBuilder sb = new StringBuilder();

        // 拼入日期
        sb.append(Toolbox.format(new Date(), "yyyyMMdd"));

        // 获取流水号
        SerialNumber serial = serialNumberMapper.selectByPrimaryKey("file_name_serial");
        Integer value = serial.getValue();

        // 更新流水号
        serial.setValue(value + serial.getStep());
        serialNumberMapper.updateByPrimaryKey(serial);

        // 拼入流水号
        String prefix = "000000000000".substring(value.toString().length());
        sb.append(prefix).append(value);

        return sb.toString();
    }

    public void save(MultipartFile file, String shareRecordId, int userId) {
        if (file.isEmpty()) {
            throw new BusinessException(FILE_UPLOAD_FAILURE, "空文件！");
        }
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1,
                file.getOriginalFilename().length());
        if (!suffix.toUpperCase().equals("JPG") & !suffix.toUpperCase().equals("JPEG") & !suffix.toUpperCase().equals("PNG")) {
            throw new BusinessException(FILE_TYPE_ERROR, "不支持的文件类型！");
        }
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new BusinessException(PARAMETER_ERROR, "指定的用户不存在！");
        }
        ShareRecords shareRecord = shareRecordsMapper.selectByPrimaryKey(shareRecordId);
        if (shareRecord == null) {
            throw new BusinessException(PARAMETER_ERROR, "找不到此发布记录！");
        }
        if (shareRecord.getUserId() != userId) {
            throw new BusinessException(PARAMETER_ERROR, "该发布记录不属于此用户！");
        }

        try {
            //System.out.println(file.getOriginalFilename());
            File fileDir = new File(fileDirectory + shareRecordId + "/");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            file.transferTo(new File(fileDirectory + shareRecordId + "/" + this.generateFileNameSerial() + "." + suffix));
        } catch (IOException e) {
            throw new BusinessException(FILE_UPLOAD_FAILURE, "上传失败！");
        }
    }

    public String[] getFileNameList(String shareRecordId) {
        File fileDir = new File(fileDirectory + shareRecordId + "/");
        if (!fileDir.exists()) {
            return null;
        }
        String[] fileList = fileDir.list();
        return fileList;
    }

    public void getFile(HttpServletRequest request, HttpServletResponse response, String shareRecordId, String fileName) {
        if (fileName != null) {
            //设置文件路径
            File file = new File(fileDirectory + shareRecordId + "/" + fileName);
            //File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("image/jpeg");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            else{
                throw new BusinessException(FILE_NOT_FOUND, "找不到指定文件！");
            }
        }
        throw new BusinessException(FILE_DOWNLOAD_FAILURE, "文件下载失败！");
    }
}
