package com.hai.autocollection.ftp.util;

import java.util.Arrays;

/**
 * @author created by hai on 2019/9/7
 */
public class CalculateUtil {


   public static Integer add(Integer... integers){
       return Arrays.stream(integers).filter(integer -> integer!=null).reduce(0, (a, b) -> a + b);
   }

    public static void main(String[] args) {
        Integer integer = new Integer(1);
        Integer first = null;
        Integer add = add(integer, first);
        System.out.println(add);
    }

}
