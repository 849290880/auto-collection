package com.hai.autocollection.ftp.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author created by hai on 2020/1/19
 */
public class FtpUtil {

    /**
     * ftp文件编码
     * @param path
     * @return
     */
    public static String encodePath(String path) throws UnsupportedEncodingException {
        return new String(path.getBytes("utf-8"), "ISO8859-1");
    }

    /**
     * 同名文件命名， 例如 1. a.pdf --> a(1).pdf 2. a --> a(1) 3. a(1).pdf --> a(2).pdf
     * 4. a(1) --> a(2)
     *
     * @param fileName
     * @return
     */
    public static String renameSameFileName(String fileName) {
        String newFileName = null;
        int i = fileName.lastIndexOf(".");
        if (i != -1) { // 有同名文件,有.作为后缀
            String rule = "(.*)\\.(.*)";
            Matcher matcher = Pattern.compile(rule).matcher(fileName);

            if (matcher.find()) {
                String fileNameNoSuffix = matcher.group(1); // 文件名
                String suffix = matcher.group(2);
                String rule2 = "(.*)\\((\\d)\\)";
                Matcher matcher1 = Pattern.compile(rule2).matcher(fileNameNoSuffix);
                if (matcher1.find()) {// 同名的文件名是 文件名(1).pdf 这种
                    String number = matcher1.group(2);
                    Integer newNumber = CalculateUtil.add(Integer.valueOf(number), 1);
                    newFileName = fileNameNoSuffix.replaceAll(rule2, "$1").concat("(" + newNumber + ").")
                            .concat(suffix);
                } else { // 同名的文件名是 文件名.pdf这种 直接添加 (1).pdf
                    newFileName = fileName.replaceAll(rule, "$1(1).$2");
                }
            }
        } else {// 有同名文件,没有以.作为后缀
            String rule2 = "(.*)\\((\\d)\\)";
            Matcher matcher1 = Pattern.compile(rule2).matcher(fileName);
            if (matcher1.find()) {// 同名的文件名是 文件名(1) 这种
                String number = matcher1.group(2);
                Integer newNumber = CalculateUtil.add(Integer.valueOf(number), 1);
                newFileName = fileName.replaceAll(rule2, "$1").concat("(" + newNumber + ")");
            } else {
                newFileName = fileName.concat("(1)");
            }
        }
        return newFileName;
    }

}
