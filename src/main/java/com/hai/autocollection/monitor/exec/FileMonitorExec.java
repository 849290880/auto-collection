package com.hai.autocollection.monitor.exec;

import com.hai.autocollection.exception.CommonException;
import com.hai.autocollection.ftp.service.FtpUploadService;
import com.hai.autocollection.monitor.config.FileMonitorConfig;
import com.hai.autocollection.monitor.health.FileMonitorAdmin;
import com.hai.autocollection.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.spring5.context.SpringContextUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

/**
 * @author created by hai on 2020/1/20
 */
@Slf4j
public class FileMonitorExec {

    private static FileMonitorExec fileMonitorExec = new FileMonitorExec();

    private FileMonitorExec(){}

    public static FileMonitorExec get(){
        return fileMonitorExec;
    }

    public void exec(FileMonitorConfig config){
        FileMonitorAdmin fileMonitorAdmin = FileMonitorAdmin.get();
        List<String> dirPath = config.getDirPath();
        if (CollectionUtils.isEmpty(dirPath)) {
            String errMsg = "请设置扫描目录";
            //保存在全局map中,页面查看
            log.error(errMsg);
            fileMonitorAdmin.putHealMsg(errMsg);
            Thread thread = Thread.currentThread();
            try {
                thread.wait();
            } catch (InterruptedException e) {
                log.error("线程中断");
                throw new RuntimeException(e);
            }
        }

        log.info("线程执行了");
        fileMonitorAdmin.putHealMsg("normal");
        try {
            Thread.sleep(config.getInterval());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



//        FileMonitorConfig finalConfig = config;
//        FileMonitorConfig c = config;
//        dirPath.forEach(d->{
//            File file = new File(d);
//            if (file.exists()) {
//                if (file.isDirectory()) {
//
//                    File[] files = file.listFiles(new FilenameFilter() {
//                        @Override
//                        public boolean accept(File dir, String name) {
//                            if (name.contains(c.getFileSuffix())) {
//                                return true;
//                            }
//                            return false;
//                        }
//                    });
//
//                    for (File f : files) {
//                        if (f.exists()) {
//                            FtpUploadService ftpUploadService = SpringContextUtil.getBean("ftpUploadServiceImpl", FtpUploadService.class);
//                            ftpUploadService.upload(f);
//                            //上传
//                            log.info("上传到ftp文件夹中,文件名为:"+f.getName());
//
//
//                            //移动
//                            String handleDirPath = finalConfig.getHandleDirPath();
//                            File handleDir = new File(handleDirPath);
//                            if (!handleDir.exists()) {
//                                //创建并移动
//                                handleDir.mkdir();
//
//                            }else {
//                                String movePath = handleDir + "/" + f.getName();
//                                File mv = new File(movePath);
//                                if (!mv.exists()) {
//                                    f.renameTo(mv);
//
//                                }else {
//                                    //可能是有同名文件
//                                    movePath = handleDir + "/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + "-" + f.getName();
//                                    mv = new File(movePath);
//                                    if (!mv.exists()) {
//                                        f.renameTo(mv);
//                                    }else {
//                                        System.out.println("重新调用命名逻辑");
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }else{
//                file.mkdir();
//            }
//        });
//
//
//        try {
//            Thread.sleep(config.getInterval());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
