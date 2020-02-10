package com.hai.autocollection.ftp.service;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @Author HAI
 * @Date 2019/8/28 1:24
 * @Version 1.0
 */
public interface FtpUploadService {

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    void upload(MultipartFile multipartFile);

    /**
     * 上传文件
     * @param inputStream
     */
    void upload(InputStream inputStream,String filename);

    /**
     * 上传文件
     * @param file
     */
    void upload(File file);

    /**
     * 根据传入绝对路径的文件名，命名在ftp的目录上，唯一存在的文件名
     * 文件名命名规则： 文件名 + (N) + 文件名后缀的方式
     * @param path
     * @param ftpClient
     * @return
     */
    String renameToExistOne(String path,FTPClient ftpClient);

}
