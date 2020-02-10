package com.hai.autocollection.monitor.service;

import com.hai.autocollection.monitor.config.FileMonitorConfig;

/**
 * @author created by hai on 2020/1/20
 */
public interface FileMonitorService {

    void updateSetting(FileMonitorConfig config);
}
