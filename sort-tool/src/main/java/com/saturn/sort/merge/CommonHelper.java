package com.saturn.sort.merge;

/**
 * Created by lyz on 2017/12/10.
 */
public class CommonHelper {
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
