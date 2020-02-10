package com.hai.autocollection.start;

import com.hai.autocollection.ftp.service.FtpUploadService;
import com.hai.autocollection.monitor.DirMonitor;
import com.hai.autocollection.monitor.config.FileMonitorConfig;
import com.hai.autocollection.monitor.exec.FileMonitorExec;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author created by hai on 2020/1/17
 */
@Component
public class CustomApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private FtpUploadService ftpUploadService;

    private final FileMonitorConfig fileMonitorConfig;

    public CustomApplicationListener(FileMonitorConfig fileMonitorConfig) {
        this.fileMonitorConfig = fileMonitorConfig;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null) {

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(()->{

                while (true){
                    //获取配置文件中的监控的目录
                    FileMonitorConfig config = null;
                    synchronized (FileMonitorConfig.class){
                        config = new FileMonitorConfig();
                        BeanUtils.copyProperties(fileMonitorConfig,config);
                    }
                    FileMonitorExec fileMonitorExec = FileMonitorExec.get();
                    fileMonitorExec.exec(config);
                }

            });

        }
    }

}
