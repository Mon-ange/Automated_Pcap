package utils;

import pages.common.Globals;

import java.io.IOException;
import java.util.*;

public class AppiumUtil {
    //static?
    public static void parseProperty(String name) throws IOException{
        String strKey = null;
        String strValue = null;
        Properties properties = new Properties();
        properties.load(AppiumUtil.class.getResourceAsStream(name));
        Enumeration<?> enumeration = properties.propertyNames();
        while(enumeration.hasMoreElements()){
            strKey = (String)enumeration.nextElement();
            strValue = properties.getProperty(strKey);
            Globals.params.put(strKey,strValue);
        }

    }
}
