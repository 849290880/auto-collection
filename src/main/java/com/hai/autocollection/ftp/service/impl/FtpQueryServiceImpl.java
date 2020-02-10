package com.hai.autocollection.ftp.service.impl;

import com.hai.autocollection.exception.CommonException;
import com.hai.autocollection.exception.FtpException;
import com.hai.autocollection.ftp.entity.WrapperFtpClient;
import com.hai.autocollection.ftp.service.ConnectionService;
import com.hai.autocollection.ftp.service.FtpQueryService;
import com.hai.autocollection.ftp.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author created by hai on 2020/1/19
 */
@Service
@Slf4j
public class FtpQueryServiceImpl implements FtpQueryService {

    @Autowired
    private ConnectionService connectionService;

    @Override
    @Retryable(value = {FtpException.class}, maxAttempts = 3, backoff = @Backoff(delay = 500L))
    public List<FTPFile> list(String path, FTPClient ftpClient) {

        List<FTPFile> list = null;
        try {
            list = Arrays.asList(ftpClient.listFiles(path));
        } catch (Exception e) {
            throw new FtpException("ftp查询文件失败");
        }
        return list;
    }

    @Override
    public boolean query(String path, String filename) {
        try {
            WrapperFtpClient wrapperFtpClient = connectionService.connectionServer();
            String filePath = FtpUtil.encodePath(wrapperFtpClient.buildUploadPath(filename));
            List<FTPFile> list = list(filePath, wrapperFtpClient.getFtpClient());
            return !list.isEmpty() && list.size() == 1;
        }catch (Exception e){
            log.error("ftp文件查询失败",e);
            throw new CommonException("ftp文件查询失败");
        }
    }

    @Recover
    public void recover(FtpException e) {
        log.error("连接FTP服务器异常",e);
        throw new CommonException(e.getMessage());
    }
}
