package com.youjing.yjeducation.dao;

import android.content.Context;
import com.youjing.yjeducation.util.StringUtils;

import com.j256.ormlite.dao.Dao;
import com.youjing.yjeducation.component.DatabaseHelper;
import com.youjing.yjeducation.model.MessageEntity;
import com.youjing.yjeducation.util.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author: stt
 * @since: 2016.06.22
 * @version: 1.0
 * @feature: 推送消息DAO
 */
public class MessageDao {

    //数据库相关操作日志tag
    public static final String DB_LOG = "database";

    private Context context;
    private Dao<MessageEntity, Integer> MessageEntityOp;
    private DatabaseHelper helper;

    public MessageDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            MessageEntityOp = helper.getDao(MessageEntity.class);
        } catch (SQLException e) {
            StringUtils.Log(DB_LOG, e.toString());
        }
    }

    /**
     * 增加一条数据
     *
     * @param entity
     */
    public void add(MessageEntity entity) {
        try {
            MessageEntityOp.create(entity);
            StringUtils.Log(DB_LOG, "Insert : " + entity.toString());
        } catch (SQLException e) {
            StringUtils.Log(DB_LOG, e.toString());
        }

    }

    /**
     * 删除单条数据
     *
     * @param entity
     */
    public void del(MessageEntity entity) {
        try {
            MessageEntityOp.delete(entity);
            StringUtils.Log(DB_LOG, "Delete : " + entity.toString());
        } catch (SQLException e) {
            StringUtils.Log(DB_LOG, e.toString());
        }

    }

    public void delAll() {
        for (MessageEntity entity : this.getAll()) {
            this.del(entity);
        }
    }

    /**
     * 查询全部
     *
     * @return
     */
    public ArrayList<MessageEntity> getAll() {
        try {
            StringUtils.Log(DB_LOG, "Query : All");
            return (new ArrayList<MessageEntity>() {{
                this.addAll(MessageEntityOp.queryForAll());
            }});
        } catch (SQLException e) {
            StringUtils.Log(DB_LOG, e.toString());
        }
        return null;
    }
}
