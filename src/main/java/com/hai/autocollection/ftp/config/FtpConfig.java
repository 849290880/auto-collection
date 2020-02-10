package com.hai.autocollection.ftp.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "ftp")
@Configuration
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class FtpConfig {

    private String ip;

    private String username;

    private String password;

    private String workDir;

    private String space;

//    private String encode;
//
//    private Integer localPort; //本地客户端端口号
//
//    private String initPath; //ftp落脚点
//
//    private String uploadPath; //文件上传的路径

}