package com.youjing.yjeducation.talkfun;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.lang.ref.WeakReference;

/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/2/2.
 */
public class BaseDialogFragment extends DialogFragment {
    public WeakReference<Context> mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = new WeakReference<Context>(getActivity());
    }
}
