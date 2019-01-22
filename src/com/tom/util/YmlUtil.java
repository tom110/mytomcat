package com.tom.util;

import org.yaml.snakeyaml.*;

import java.util.LinkedHashMap;

public class YmlUtil {

    public static LinkedHashMap parseYml(){
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
        return yaml.loadAs(YmlUtil.class.getResourceAsStream("../web.yml"), LinkedHashMap.class);
    }


    public static void main(String[] args){
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
        LinkedHashMap map = yaml.loadAs(YmlUtil.class.getResourceAsStream("../web.yml"), LinkedHashMap.class);//如果读入Map,这里可以是Mapj接口,默认实现为LinkedHashMap
        System.out.println("he");
    }
}
