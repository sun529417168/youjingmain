package com.youjing.yjeducation.talkfun;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;

public class HttpRequest {
    public static final int SHOW_RESPONSE = 0;
    public static final int REQUEST_ERROR = 1;
    private static IHttpRequestListener mRequestListener;

    //方法：发送网络请求
    public void sendRequestWithGET(String url) {
        final String requestUrl = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(requestUrl);
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");//将entity当中的数据转换为字符串
                        //在子线程中将Message对象发出去
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        message.obj = response.toString();
                        handler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = REQUEST_ERROR;
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block

                    Message message = new Message();
                    message.what = REQUEST_ERROR;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void setRequestListener(IHttpRequestListener l) {
        mRequestListener = l;
    }

    //新建Handler的对象，在这里接收Message，然后更新TextView控件的内容
    private HttpRequestHandler handler = new HttpRequestHandler();

    public interface IHttpRequestListener {
        void onRequestCompleted(String responseStr);

        void onIOError(String errorStr);
    }

    private static class HttpRequestHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_RESPONSE:
                    //parseJson((String) msg.obj);
                    if (mRequestListener != null) {
                        mRequestListener.onRequestCompleted((String) msg.obj);
                    }
                    break;
                case REQUEST_ERROR:
                    if (mRequestListener != null) {
                        mRequestListener.onIOError("请求服务器失败");
                    }
                    break;
            }
        }
    }
}
