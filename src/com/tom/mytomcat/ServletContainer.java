package com.tom.mytomcat;

import com.tom.servlet.HttpServlet;
import com.tom.util.YmlUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class ServletContainer {

    private static Map<String,String> servletMaps=new LinkedHashMap<String,String>();
    private static Map<String,String> servletMappingMaps=new LinkedHashMap<String,String>();
    private static Map<String,HttpServlet> servletContainer=new LinkedHashMap<String,HttpServlet>();

    static {
        LinkedHashMap webapp= (LinkedHashMap) YmlUtil.parseYml().get("web-app");
        servletMaps= (Map<String, String>) webapp.get("servlet");
        servletMappingMaps= (Map<String, String>) webapp.get("servlet-mapping");
    }

    public static HttpServlet getHttpServlet(String path){
        if(path==null || "".equals(path.trim())|| "/".equals(path)){
            path="/index";
        }
        //如果path在servletContainer中，就直接返回所对应的HttpServlet
        if(servletContainer.containsKey(path)){
            return servletContainer.get(path);
        }

        //获取所有servlet-mapping的path，如果path中不含请求的path就返回null
        //如果servlet-mapping中包含请求的path，那就通过path得到对应的ServletName，然后通过ServletName得到ServletClass
        for(String key:servletMappingMaps.keySet()){
            String servletMappingPath=servletMappingMaps.get(key);
            if(servletMappingPath.equals(path)){
                String servletClassName=servletMaps.get(key);
                HttpServlet httpServlet=null;
                try {
                    httpServlet= (HttpServlet) Class.forName(servletClassName).newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                servletContainer.put(key,httpServlet);
                return httpServlet;
            }
        }

        return null;
    }
}
