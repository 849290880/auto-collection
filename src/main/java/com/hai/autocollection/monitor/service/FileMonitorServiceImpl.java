package com.hai.autocollection.monitor.service;

import com.hai.autocollection.exception.CommonException;
import com.hai.autocollection.monitor.config.FileMonitorConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Map;

/**
 * @author created by hai on 2020/1/20
 */
@Service
public class FileMonitorServiceImpl implements FileMonitorService{

    @Autowired
    private FileMonitorConfig fileMonitorConfig;

    @Override
    public void updateSetting(FileMonitorConfig config) {
        // 改变内存里面的配置属性
        synchronized (FileMonitorConfig.class){
            BeanUtils.copyProperties(config,fileMonitorConfig);
        }
        //更新配置文件
        Yaml yaml = new Yaml();
        Map map = null;
        try(InputStream systemResourceAsStream = new FileInputStream(System.getProperty("user.dir")+"/application.yml");){
            map = yaml.loadAs(systemResourceAsStream, Map.class);
            map.put("monitor",config);
        }catch (Exception e){
            throw new CommonException("配置文件更新失败");
        }

        try(FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+"/application.yml");){
            yaml.dump(map,fileWriter);
        }catch (Exception e){
            throw new CommonException("配置文件更新失败");
        }

        //TODO 唤醒扫描线程


    }
}
