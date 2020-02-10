package com.hai.autocollection.ftp.service.impl;

import com.hai.autocollection.exception.CommonException;
import com.hai.autocollection.exception.FtpException;
import com.hai.autocollection.ftp.entity.WrapperFtpClient;
import com.hai.autocollection.ftp.service.ConnectionService;
import com.hai.autocollection.ftp.service.FtpUploadService;
import com.hai.autocollection.ftp.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author created by hai on 2020/1/17
 */
@Service
@Slf4j
public class FtpUploadServiceImpl implements FtpUploadService {

    @Autowired
    private ConnectionService connectionService;



    @Override
    @Retryable(value = {FtpException.class}, maxAttempts = 3, backoff = @Backoff(delay = 500L))
    public void upload(MultipartFile multipartFile) {
        try {
            upload(multipartFile.getInputStream(),multipartFile.getOriginalFilename());
        } catch (IOException e) {
            throw new CommonException("ftp上传文件异常");
        }
    }

    @Override
    @Retryable(value = {FtpException.class}, maxAttempts = 3, backoff = @Backoff(delay = 500L))
    public void upload(InputStream in,String filename) {
        FTPClient ftpClient = null;
        try(InputStream inputStream = in) {
            WrapperFtpClient wrapperFtpClient = connectionService.connectionServer();
            ftpClient = wrapperFtpClient.getFtpClient();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //检查是否有同名文件，有同名文件进行重名名
            String path = wrapperFtpClient.buildUploadPath(filename);
            String newPath = renameToExistOne(path, ftpClient);
            ftpClient.storeFile(FtpUtil.encodePath(newPath), inputStream);
        }catch (CommonException e){
            throw e;
        }catch (Exception e){
            throw new FtpException("ftp上传文件失败");
        }finally {
            if(ftpClient!=null){
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                    throw new CommonException("ftp登录失败");
                }
            }
        }
    }

    @Override
    @Retryable(value = {FtpException.class}, maxAttempts = 3, backoff = @Backoff(delay = 500L))
    public void upload(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new CommonException("ftp上传文件异常");
        }
        upload(fileInputStream,file.getName());
    }


    @Override
    public String renameToExistOne(String path, FTPClient ftpClient) {
        FTPFile[] ftpFiles = null;
        try {
            ftpFiles = ftpClient.listFiles(FtpUtil.encodePath(path));
            String filePath = null;
            if (ftpFiles!=null&&ftpFiles.length==1) { // ftp存在该文件名的文件
                filePath = FtpUtil.renameSameFileName(path);
                return renameToExistOne(filePath,ftpClient);
            }
            return path;
        } catch (IOException e) {
            throw new CommonException("ftp文件查询异常");
        }
    }

    @Recover
    public void recover(FtpException e) {
        log.error("连接FTP服务器异常",e);
        throw new CommonException(e.getMessage());
    }



}
