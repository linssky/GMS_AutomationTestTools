package uilt;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ProPertyUtil {
    private Properties property=new Properties();
    private String filepath = "compareTest.properties";

    public String get(String key){
        return property.getProperty(key);
    }

    public ProPertyUtil(String path){
        try {
            filepath = path;
            property.load(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
