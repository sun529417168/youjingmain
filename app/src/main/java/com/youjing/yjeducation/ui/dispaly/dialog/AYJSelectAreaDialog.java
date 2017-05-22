package com.youjing.yjeducation.ui.dispaly.dialog;

import android.content.res.AssetManager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.model.CityModel;
import com.youjing.yjeducation.model.DistrictModel;
import com.youjing.yjeducation.model.ProvinceModel;
import com.youjing.yjeducation.util.FileUtils;
import com.youjing.yjeducation.util.TranslationUtils;
import com.youjing.yjeducation.util.XmlParserHandler;
import com.youjing.yjeducation.wiget.PickerView;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 10:54
 */
@VLayoutTag(R.layout.dialog_area_select)
public abstract class AYJSelectAreaDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.pv_province)
    protected PickerView mPv_province;
    @VViewTag(R.id.pv_city)
    protected PickerView mPv_city;
    @VViewTag(R.id.pv_district)
    protected PickerView mPv_district;
    @VViewTag(R.id.btn_ok)
    private Button mBtnOk;
    @VViewTag(R.id.btn_cancel)
    private Button mBtnCancel;

    private String TAG = "AYJSelectAreaDialog";

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    @Override
    public void onClick(View view) {
        if (view == mBtnCancel) {
            close();
        } else if (view == mBtnOk) {

          /*  showToast("AYJSelectAreaDialog" +mProvinceDatas[mPv_province.getSelected()] +
                    mCitisDatasMap.get(mProvinceDatas[mPv_province.getSelected()])[mPv_city.getSelected()]+
                    mDistrictDatasMap.get(mCitisDatasMap.get(mProvinceDatas[mPv_province.getSelected()])[mPv_city.getSelected()])[mPv_district.getSelected()]+ "");*/
//            if (province.equals("北京市") || province.equals("重庆市") || province.equals("上海市") || province.equals("天津市")) {
//                onBtnOkClick(mProvinceDatas[mPv_province.getSelected()] +
//                        mDistrictDatasMap.get(mProvinceDatas[mPv_province.getSelected()])[mPv_district.getSelected()]);
//            } else {
            onBtnOkClick(mPv_province.getSelectedStr()+"-"+provinceList.get(mPv_province.getSelected()).getProvinceId()
                    + "," +mPv_city.getSelectedStr()+"-"+cityMap.get(mPv_province.getSelectedStr()).get(mPv_city.getSelected()).getCityId()
                    + "," +mPv_district.getSelectedStr()+"-"+districtMap.get(mPv_province.getSelectedStr()).get(mPv_city.getSelectedStr()).get(mPv_district.getSelected()).getDistrictId());
//            }

        }
    }

    @Override
    protected void onLoadedView() {
//        initProvinceDatas();
//        showData2();


        initData();
        showData();
    }

    /**
     * 解析省市区的XML数据
     */
    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getContext().getAssets();
        try {
            InputStream input = new FileInputStream(new File(FileUtils.getDir("youjing", getContext())+"/province_data.xml"));
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();

                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getDistrictId());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap    mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void showData2() {
        //设置省
        final List<String> mProvinceList = new ArrayList<>();
        for (int i = 0; i < mProvinceDatas.length; i++) {
            if (mProvinceDatas[i].length() > 4) {
                mProvinceList.add(mProvinceDatas[i].substring(0, 4) + "...");
            } else {
                mProvinceList.add(mProvinceDatas[i]);
            }
        }
        mPv_province.setData(mProvinceList);
        mPv_province.setSelected(0);
        mPv_province.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text, int index) {
                List<String> mCityList = new ArrayList<String>();
                String[] mCityString = mCitisDatasMap.get(mProvinceDatas[index]);
                for (int j = 0; j < mCityString.length; j++) {
                    if (mCityString[j].length() > 4) {
                        mCityList.add(mCityString[j].substring(0, 4) + "...");
                    } else {
                        mCityList.add(mCityString[j]);
                    }
                }

                if (mCityString.length == 1) {
                    mPv_city.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });
                } else {
                    mPv_city.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return false;
                        }
                    });
                }
                mPv_city.setData(mCityList);
                List<String> mDistrictL = new ArrayList<String>();
                String[] mCitySt = mDistrictDatasMap.get(mCitisDatasMap.get(mProvinceDatas[mPv_province.getSelected()])[mPv_city.getSelected()]);
                for (int j = 0; j < mCitySt.length; j++) {
                    if (mCitySt[j].length() > 4) {
                        mDistrictL.add(mCitySt[j].substring(0, 4) + "...");
                    } else {
                        mDistrictL.add(mCitySt[j]);
                    }
                }
                mPv_district.setData(mDistrictL);
            }
        });
        //设置地级市
        final String[] mCitySelect = mCitisDatasMap.get(mProvinceDatas[0]);
        final List<String> mCity = new ArrayList<>();
        for (int i = 0; i < mCitySelect.length; i++) {
            if (mCitySelect[i].length() > 4) {
                mCity.add(mCitySelect[i].substring(0, 4) + "...");
            } else {
                mCity.add(mCitySelect[i]);
            }
        }
        mPv_city.setData(mCity);
        mPv_city.setSelected(0);
        mPv_city.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text, int index) {
                List<String> mDistrictList = new ArrayList<String>();
                String[] mCityString = mDistrictDatasMap.get(mCitisDatasMap.get(mProvinceDatas[mPv_province.getSelected()])[index]);
                for (int j = 0; j < mCityString.length; j++) {
                    if (mCityString[j].length() > 4) {
                        mDistrictList.add(mCityString[j].substring(0, 4) + "...");
                    } else {
                        mDistrictList.add(mCityString[j]);
                    }

                }
                if (mCityString.length == 1) {
                    mPv_city.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });
                } else {
                    mPv_city.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return false;
                        }
                    });
                }
                mPv_district.setData(mDistrictList);
            }
        });
        //设置县级市
        String[] mDistrictSelect = mDistrictDatasMap.get(mCitisDatasMap.get(mProvinceDatas[0])[0]);
        final List<String> mDistrict = new ArrayList<>();
        for (int i = 0; i < mDistrictSelect.length; i++) {
            if (mDistrictSelect[i].length() > 4) {
                mDistrict.add(mDistrictSelect[i].substring(0, 4) + "...");
            } else {
                mDistrict.add(mDistrictSelect[i]);
            }
        }
        mPv_district.setData(mDistrict);
        mPv_district.setSelected(0);
    }


    //省 list 集合
    private ArrayList<ProvinceModel> provinceList = new ArrayList<>();

    //市 map 集合
    private HashMap<String, ArrayList<CityModel>> cityMap = new HashMap<>();

    //县 map 集合
    private HashMap<String, HashMap<String, ArrayList<DistrictModel>>> districtMap = new HashMap<>();


    protected void initData() {
        List<ProvinceModel> provinceModelList = TranslationUtils.getAreaList(getContext());
        if (provinceModelList != null && !provinceModelList.isEmpty()) {
            mCurrentProviceName = provinceModelList.get(0).getName();
            List<CityModel> cityList = provinceModelList.get(0).getCityList();
            if (cityList != null && !cityList.isEmpty()) {
                mCurrentCityName = cityList.get(0).getName();
                List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                mCurrentDistrictName = districtList.get(0).getName();

            }
        }

        // 构建省list
        int length = provinceModelList.size();

        for (int i = 0; i < length; i++) {
            ProvinceModel mProvinceModel = new ProvinceModel();
            mProvinceModel.setProvinceId(provinceModelList.get(i).getProvinceId());
            mProvinceModel.setName(provinceModelList.get(i).getName());
            provinceList.add(mProvinceModel);

            // 构建市map
            int length2 = provinceModelList.get(i).getCityList().size();
            ArrayList<CityModel> cityList = new ArrayList<CityModel>();
            HashMap<String, ArrayList<DistrictModel>> districtModelUpMap = new HashMap<String, ArrayList<DistrictModel>>();

            for (int j = 0; j < length2; j++) {
                CityModel mCityModel = new CityModel();

                mCityModel.setCityId(provinceModelList.get(i).getCityList().get(j).getCityId());
                mCityModel.setName(provinceModelList.get(i).getCityList().get(j).getName());
                cityList.add(mCityModel);
                cityMap.put(provinceModelList.get(i).getName(), cityList);

                // 构建区map
                int length3 = provinceModelList.get(i).getCityList().get(j).getDistrictList().size();
                ArrayList<DistrictModel> districtModelList = new ArrayList<DistrictModel>();

                for (int k = 0; k < length3; k++) {
                    DistrictModel mDistrictModel = new DistrictModel();
                    mDistrictModel.setDistrictId(provinceModelList.get(i).getCityList().get(j).getDistrictList().get(k).getDistrictId());
                    mDistrictModel.setName(provinceModelList.get(i).getCityList().get(j).getDistrictList().get(k).getName());
                    districtModelList.add(mDistrictModel);
                    districtModelUpMap.put(provinceModelList.get(i).getCityList().get(j).getName(), districtModelList);

                }// for k
            }//for j
            districtMap.put(provinceModelList.get(i).getName(), districtModelUpMap);
        }// for i


//        StringUtils.Log("hujiachen", "provinceModelList size=" + provinceModelList.size());
//        StringUtils.Log("hujiachen", "provinceList size=" + provinceList.size());
//        StringUtils.Log("hujiachen", "cityMap size=" + cityMap.get(provinceModelList.get(0).getName()).size());

        StringUtils.Log("hujiachen", "districtMap =" + provinceModelList.get(0).getName());
        StringUtils.Log("hujiachen", "districtMap =" + cityMap.get(provinceModelList.get(0).getName()).get(0).getName());
        StringUtils.Log("hujiachen", "districtMap size=" + districtMap.get(provinceModelList.get(0).getName()).get(cityMap.get(provinceModelList.get(0).getName()).get(0).getName()).size());//.get().size()
    }

    private void showData() {

        // 设置省
        int provinceLength = provinceList.size();
        final List<String> mProvinceList = new ArrayList<>();
        for (int i = 0; i < provinceLength; i++) {
            mProvinceList.add(provinceList.get(i).getName());
        }
        mPv_province.setData(mProvinceList);
        mPv_province.setSelected(0);

        mPv_province.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text, int index) {
                ArrayList<String> cityName = new ArrayList<String>();
                ArrayList<String> districtName = new ArrayList<String>();
                int length = cityMap.get(text).size();
                for (int i = 0; i < length; i++) {
                    cityName.add(cityMap.get(text).get(i).getName());
                }

                if (length == 1) {
                    mPv_city.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });
                } else {
                    mPv_city.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return false;
                        }
                    });
                }
                mPv_city.setData(cityName);


                int districtLength = districtMap.get(text).get(cityMap.get(text).get(mPv_city.getSelected()).getName()).size();
                for (int j = 0; j < districtLength; j++) {
                    districtName.add(districtMap.get(text).get(mPv_city.getSelectedStr()).get(j).getName());
                }
                mPv_district.setData(districtName);
            }

        });

        // 设置市
        final List<String> mCityList = new ArrayList<>();
        int cityLength = cityMap.get(provinceList.get(0).getName()).size();
        for (int i = 0; i < cityLength; i++) {
            mCityList.add(cityMap.get(mPv_province.getSelectedStr()).get(i).getName());
        }
        mPv_city.setData(mCityList);
        mPv_city.setSelected(0);
        mPv_city.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text, int index) {
                ArrayList<String> districtName = new ArrayList<String>();
                if(districtMap != null){
                    if (!TextUtils.isEmpty(mPv_province.getSelectedStr()) && districtMap.get(mPv_province.getSelectedStr()).get(text) != null){

                        int length = districtMap.get(mPv_province.getSelectedStr()).get(text).size();
                        for (int i = 0; i < length; i++) {
                            districtName.add(districtMap.get(mPv_province.getSelectedStr()).get(text).get(i).getName());
                        }
                        if (length == 1) {
                            mPv_city.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    return true;
                                }
                            });
                        } else {
                            mPv_city.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    return false;
                                }
                            });
                        }
                        mPv_district.setData(districtName);
                    }
                }
            }
        });

        // 设置县
        int districtLength = districtMap.get(provinceList.get(0).getName()).get(cityMap.get(provinceList.get(0).getName()).get(0).getName()).size();
        final List<String> mDistrictList = new ArrayList<>();
        for (int i = 0; i < districtLength; i++) {
            mDistrictList.add(districtMap.get(mPv_province.getSelectedStr()).get(mPv_city.getSelectedStr()).get(i).getName());
        }
        mPv_district.setData(mDistrictList);
        mPv_district.setSelected(0);
    }

    protected abstract void onBtnOkClick(String area);
}
