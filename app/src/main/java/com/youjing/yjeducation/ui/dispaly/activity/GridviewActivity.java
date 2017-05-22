package com.youjing.yjeducation.ui.dispaly.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.youjing.yjeducation.util.StringUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;

import org.vwork.mobile.ui.utils.VLayoutTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/3/15
 * Time 20:34
 */
@VLayoutTag(R.layout.test_gridview)
public class GridviewActivity extends YJBaseActivity {

    private List list,list_high,list_mid,list_small;
    private GridView gird,gird_subject;
    private CheckBoxAdapter adapter ,adapterSubject;
    HashMap isSelectTy,isSelectTy2,isSelectTy3,isSelectTy4;
    private int mGrade,project;
    private String TAG = "GridviewActivity";

    @Override
    protected void onLoadedView() {
        super.onLoadedView();
        mGrade = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("grade_", 0);
       // project = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("project_", 0);

        StringUtils.Log(TAG,"mGrade="+mGrade+":::::::project="+project);
        initView();
    }
    private void initView() {
        setList();
        gird = (GridView) GridviewActivity.this.findViewById(R.id.activity_main_grid);
        adapter = new CheckBoxAdapter(GridviewActivity.this, list, isSelectTy, "高三");
        gird.setAdapter(adapter);

        /* gird_subject = (GridView) GridviewActivity.this.findViewById(R.id.activity_main_grid2);
        adapterSubject = new CheckBoxAdapter(GridviewActivity.this, list_high, isSelectTy2, "数学");
        gird_subject.setAdapter(adapterSubject);*/
    }
    /**
     * 初始化list，添加数据,初始化checkbox的值
     */
    private void setList(){
        list = new ArrayList();
        list_high = new ArrayList();
        list_mid = new ArrayList();
        list_small = new ArrayList();

        if(list.size() == 0){
            list.add("高    三");
            list.add("高    二");
            list.add("高    一");
            list.add("初    三");
            list.add("初    二");
            list.add("初    一");
            list.add("小学五");
            list.add("小学四");
            list.add("小学三");
        }
     /*   if(list_high.size() == 0){
            list_high.add("数学");
            list_high.add("语文");
            list_high.add("英语");
            list_high.add("物理");
            list_high.add("化学");
            list_high.add("生物");
        }
        if(list_mid.size() == 0){
            list_mid.add("数学");
            list_mid.add("语文");
            list_mid.add("英语");
            list_mid.add("物理");
        }
        if(list_small.size() == 0){
            list_small.add("数学");
            list_small.add("语文");
            list_small.add("英语");
        }*/
        isSelectTy = new HashMap();
        isSelectTy.put(0, false);
        isSelectTy.put(1, false);
        isSelectTy.put(2, false);
        isSelectTy.put(3, false);
        isSelectTy.put(4, false);
        isSelectTy.put(5, false);
        isSelectTy.put(6, false);
        isSelectTy.put(7, false);
        isSelectTy.put(8, false);
        isSelectTy.put(mGrade, true);


 /*       isSelectTy2 = new HashMap();
        isSelectTy2.put(0, false);
        isSelectTy2.put(1, false);
        isSelectTy2.put(2, false);
        isSelectTy2.put(3, false);
        isSelectTy2.put(4, false);
        isSelectTy2.put(5, false);
        isSelectTy2.put(project, true);

        isSelectTy3 = new HashMap();
        isSelectTy3.put(0, false);
        isSelectTy3.put(1, false);
        isSelectTy3.put(2, false);
        isSelectTy3.put(3, false);
        isSelectTy3.put(project, true);

        isSelectTy4 = new HashMap();
        isSelectTy4.put(0, false);
        isSelectTy4.put(1, false);
        isSelectTy4.put(2, false);
        isSelectTy4.put(project, true);*/
    }


    public static int temp = -1;
    public class CheckBoxAdapter extends BaseAdapter {
        Activity context;
        private List list;
        private String str;
        HashMap isSelect;
        public CheckBoxAdapter(Activity context, List list, HashMap isSelect, String str) {
            this.context = context;
            this.list = list;
            this.isSelect = isSelect;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Holder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gridview_item, null);
                holder = new Holder();
                holder.ib_item = (Button) convertView.findViewById(R.id.ib_item);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.ib_item.setPressed((boolean) isSelect.get(position));
            holder.ib_item.setText((String) list.get(position));
            /*if (list.get(position).equals(str)) {
                holder.tvContent.setChecked(true);
            }*/
            holder.ib_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isSelect.put(position, true);
             /*       if(position == 0 ||position == 1||position == 2){
                        if(view.isPressed()){
                            ButtonAdapter  adapter2 = new ButtonAdapter(GridviewActivity.this, list_high, isSelectTy2, "数学");
                            gird_subject.setAdapter(adapter2);
                        }
                    }
                    if(position == 3 ||position == 4||position == 5){
                        if(view.isPressed()) {
                            ButtonAdapter adapter2 = new ButtonAdapter(GridviewActivity.this, list_mid, isSelectTy3, "数学");
                            gird_subject.setAdapter(adapter2);
                        }
                    }
                    if(position == 6 ||position == 7||position ==8){
                        if(view.isPressed()) {
                            ButtonAdapter adapter2 = new ButtonAdapter(GridviewActivity.this, list_small, isSelectTy4, "数学");
                            gird_subject.setAdapter(adapter2);
                        }
                    }*/

                    if (view.isPressed()) {
                        for (int i = 0; i < list.size(); i++) {
                            //把其他的checkbox设置为false
                            if (i != position) {
                                isSelect.put(i, false);
                            } else {
                                saveGrade(position);
                            }
                        }
                    }
                    //通知适配器更改
                    CheckBoxAdapter.this.notifyDataSetChanged();
                }
            });
            if (temp == position) {
                holder.ib_item.setPressed(true);
            }
            return convertView;
        }

        class Holder {
            CheckBox tvContent;
            Button ib_item;
        }

        public void notifyDataSetChanged(String str) {
            this.str = str;
            this.notifyDataSetChanged();
        }

    }
    public static int temp2 = -1;
    public class ButtonAdapter extends BaseAdapter {
        Activity context;
        private List list;
        private String str;
        HashMap isSelect;
        public ButtonAdapter(Activity context, List list, HashMap isSelect, String str) {
            this.context = context;
            this.list = list;
            this.isSelect = isSelect;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Holder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gridview_item, null);
                holder = new Holder();
                holder.ib_item = (Button) convertView.findViewById(R.id.ib_item);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.ib_item.setPressed((boolean) isSelect.get(position));
            holder.ib_item.setText((String) list.get(position));
            /*if (list.get(position).equals(str)) {
                holder.tvContent.setChecked(true);
            }*/
            holder.ib_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isSelect.put(position, true);
                    if (view.isPressed()) {
                        for (int i = 0; i < list.size(); i++) {
                            //把其他的checkbox设置为false
                            if (i != position) {
                                isSelect.put(i, false);
                            } else {
                                 saveGrade(position);
                            }
                        }
                    }
                    //通知适配器更改
                    ButtonAdapter.this.notifyDataSetChanged();
                }
            });
            if (temp2 == position) {
                holder.ib_item.setPressed(true);
            }
            return convertView;
        }

        class Holder {
            Button ib_item;
        }

        public void notifyDataSetChanged(String str) {
            this.str = str;
            this.notifyDataSetChanged();
        }

    }

   /* public static int temp2 = -1;
    public class CheckBoxSubjectAdapter extends BaseAdapter {
        Activity context;
        private List list;
        private String str;
        private HashMap isSelect;
        public CheckBoxSubjectAdapter(Activity context, List list, HashMap isSelect, String str) {
            this.context = context;
            this.list = list;
            this.isSelect = isSelect;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Holder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gridview_item, null);
                holder = new Holder();
                holder.tvContent = (CheckBox) convertView.findViewById(R.id.all_class);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            //                        holder.tvContent.setId(position);
            holder.tvContent.setChecked((boolean)isSelect.get(position));
            holder.tvContent.setText((String) list.get(position));
             if (list.get(position).equals(str)) {
                holder.tvContent.setChecked(true);
            }
            holder.tvContent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isSelect.put(position, isChecked);
                    StringUtils.Log("22222222222", "position===" + position);
                    if (buttonView.isChecked()) {
                        for (int i = 0; i < list.size(); i++) {
                            //把其他的checkbox设置为false
                            if (i != position) {
                                isSelect.put(i, false);
                            }else {
                                saveProject(position);
                            }
                        }
                    }
                    //通知适配器更改
                    CheckBoxSubjectAdapter.this.notifyDataSetChanged();
                }
            });
            if (temp2 == position) {
                holder.tvContent.setChecked(true);
            }
            return convertView;
        }
        class Holder {
            CheckBox tvContent;
        }
        public void notifyDataSetChanged(String str) {
            this.str = str;
            this.notifyDataSetChanged();
        }
    }*/

    private void saveGrade(int num){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("grade_",num);
        editor.commit();
    }
    private void saveProject(int num){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("project_",num);
        editor.commit();
    }
}
