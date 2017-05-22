package com.youjing.yjeducation.ui.dispaly.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.model.YJLivelUpMedel;
import com.youjing.yjeducation.model.YJMedalGetMedel;
import com.youjing.yjeducation.ui.actualize.dialog.YJShareDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJUpgradeLevelDialog;
import com.youjing.yjeducation.ui.dispaly.dialog.AYJCourseExpiredDialog;

import org.vwork.utils.base.VParams;

/**
 * @author stt
 *         类说明：获得勋章弹框
 *         创建时间：2016.6.27
 */
public class AYJPushActivity extends YJBaseActivity {
    private YJLivelUpMedel yjLivelUpMedel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        yjLivelUpMedel = (YJLivelUpMedel) this.getIntent().getSerializableExtra("livelUpMedel");
        YJUpgradeLevelDialog yjUpgradeLevelDialog = new YJUpgradeLevelDialog();
        VParams data = createTransmitData(YJUpgradeLevelDialog.TXT_INFO, yjLivelUpMedel);
        showDialog(yjUpgradeLevelDialog, data);
    }
}
