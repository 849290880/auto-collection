package com.hai.autocollection.controller;

import com.hai.autocollection.ftp.service.FtpQueryService;
import com.hai.autocollection.ftp.service.FtpUploadService;
import com.hai.autocollection.monitor.config.FileMonitorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * @author created by hai on 2020/1/17
 */
@Controller
public class HelloController {

    @Autowired
    private FileMonitorConfig fileMonitorConfig;

    @Autowired
    private FtpUploadService ftpUploadService;

    @Autowired
    private FtpQueryService ftpQueryService;

    @GetMapping("hello")
    @ResponseBody
    public String hello() throws InterruptedException {
        synchronized (FileMonitorConfig.class){
            fileMonitorConfig.setInterval(2000);
            fileMonitorConfig.setHandleDirPath("D:\\monitor-test4");
        }
        return "hello";
    }

    @GetMapping("index")
    public String index(){
        return "index";
    }

    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile multipartFile){
        ftpUploadService.upload(multipartFile);
        return "ok";
    }

    @GetMapping("/query")
    @ResponseBody
    public boolean queryFileExist(String filename){
        return ftpQueryService.query("00-50-56-C0-00-08", filename);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.asList("1", "4").equals(Arrays.asList("1", "4")));
    }

    private Thread thread;

    private volatile boolean status;

    @GetMapping("start")
    @ResponseBody
    public String start(){
        Thread thread = new Thread(() -> {
            System.out.println("线程启动");
            Thread currentThread = Thread.currentThread();
            System.out.println(currentThread.hashCode());
            while (true) {
                try {
                    synchronized (currentThread) {
                        Thread.sleep(1000);
                        System.out.println("正在工作中");
                        

                        if (status) {
                            currentThread.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "start");
        this.thread = thread;
        this.thread.start();
        return "线程启动";
    }

    @GetMapping("/resume")
    @ResponseBody
    public String resume(){
        System.out.println(thread.hashCode());
        synchronized (thread){
            this.status = false;
            thread.notify();
        }
        return "线程继续执行";
    }


    @GetMapping("/stop")
    @ResponseBody
    public String stop() throws InterruptedException {
        this.status = true;
        return "线程停止了";
    }


}
