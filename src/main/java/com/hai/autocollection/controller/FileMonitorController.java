package com.hai.autocollection.controller;

import com.hai.autocollection.exception.CommonException;
import com.hai.autocollection.monitor.config.FileMonitorConfig;
import com.hai.autocollection.monitor.health.FileMonitorAdmin;
import com.hai.autocollection.monitor.service.FileMonitorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.util.Map;

/**
 * @author created by hai on 2020/1/20
 */
@RestController
public class FileMonitorController {

    @Autowired
    private FileMonitorService fileMonitorService;

    /**
     * 获取当前扫描线程的运行状态
     * @return
     */
    @GetMapping("health")
    public Map<String,String> queryHealthStatus(){
        return FileMonitorAdmin.get().getMap();
    }

    /**
     * 对本地扫描线程的设置并对线程进行启动获取唤醒的操作
     * @param config
     */
    @PostMapping("setting")
    public void setting(@RequestBody FileMonitorConfig config){
        fileMonitorService.updateSetting(config);
    }
}
