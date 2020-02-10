package com.hai.autocollection.ftp.service.impl;

import com.hai.autocollection.exception.CommonException;
import com.hai.autocollection.exception.FtpException;
import com.hai.autocollection.ftp.entity.WrapperFtpClient;
import com.hai.autocollection.ftp.service.ConnectionService;
import com.hai.autocollection.ftp.config.FtpConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author created by hai on 2020/1/17
 */
@Service
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

    private final FtpConfig ftpConfig;

    public ConnectionServiceImpl(FtpConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    @Override
    @Retryable(value = {FtpException.class}, maxAttempts = 3, backoff = @Backoff(delay = 500L))
    public FTPClient connectionFtpServer() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpConfig.getIp());
            ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());
            ftpClient.setControlEncoding("utf-8");
            ftpClient.makeDirectory(ftpConfig.getWorkDir());
            ftpClient.changeWorkingDirectory(ftpConfig.getWorkDir());
        } catch (IOException e) {
            throw new FtpException("连接FTP服务器异常");
        }
        return ftpClient;
    }

    @Override
    public WrapperFtpClient connectionServer() {
        FTPClient ftpClient = connectionFtpServer();
        return WrapperFtpClient.builder().ftpClient(ftpClient).ftpConfig(ftpConfig).build();
    }

    @Recover
    public void recover(FtpException e) {
        log.error("连接FTP服务器异常",e);
        throw new CommonException("连接FTP服务器异常");
    }


}
