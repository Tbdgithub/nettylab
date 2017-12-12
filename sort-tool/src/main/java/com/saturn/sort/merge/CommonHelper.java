package com.saturn.sort.merge;

import java.text.DecimalFormat;

/**
 * Created by lyz on 2017/12/10.
 */
public class CommonHelper {

    public static String printDecimalRadix2( double number) {
        String pattern="######0.00";
        DecimalFormat df = new DecimalFormat(pattern);
        String result = df.format(number);
        return result;
    }

  public   static boolean isNullOrEmpty(String line)
    {

        if(line==null)
        {
            return  true;
        }

        if(line.equals(""))
        {
            return  true;
        }

        return  false;
    }

}
