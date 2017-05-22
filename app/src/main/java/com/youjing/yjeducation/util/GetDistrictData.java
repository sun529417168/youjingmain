package com.youjing.yjeducation.util;

import android.content.Context;
import com.youjing.yjeducation.util.StringUtils;

import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * user  秦伟宁
 * Date 2016/9/1
 * Time 10:49
 */
public class GetDistrictData {

    public static void setData(Context context){
        File file = new File(FileUtils.getDir("youjing",context) + "/province_data.xml");
        if (!file.exists()) {
            initData(context);
        }
    }

    private static void initData(final Context context) {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_AREA, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log("GetDistrictData", "GET_AREA成功s=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log("GetDistrictData", "GET_AREA成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    JSONObject data = json.getJSONObject("data");

                    JSONArray provinceList = data.getJSONArray("provinceList");
                    writeFileData("province_data.xml", parseToXml(provinceList),context);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    // 文件写入手机
    public static void  writeFileData(String fileName, String message,Context context) {
        String filePath = FileUtils.getDir("youjing", context);
        writeTxtToFile(message, filePath, fileName);
    }

    // json转XML
    public static String parseToXml(JSONArray provinceList) {
        String xml = "";
        int length = provinceList.length();
        StringBuffer xmlnodes = new StringBuffer();
        try {
            if (length > 0) {
                xmlnodes.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                xmlnodes.append("<root>");
                for (int i = 0; i < length; i++) {
                    xmlnodes.append("<province name='" + ((JSONObject) provinceList.get(i)).getString("provinceName") + "' provinceId='" + ((JSONObject) provinceList.get(i)).getString("provinceId") + "'>");
                    int length2 = ((JSONObject) provinceList.get(i)).getJSONArray("cityVos").length();
                    for (int j = 0; j < length2; j++) {
                        xmlnodes.append("<city name='" + ((JSONObject) ((JSONObject) provinceList.get(i)).getJSONArray("cityVos").get(j)).getString("cityName") + "' cityId='" + ((JSONObject) ((JSONObject) provinceList.get(i)).getJSONArray("cityVos").get(j)).getString("cityId") + "'>");
                        int length3 = ((JSONObject) ((JSONObject) provinceList.get(i)).getJSONArray("cityVos").get(j)).getJSONArray("districtVos").length();
                        for (int k = 0; k < length3; k++) {
                            xmlnodes.append("<district name='" + ((JSONObject) ((JSONObject) ((JSONObject) provinceList.get(i)).getJSONArray("cityVos").get(j)).getJSONArray("districtVos").get(k)).getString("districtName") + "' districtId='" + ((JSONObject) ((JSONObject) ((JSONObject) provinceList.get(i)).getJSONArray("cityVos").get(j)).getJSONArray("districtVos").get(k)).getString("districtId") + "'></district>");
                        }
                        xmlnodes.append("</city>");
                    }
                    xmlnodes.append("</province>");
                }
                xmlnodes.append("</root>");
            }
            return xmlnodes.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return xmlnodes.toString();
    }


    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        StringUtils.Log("hujiachen", "path=" + strFilePath);
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
        }
    }

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            StringUtils.Log("error:", e + "");
        }
    }
}
