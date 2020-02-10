package com.hai.autocollection.monitor.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author created by hai on 2020/1/17
 */
@ConfigurationProperties(prefix = "monitor")
@Configuration
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class FileMonitorConfig {

    //监控的目录
    private List<String> dirPath;

    // 监控时间
    private long interval;

    // 需要上传的文件后缀
    private String fileSuffix;

    // 扫描成功的文件路径
    private String handleDirPath;



}
