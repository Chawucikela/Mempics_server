package com.nowcoder.seckill.service.impl;

import com.nowcoder.seckill.common.BusinessException;
import com.nowcoder.seckill.common.ErrorCode;
import com.nowcoder.seckill.common.Toolbox;
import com.nowcoder.seckill.component.ObjectValidator;
import com.nowcoder.seckill.dao.SRIRelationMapper;
import com.nowcoder.seckill.dao.SerialNumberMapper;
import com.nowcoder.seckill.dao.ShareRecordsMapper;
import com.nowcoder.seckill.entity.SRIRelation;
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

    @Autowired
    private SRIRelationMapper sriRelationMapper;

    @Value("${file.root.directory}")
    private String rootDirectory;

    @Value("${file.shareimg.directory}")
    private String shareDirectory;

    @Value("${file.profilepic.directory}")
    private String profilePicDirectory;

    @Value("${file.profilepic.filename}")
    private String profilePicFileName;

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

    private void sendFile(HttpServletResponse response, File file) {
        if (file.exists()) {
            response.setContentType("image/jpeg");// 设置强制下载不打开
            //response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
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
                throw new BusinessException(FILE_DOWNLOAD_FAILURE, "文件下载失败！");
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

    private boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return true;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }

        return dirFile.delete();
    }

    public void saveProfilePic(MultipartFile file, int userId) {
        if (file.isEmpty()) {
            throw new BusinessException(FILE_UPLOAD_FAILURE, "空文件！");
        }
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename()./*indexOf*/lastIndexOf(".") + 1,
                file.getOriginalFilename().length());
        if (!suffix.toUpperCase().equals("JPG") & !suffix.toUpperCase().equals("JPEG") & !suffix.toUpperCase().equals("PNG")) {
            throw new BusinessException(FILE_TYPE_ERROR, "不支持的文件类型！");
        }
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new BusinessException(PARAMETER_ERROR, "指定的用户不存在！");
        }
        //String fileName = this.generateFileNameSerial() + "." + suffix;
        try {
            //System.out.println(file.getOriginalFilename());
            File fileDir = new File(rootDirectory + profilePicDirectory + userId + "/");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            file.transferTo(new File(rootDirectory + profilePicDirectory + userId + "/" + profilePicFileName));

        } catch (IOException e) {
            throw new BusinessException(FILE_UPLOAD_FAILURE, "存储失败！");
        }
    }

    public void saveShareImg(MultipartFile file, String shareRecordId, int userId) {
        if (file.isEmpty()) {
            throw new BusinessException(FILE_UPLOAD_FAILURE, "空文件！");
        }
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename()./*indexOf*/lastIndexOf(".") + 1,
                file.getOriginalFilename().length());
        if (!suffix.toUpperCase().equals("JPG") & !suffix.toUpperCase().equals("JPEG") & !suffix.toUpperCase().equals("PNG")) {
            throw new BusinessException(FILE_TYPE_ERROR, "不支持的文件类型！");
        }
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new BusinessException(PARAMETER_ERROR, "指定的用户不存在！");
        }
        ShareRecords shareRecord = shareRecordsMapper. selectByPrimaryKey(shareRecordId);
        if (shareRecord == null) {
            throw new BusinessException(PARAMETER_ERROR, "找不到此发布记录！");
        }
        if (shareRecord.getUserId() != userId) {
            throw new BusinessException(PARAMETER_ERROR, "该发布记录不属于此用户！");
        }
        String fileName = this.generateFileNameSerial() + "." + suffix;
        try {
            //System.out.println(file.getOriginalFilename());
            File fileDir = new File(rootDirectory + shareDirectory + shareRecordId + "/");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            file.transferTo(new File(rootDirectory + shareDirectory + shareRecordId + "/" + fileName));

        } catch (IOException e) {
            throw new BusinessException(FILE_UPLOAD_FAILURE, "存储失败！");
        }
        SRIRelation sriRelation = new SRIRelation();
        sriRelation.setShareRecordId(shareRecordId);
        sriRelation.setFileName(fileName);
        int result = sriRelationMapper.insert(sriRelation);
        if (result == 0) {
            throw new BusinessException(PARAMETER_ERROR, "数据库错误！");
        }
    }

    public String[] getFileNameList(String shareRecordId) {
        File fileDir = new File(rootDirectory + shareDirectory + shareRecordId + "/");
        if (!fileDir.exists()) {
            return null;
        }
        String[] fileList = fileDir.list();
        return fileList;
    }

    public void getShareImg(HttpServletRequest request, HttpServletResponse response, String shareRecordId, String fileName) {
        if (fileName != null) {
            ShareRecords shareRecord = shareRecordsMapper.selectByPrimaryKey(shareRecordId);
            if (shareRecord == null) {
                throw new BusinessException(PARAMETER_ERROR, "找不到此发布记录！");
            }
            //设置文件路径
            File file = new File(rootDirectory + shareDirectory + shareRecordId + "/" + fileName);
            //File file = new File(realPath , fileName);
            sendFile(response, file);
        }
        else {
            throw new BusinessException(PARAMETER_ERROR, "文件参数异常！");
        }
    }

    public void getProfilePicImg(HttpServletRequest request, HttpServletResponse response, int userId) {
        File file = new File(rootDirectory + profilePicDirectory + userId + "/" + profilePicFileName);
        //File file = new File(realPath , fileName);
        sendFile(response, file);
    }

    public boolean deleteShareImgDir(String shareRecordId) {
        File fileDir = new File(rootDirectory + shareDirectory + shareRecordId + "/");
        boolean result = this.deleteFile(fileDir);
        return result;
    }


}
