package com.youjing.yjeducation.talkfun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.talkfun.sdk.module.NoticeEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 良好的代码风格能个人带来一个美好的下午
 * Created by tony on 2015/9/16.
 */
public class NoticeFragment extends Fragment implements DispatchNotice {
    @Bind(R.id.notice_tv)
    TextView noticeTv;
    @Bind(R.id.date)
    TextView timeTv;
    //    private String tag;
    private String content;
    private String time;

    public NoticeFragment() {

    }

//    public static NoticeFragment getInstance(String tag) {
//        NoticeFragment nf = new NoticeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("notice", tag);
//        nf.setArguments(bundle);
//        return nf;
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments().getString("notice") != null) {
//            tag = getArguments().getString("notice");
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.notice_fragment_layout, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (content != null && noticeTv != null)
            noticeTv.setText("公告 ：" + content);
        if (time != null && timeTv != null)
            timeTv.setText(time);
    }

    private final String prefix = "<p>";
    private final String suffix = "</p>";

    @Override
    public void getNotice(NoticeEntity noticeEntity) {
        if (noticeEntity != null) {
            content = noticeEntity.getContent();
            if (TextUtils.isEmpty(content) || !content.startsWith(prefix) || !content.endsWith(suffix))
                return;
            time = noticeEntity.getTime();
            content = content.substring(content.indexOf(prefix) + prefix.length(), content.lastIndexOf(suffix));
            try {
                content = URLDecoder.decode(content, "utf-8");
                if (noticeTv != null)
                    noticeTv.setText("公告 ：" + content);
                if (timeTv != null)
                    timeTv.setText(time);
            } catch (UnsupportedEncodingException e) {
            }
        }
    }
}
