package com.wjy.ssm.util;

/**
 * Created by kevin on 2016/8/30.
 */



import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfig
{


    private Properties config;

    private String vm;

    private String defaultVm = "CONF_HOME";

    public ReadConfig(String configFile){
        init(configFile);
    }

    public ReadConfig(String vm, String configFile){
        this.vm = vm;
        init(configFile);
    }

    public  Properties getConfig(){
        return  config;
    }

    private void init(String configFile)
    {
        config = initProp(configFile);

    }

    private Properties initProp(String configFile)
    {
        if(vm == null){
            vm = defaultVm;
        }

        Properties sysProps=System.getProperties(); //系统属性
        String confHome = sysProps.getProperty("CONF_HOME");

        Properties properties = new Properties();
        try {
            InputStream fis = new FileInputStream( confHome + configFile );
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(configFile+" config file does not exist", e);
        }
        return properties;
    }
}

