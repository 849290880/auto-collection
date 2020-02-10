package com.hai.autocollection.ftp.entity;

import com.hai.autocollection.ftp.config.FtpConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;

/**
 * @author created by hai on 2020/1/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WrapperFtpClient{

    private FTPClient ftpClient;

    private FtpConfig ftpConfig;

    public String buildUploadPath(String filename){
        return ftpConfig.getSpace()+"/"+ftpConfig.getWorkDir()+ "/" + filename;
    }

}
