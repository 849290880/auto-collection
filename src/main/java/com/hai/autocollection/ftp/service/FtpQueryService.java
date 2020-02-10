package com.hai.autocollection.ftp.service;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.util.List;

/**
 * @author created by hai on 2020/1/19
 */
public interface FtpQueryService {

    /**
     * 列出某个路径下的所有文件
     * @param path
     * @return
     */
    List<FTPFile> list(String path, FTPClient ftpClient);

    /**
     * 查询某个文件再某个目录是否存在
     * @param path
     * @param filename
     * @return
     */
    boolean query(String path,String filename);

}
