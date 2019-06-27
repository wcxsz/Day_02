package com.example.day_00_lainxi.utils;

import com.example.day_00_lainxi.app.BaseApp;
import com.example.day_00_lainxi.bean.DataDao;
import com.example.day_00_lainxi.dao.DaoMaster;
import com.example.day_00_lainxi.dao.DaoSession;
import com.example.day_00_lainxi.dao.DataDaoDao;

import java.util.List;

public class MyUtils {
    public static MyUtils myUtils;
    private final DataDaoDao dataDaoDao;

    private MyUtils(){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(BaseApp.getBaseApp(), "data.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        dataDaoDao = daoSession.getDataDaoDao();
    }

    public static MyUtils getMyUtils() {
        if (myUtils == null){
            synchronized (MyUtils.class){
                if (myUtils == null){
                    myUtils = new MyUtils();
                }
            }
        }
        return myUtils;
    }
    public boolean has(DataDao dataDao){
        List<DataDao> list = dataDaoDao.queryBuilder().where(DataDaoDao.Properties.Desc.eq(dataDao.getDesc())).list();
        if (list.size()>0){
            return true;
        }
        return false;
    }
    public void insert(DataDao dataDao){
        if (!has(dataDao)){
            dataDaoDao.insertOrReplace(dataDao);
        }
    }
    public void delete(DataDao dataDao){
        if (has(dataDao)){
            dataDaoDao.delete(dataDao);
        }
    }
    public List<DataDao> query(){
        return dataDaoDao.queryBuilder().list();
    }
}
