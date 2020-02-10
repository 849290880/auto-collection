package com.hai.autocollection.ftp.service;

import com.hai.autocollection.ftp.entity.WrapperFtpClient;
import org.apache.commons.net.ftp.FTPClient;

/**
 * @author created by hai on 2020/1/17
 */
public interface ConnectionService {


    FTPClient connectionFtpServer();

    WrapperFtpClient connectionServer();
}
