package handler;

import java.io.IOException;

import org.json.JSONObject;

import was.util.WasUtil;

public class WasConfig {

    private static JSONObject serverConfig;
    private static JSONObject virtualHostConfig;
    private static JSONObject mappingConfig;

    public WasConfig() {
        try {
        	WasUtil WasUtil = new WasUtil();
            String fileContent = WasUtil.getFileContent("server_setting.json"); // setting  파일을 읽어온다.
            serverConfig = new JSONObject(fileContent).getJSONObject("server");
            virtualHostConfig = new JSONObject(fileContent).getJSONObject("virtualhost");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String fileContent = WasUtil.getFileContent("servlet_mapping.json");
            mappingConfig = new JSONObject(fileContent);
            System.out.println(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getPort() {
        return serverConfig.getInt("port");
    }

    public static String getRootDir(String host) {
        if (virtualHostConfig.isNull(host) || virtualHostConfig.getJSONObject(host).isNull("root")) {
            return null;
        }

        return virtualHostConfig.getJSONObject(host).getString("root");
    }

    public static String getViewPage(String host, String type) {
        if (virtualHostConfig.isNull(host) || virtualHostConfig.getJSONObject(host).isNull("pages") || virtualHostConfig.getJSONObject(host).getJSONObject("pages").isNull(type)) {
            return null;
        }
        return virtualHostConfig.getJSONObject(host).getJSONObject("pages").getString(type);
    }

    public static String getServletMappingClass(String host, String url) {
        if (virtualHostConfig.isNull(host) ||
                virtualHostConfig.getJSONObject(host).isNull("servlet") ||
                mappingConfig.isNull(virtualHostConfig.getJSONObject(host).getString("servlet")) ||
                mappingConfig.getJSONObject(virtualHostConfig.getJSONObject(host).getString("servlet")).isNull(url)) {
            return null;
        }
        return mappingConfig.getJSONObject(virtualHostConfig.getJSONObject(host).getString("servlet")).getString(url);
    }

}
