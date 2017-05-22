package com.youjing.yjeducation.question;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import android.content.Context;

import com.lecloud.volley.toolbox.image.MD5Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class RequestUrl {
    public static String sendData(Object sendUrl, Map<String, Object> data,Context  context) {
        //ContextWrapper.
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        StringBuilder stringBuilder = DataString.dataString(data);
        params.add(new BasicNameValuePair("_pkey", "Android"));
        params.add(new BasicNameValuePair("key", MakeKeyUtils.makeKey(data)));
        params.add(new BasicNameValuePair("data", stringBuilder.toString()));
        String result = HttpPostRequest.postRequest(sendUrl, params,context);
        System.out.println("发送参数：" + data);
        System.out.println("发送地址：" + sendUrl);
        System.out.println("返回结果: " + result);
        return result;
    }

    public static class DataString {
        public static StringBuilder dataString(Map<String, Object> data) {
            StringBuilder stringBuilder = new StringBuilder();
            if (data != null) {
                stringBuilder.append("{");
                Set<String> keys = data.keySet();
                for (String k : keys) {
                    stringBuilder.append("\"").append(k).append("\"").append(":");
                    if (data.get(k) instanceof List) {
                        List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(k);
                        stringBuilder.append("[");
                        for (Map<String, Object> map : list) {
                            Set<String> keysSet = map.keySet();
                            stringBuilder.append("{");
                            for (String s : keysSet) {
                                stringBuilder.append("\"").append(s).append("\"").append(":");
                                stringBuilder.append("\"").append(map.get(s)).append("\"");
                                stringBuilder.append(",");
                            }
                            if (stringBuilder.lastIndexOf(",") > 0) {
                                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                            }
                            stringBuilder.append("},");
                        }
                        if (stringBuilder.lastIndexOf(",") > 0) {
                            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        }
                        stringBuilder.append("]");
                    } else {
                        stringBuilder.append("\"").append(data.get(k)).append("\"");
                    }
                    stringBuilder.append(",");
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                stringBuilder.append("}");
            }
            return stringBuilder;
        }
    }

    public static   class MakeKeyUtils {

        /**
         * 请求参数 生成key
         *
         * @param dataMap
         * @return
         */
        public static String makeKey(Map<String, Object> dataMap) {

            StringBuilder stringBuilder = new StringBuilder();
            if (dataMap != null) {
                stringBuilder.append("{");
                Set<String> keys = dataMap.keySet();
                for (String k : keys) {
                    stringBuilder.append("\"").append(k).append("\"").append(":");
                    if (dataMap.get(k) instanceof List) {
                        List<Map<String, Object>> list = (List<Map<String, Object>>) dataMap.get(k);
                        stringBuilder.append("[");
                        for (Map<String, Object> map : list) {
                            Set<String> keysSet = map.keySet();
                            stringBuilder.append("{");
                            for (String s : keysSet) {
                                stringBuilder.append("\"").append(s).append("\"").append(":");
                                stringBuilder.append("\"").append(map.get(s)).append("\"");
                                stringBuilder.append(",");
                            }
                            if (stringBuilder.lastIndexOf(",") > 0) {
                                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                            }
                            stringBuilder.append("},");
                        }
                        if (stringBuilder.lastIndexOf(",") > 0) {
                            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        }
                        stringBuilder.append("]");
                    } else {
                        stringBuilder.append("\"").append(dataMap.get(k)).append("\"");
                    }
                    stringBuilder.append(",");
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                stringBuilder.append("}");
            }

            String data[] = {stringBuilder.toString()};

            Map<String, String[]> maps = new HashMap<String, String[]>();
            maps.put("data", data);
            maps.put("_pkey", new String[]{GlobalProperty._pkey});
            // 开始讲key + value 拼接成一个字符串
            Set<String> set = maps.keySet();
            String[] keys = new String[set.size()];
            set.toArray(keys);
            // key 排序
            Arrays.sort(keys);
            StringBuffer s = new StringBuffer();
            String[] values = null;
            for (String k : keys) {
                values = maps.get(k);
                if (values != null) {
                    // value 排序
                    Arrays.sort(values);
                    for (String v : values) {
                        s.append(k + v);
                    }
                } else {
                    s.append(k);
                }
            }

            return MD5Utils.encrypt(s.toString(), GlobalProperty.salt);
        }

    }

    public static class MD5Utils {

        private static final String ENCRYPT_TYPE = "MD5";

        private static final Logger logger = Logger.getAnonymousLogger();

        /**
         * 对特定字符串进行md5加密，可以添加盐值
         *
         * @param source 要加密的字符串
         * @param salt   添加的盐值
         * @return
         * @author taotao
         * @date 2013-9-13
         */
        public static String encrypt(String source, String salt) {
            //检查加密源是否存在
            if (source == null) {
                return null;
            }
            //添加盐值
            if (salt != null) {
                source += salt;
            }
            try {
                MessageDigest md = MessageDigest.getInstance(ENCRYPT_TYPE);
                md.reset();
                md.update(source.getBytes("UTF-8"));
                byte[] byteArray = md.digest();
                StringBuffer rs = new StringBuffer();
                for (int i = 0; i < byteArray.length; i++) {
                    if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                        rs.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                    } else {
                        rs.append(Integer.toHexString(0xFF & byteArray[i]));
                    }
                }
                return rs.toString();
            } catch (Exception e) {
                return source;
            }
        }

        /**
         * 对特定字符串进行md5加密
         *
         * @param source 要加密的字符串
         * @return 结果
         * @author taotao
         * @date 2013-9-13
         */
        public static String encrypt(String source) {
            return encrypt(source, null);
        }

        /**
         * 密码加密，模拟spring security 加密方式
         *
         * @param source
         * @param salt
         * @return
         * @author taotao
         * @date 2014-1-18
         */
        public static String securityPassword(String source, Object salt) {
            return encrypt(mergePasswordAndSalt(source, salt, false));
        }

        /**
         * 合并要加密的密码和盐值
         *
         * @param password
         * @param salt
         * @param strict
         * @return
         * @author taotao
         * @date 2014-1-18
         */
        private static String mergePasswordAndSalt(String password, Object salt, boolean strict) {
            if (password == null) {
                password = "";
            }
            if (strict && (salt != null)) {
                if ((salt.toString().lastIndexOf("{") != -1) || (salt.toString().lastIndexOf("}") != -1)) {
                    throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
                }
            }

            if ((salt == null) || "".equals(salt)) {
                return password;
            } else {
                return password + "{" + salt.toString() + "}";
            }
        }

    }


    public static class GlobalProperty {

        public static String _pkey = "Android";
        public static String salt = "qiming!@#";

        public final static int CITY = 205;// 是否需要选择地区
        public final static int SUCCEED = 200;// 登陆成功or发送成功
        public final static int INEXISTENCE = 201;// 用户不存在
        public final static int INCORRECTNESS = 202;// 密码不正确
        public final static int ILLEGAL = 203;// 参数违法
        public final static int ABNORMAL_SERVER = 500;// 服务器异常
        public final static int NETWORK_ANOMALY = 400;// 网络异常
        public final static int MOBILE_NOEXIST = 1;// 手机号不存在
        public final static int MOBILE_EXIST = -1;// 手机号已存在
        public final static int ACCOUNT_IS_EMPTY = -2;// 账号不能为空
        public final static int PASSWORD_IS_EMPTY = -3;// 密码不能为空
        public final static int WRONG_PHONE_NUMBER = -4;// 错误手机号码
        public final static int PHONE_VERIFICATION_CODE_ERROR = -5;// 手机验证码错误
        public final static int LENGTH_OF_VERIFICATION_CODE_ERROR = -6;// 验证码长度错误
        public final static int PHONE_VERIFICATION_CODE_IS_EMPTY = -7;// 手机验证码为空
        public final static int PASSWORD_LENGTH_ERROR = -8;// 密码长度错误
        public final static int ENTER_THE_PASSWORD_TWICE = -9;// 两次输入的密码不同
        public final static int NULL = -10;// 参数为空
        public final static int EMAIL_EXIST = -11;// 邮箱已存在
        public final static int ORIGINAL_INCORRECTNESS = -12;// 原密码输入错误
        public final static int LATEST_VERSION = -13;// 最新版本
        public final static int TEMPORARILY_VERSION = -14;// 允许暂时不升级
        public final static int MUST_VERSION = -15;// 必须升级
        public final static int OTHER_VERSION = -16;// 其他
        public final static int NONE_VERSION = -17;// 没有最新版本
        public final static int Book_INEXISTENCE = -18;// 电子书不存在

        // 题库fragment索引值
        public static final int QUESTIONBANK_FRAGMENT_INDEX = 0;
        // 视频fragment索引值
        public static final int VIDEO_FRAGMENT_INDEX = 1;
        // 直播fragment索引值
        public static final int LIVEBROADCAST_FRAGMENT_INDEX = 2;
        // 资讯fragment索引值
        public static final int INFORMATION_FRAGMENT_INDEX = 3;

        // 服务器地址
        // public static String serverIP = "http://192.168.1.102:7080/";
        // public static String serverIP = "http://192.168.1.101:6080/app-web/";
        // public static String serverIP = "http://192.168.1.101:9011/app-web/";

        // public static String serverIP = "http://192.168.1.106:66/";
        // public static String serverIP = "http://116.255.139.73:76/";
        public static String serverIP = "http://android.xueaedu.com:76/";// 线上地址
        // public static String serverIP = "http://app.qmtiku.com:76/";// 外网测试地址

        // 项目ID

        // 1 获取手机验证短信接口
        public static String phoneNumber = "customer/mobileCode.do";
        // 2 注册接口
        public static String register = "customer/register.do";
        // 修改密码接口
        public static String forgetPwd = "customer/changeCustomerPwdByMobile.do";
        // 修改密码手机验证短信接口
        public static String phoneNumberUpdate = "customer/UpdatePwdMobileCode.do";
        // 3 登陆接口
        public static String login = "customer/login.do";
        // 4 获取用户全部学科信息接口
        public static String allSubjectInformation = "common/getUserAllSubjects.do";
        // 5 获取用户某个学科信息接口
        public static String getSingleSubjectsInfo = "common/GetSingleSubjectsInfo.do";
        // 6 获取用户某个科目章节试题信息接口
        public static String getSubjectUnderChapterInfo = "common/GetSubjectUnderChapterInfo.do";
        // 7 获取科目下真题
        public static String getSubjectRealExamInfo = "exam/getSubjectRealExamInfo.do";
        // 8 付费题库信息接口
        public static String getSubjectPayExamInfo = "exam/getSubjectPayExamInfo.do";
        // 请求状态
        public static String realExam = "realExam";// 真题
        public static String payExam = "payExam";// 付费
        // 9请求试题接口
        public static String makePaper = "exam/makePaper.do";
        // 请求类型
        public final static int TYPE_SECTION = 1;// 章节练习
        public final static int TYPE_TOPIC = 2;// 历年真题
        public final static int TYPE_SCROLL = 3;// 智能组卷
        public final static int TYPE_PAY = 4;// 付费题库
        public final static int TYPE_ERROR = 5;// 我的错题
        public final static int TYPE_FLAG = 6;// 我的标记
        public final static int TYPE_CONTINUE = 7;// 继续练习
        public final static int TYPE_EXERCISE = 8;// 我的练习

        // 10 获取我的错题（章节、知识点）列表
        public static String errorExam = "exam/ErrorExam.do";
        // 11 获取我的标记（章节、知识点）列表
        public static String markExam = "exam/MarkExam.do";

        // 15 试题标记接口
        public static String markQuestions = "exam/markQuestions.do";
        // 16 试题纠错接口
        public static String correct = "exam/correct.do";
        // 17 提交试卷接口
        public static String submitPaper = "exam/submitPaper.do";
        // 18 获得试卷成绩接口
        public static String getExamResultData = "exam/getExamResultData.do";
        // 19 临时保存试卷接口
        public static String tempSavePaper = "exam/tempSavePaper.do";
        // 20 继续练习接口
        public static String continueDoExam = "exam/continueDoExam.do";
        // 21 试题分析接口
        public static String examQuestionAnalysis = "exam/examQuestionAnalysis.do";
        // 22 获得用户基本资料接口
        public static String getCustomerBaseInfo = "customer/getCustomerBaseInfo.do";
        // 23 绑定用户资料接口
        public static String bindCustomerInfo = "customer/bindCustomerInfo.do";
        // 24 错题和标记试题分析接口
        public static String errorAndMarkAnalysis = "exam/errorAndMarkAnalysis.do";
        // 25 修改密码
        public static String changeCustomerPsw = "customer/changeCustomerPsw.do";
        // 26 考试计划信息接口
        public static String examPlan = "common/examPlan.do";
        // 27 版本检测接口
        public static String checkVersion = "version/CheckVersion.do";
        public static String checkVersion2 = "version/CheckVersion2.do";

        // 29 电子书列表接口
        public static String bookList = "book/bookList.do";
        // 30 电子下载接口
        public static String downBook = "book/downBook.do";
        // 31 获得订单号接口
        public static String getProductOrder = "pay/getProductOrder.do";
        // 32 支付成功后接口
        public static String payOrderSucess = "pay/payOrderSucess.do";

        // 33 学习分析第一版接口
        public static String analysis = "exam/analysis.do";

        // 35 产品信息接口
        public static String getProductInfo = "pay/getProductInfo.do";

        // 38 移除错题接口
        public static String removeCustomerCorrect = "customer/removeCustomerCorrect.do";
        // 39 用户做题历史列表接口
        public static String myDoExamHistory = "customer/myDoExamHistory.do";
        // 40 获得用户试题类型
        public static String examQuestionType = "exam/ExamQuestionType.do";
        // 视频获取列表
        public static String courseList = "course/courseList.do";
        // 视频详细页面
        public static String courseInfo = "course/CourseInfo.do";
        // 获取所有城市
        public static String cityList = "common/getAllSubjectsCity.do";

        // 用户切换城市
        public static String cityUpdate = "customer/updatCustomerCity.do";


        //错误日志提交
        public static String log = "http://gwy.qmtiku.com/crashrpt.do";


    }

    public static class HttpPostRequest {
        public static String postRequest(Object sendUrl, List<NameValuePair> params,Context context) {

            HttpPost httpPost = new HttpPost(GlobalProperty.serverIP + sendUrl);
            try {
                HttpEntity httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                httpPost.setEntity(httpEntity);
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    String result = EntityUtils.toString(httpResponse.getEntity());
                    return result;
                } else {
                    return null;
                }

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
}
