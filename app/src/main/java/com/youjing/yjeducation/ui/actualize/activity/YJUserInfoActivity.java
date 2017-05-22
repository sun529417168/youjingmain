package com.youjing.yjeducation.ui.actualize.activity;

import android.os.Environment;
import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJCourseListModel;
import com.youjing.yjeducation.ui.dispaly.activity.AYJCourseListActivity;
import com.youjing.yjeducation.ui.dispaly.activity.AYJUserInfoActivity;
import com.youjing.yjeducation.util.MakeSign;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/31
 * Time 18:35
 */
public class YJUserInfoActivity extends AYJUserInfoActivity {

    private String TAG = "YJUserInfoActivity";
    private static final String W_ROOT_NAME = "/Mobile";
    private static final String W_CACHE_PATH = "/Cache";
    private static final String W_CACHE_IMAGE_PATH = W_CACHE_PATH + "/img";
    public static final String W_DEF_HEAD_NAME = "user_default_photo.png";

    @Override
    protected void saveCameraPhoto(File aFile) {

   /*     Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("file", aFile);
            objectMap.put("fileType", "image");
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams();
        for (String key : objectMap.keySet()) {
            params.put(key, objectMap.get(key));
        }*/

        RequestParams params = new RequestParams();
        try {
            if (aFile != null) {
                StringUtils.Log(TAG, "aFile!= null");
            } else {
                StringUtils.Log(TAG, "aFile == null");
            }
            params.put("file", aFile);
            params.put("fileType", "image");
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJGlobal.getStudentReqManager().upLoadFile(params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "upLoadFile失败s=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);


                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            JSONObject jsonObject = new JSONObject(jsonData.getString("options"));
                            String path = jsonObject.getString("path");
                            StringUtils.Log(TAG, "path=" + path);
                            changeStudentPhoto(path);
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    protected String getDefHeadFile() {
        return getCacheImagePath() + "/" + W_DEF_HEAD_NAME;
    }

    @Override
    protected String getCacheImagePath() {
        return Environment.getExternalStorageDirectory() + W_ROOT_NAME + W_CACHE_IMAGE_PATH;
    }


    private void changeStudentPhoto(final String head) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("pic", head);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SET_USER_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "changeStudentPhoto失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            StringUtils.Log(TAG, "成功s=" + s);
                            YJGlobal.getYjUser().setPic(head);
                            notifyListener(YJNotifyTag.HEAD_USER, head);
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //设置用户性别
    @Override
    protected void setUserSex(final String sex) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            if (sex.equals("男")) {
                objectMap.put("sex", "Male");
            } else if (sex.equals("女")) {
                objectMap.put("sex", "Female");
            } else if (sex.equals("保密")) {
                objectMap.put("sex", "Unknown");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SET_USER_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            StringUtils.Log(TAG, "成功s=" + s + "\n\nsex=" + sex);
                            String sexShow = "";
                            if (sex.equals("男")) {
                                sexShow = "Male";
                            } else if (sex.equals("女")) {
                                sexShow = "Female";
                            } else if (sex.equals("保密")) {
                                sexShow = "Unknown";
                            }
                            YJGlobal.getYjUser().setSex(sexShow);
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    protected void setUserBirthday(final String birthday) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("birthday", birthday);

        } catch (Exception e) {
            e.printStackTrace();
        }

        YJStudentReqManager.getServerData(YJReqURLProtocol.SET_USER_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            StringUtils.Log(TAG, "成功s=" + s);
                            YJGlobal.getYjUser().setBirthday(birthday);
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    protected void setGrade(final String id) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("gradeId", Long.parseLong(id));

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SET_USER_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "Grade 失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            StringUtils.Log(TAG, "Grade 成功s=" + s);
                            YJGlobal.getYjUser().setGradeId(id);
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    protected void setSubjet(final String id) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("subjectId", Long.parseLong(id));

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SET_USER_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "subjectId 失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            StringUtils.Log(TAG, "subjectId 成功s=" + s);
                            YJGlobal.getYjUser().setSubjectId(id);
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    protected void setNickName(final String name) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("nickName", name);

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SET_USER_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "NickName 失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            StringUtils.Log(TAG, "NickName 成功s=" + s);
                            YJGlobal.getYjUser().setNickName(name);
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    protected void setParent(final String parent) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("parentMobile", parent);

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SET_USER_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "parentMobile 失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            StringUtils.Log(TAG, "parentMobile 成功s=" + s);
                            YJGlobal.getYjUser().setParentMobile(parent);
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    protected void setQQNum(final String qqNum) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("qqNumber", qqNum);

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SET_USER_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "parentMobile 失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            StringUtils.Log(TAG, "parentMobile 成功s=" + s);
                            YJGlobal.getYjUser().setQqNumber(qqNum);
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    protected void setArea(final String area) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("addressProvinceId", area.split(",")[0].split("-")[1]);
            objectMap.put("addressCityId", area.split(",")[1].split("-")[1]);
            objectMap.put("addressDistrictId", area.split(",")[2].split("-")[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SET_USER_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "area 失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            StringUtils.Log(TAG, "area 成功s=" + s);
                            YJGlobal.getYjUser().setAddressProvinceId(area.split(",")[0].split("-")[1]);
                            YJGlobal.getYjUser().setAddressCityId(area.split(",")[1].split("-")[1]);
                            YJGlobal.getYjUser().setAddressDistrictId(area.split(",")[2].split("-")[1]);
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
