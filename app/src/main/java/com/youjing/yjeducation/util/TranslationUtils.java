package com.youjing.yjeducation.util;

import android.content.Context;
import android.os.Environment;

import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.model.ProvinceModel;
import com.youjing.yjeducation.model.YJSubjectModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by w7 on 2016/4/30.
 * 修改个人信息，年级，科目 工具类
 */
public class TranslationUtils {

    public static final String TAG = "hujiachen";

    public static String translationIdGradeName(String id) {
        String GradeName = "";
        int position = Integer.parseInt(id);
        int length = YJGlobal.getGradeList().size();
        for (int i = 0; i < length; i++) {
            if (Integer.parseInt(YJGlobal.getGradeList().get(i).getGradeId()) == position) {
                GradeName = YJGlobal.getGradeList().get(i).getGradeName();
                return GradeName;
            }
        }
        return "未选择";
    }

    public static String translationNameGradeId(String Name) {
        String GradeName = "";
        int length = YJGlobal.getGradeList().size();
        for (int i = 0; i < length; i++) {
            if (YJGlobal.getGradeList().get(i).getGradeName().equals(Name)) {
                GradeName = YJGlobal.getGradeList().get(i).getGradeId();
                return GradeName;
            }
        }
        return "0";
    }

    private static List<YJSubjectModel> list;

    public static String translationIdSubjectName(String id) {
        String GradeName = "";
        int p = YJGlobal.getGradeList().size();
        for (int j = 0; j < p; j++) {
            if (YJGlobal.getGradeList().get(j).getGradeId().equals(YJGlobal.getYjUser().getGradeId())) {
                list = YJGlobal.getGradeList().get(j).getSubjectVos();
            }
        }
        int length = list.size();
        for (int i = 0; i < length; i++) {
            if (list.get(i).getSubjectId().equals(id)) {
                GradeName = list.get(i).getSubjectName();
                return GradeName;
            }
        }
        return "未选择";
    }

    public static String translationNameSubjectId(String Name) {
        String GradeName = "";
        int p = YJGlobal.getGradeList().size();
        for (int j = 0; j < p; j++) {
            if (YJGlobal.getGradeList().get(j).getGradeId().equals(YJGlobal.getYjUser().getGradeId())) {
                list = YJGlobal.getGradeList().get(j).getSubjectVos();
            }
        }

        int length = list.size();

        for (int i = 0; i < length; i++) {
            if (list.get(i).getSubjectName().equals(Name)) {
                GradeName = list.get(i).getSubjectId();
                return GradeName;
            }
        }
        return "0";
    }

    public static int searchNameByIdSubect(String id) {
        int postion = 0;
        int length = YJGlobal.getGradeList().size();
        for (int i = 0; i < length; i++) {
            if (Integer.parseInt(YJGlobal.getGradeList().get(i).getGradeId()) == Integer.parseInt(id)) {
                postion = i;
                return postion;
            }
        }
        return 0;
    }


    // 获取 地域的list
    public static List<ProvinceModel> getAreaList(Context context) {
        XmlParserHandler handler = null;
        try {
            InputStream input = new FileInputStream(new File(FileUtils.getDir("youjing", context)+"/province_data.xml"));
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return handler.getDataList();
    }

    // 根据省份ID 获取省份Name
    public static String searchIdProvinceName(String id,Context context) {
        List<ProvinceModel> provinceModelList = getAreaList(context);
        int length = provinceModelList.size();
        String ProvinceName = "";
        for (int i = 0; i < length; i++) {
            if (provinceModelList.get(i).getProvinceId().equals(id)) {
                ProvinceName = provinceModelList.get(i).getName();
            }
        }
        return ProvinceName;
    }

    // 根据省份ID 获取省份脚标
    public static int getProvincePosition(String id,Context context) {
        List<ProvinceModel> provinceModelList = getAreaList(context);
        int length = provinceModelList.size();
        int ProvincePostion = 0;
        for (int i = 0; i < length; i++) {
            if (provinceModelList.get(i).getProvinceId().equals(id)) {
                ProvincePostion = i;
            }
        }
        return ProvincePostion;
    }

    // 根据市区ID 获取市区Name
    public static String searchIdCityName(String id, String ProvinceId,Context context) {
        List<ProvinceModel> provinceModelList = getAreaList(context);
        int length = provinceModelList.get(getProvincePosition(ProvinceId,context)).getCityList().size();
        String CityName = "";
        for (int i = 0; i < length; i++) {
            if (provinceModelList.get(getProvincePosition(ProvinceId,context)).getCityList().get(i).getCityId().equals(id)) {
                CityName = provinceModelList.get(getProvincePosition(ProvinceId,context)).getCityList().get(i).getName();
            }
        }
        return CityName;
    }

    // 根据市区ID 获取市区脚标
    public static int getCityPosition(String id, String ProvinceId,Context context) {
        List<ProvinceModel> provinceModelList = getAreaList(context);
        int length = provinceModelList.get(getProvincePosition(ProvinceId,context)).getCityList().size();
        int ProvincePostion = 0;
        for (int i = 0; i < length; i++) {
            if (provinceModelList.get(getProvincePosition(ProvinceId,context)).getCityList().get(i).getCityId().equals(id)) {
                ProvincePostion = i;
            }
        }
        return ProvincePostion;
    }

    // 根据县ID 获取县Name
    public static String searchIdDistrictName(String id, String ProvinceId, String CityId,Context context) {
        List<ProvinceModel> provinceModelList = getAreaList(context);
        int length = provinceModelList.get(getProvincePosition(ProvinceId,context)).getCityList().get(getCityPosition(CityId, ProvinceId,context)).getDistrictList().size();
        int provincePosition = getProvincePosition(ProvinceId,context);
        int cityPosition = getCityPosition(CityId, ProvinceId,context);
        String DistrictName = "";
        for (int i = 0; i < length; i++) {
            if (provinceModelList.get(provincePosition).getCityList().get(cityPosition).getDistrictList().get(i).getDistrictId().equals(id)) {
                DistrictName = provinceModelList.get(provincePosition).getCityList().get(cityPosition).getDistrictList().get(i).getName();
            }
        }
        return DistrictName;
    }

}
