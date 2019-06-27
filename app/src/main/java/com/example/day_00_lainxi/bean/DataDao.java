package com.example.day_00_lainxi.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class DataDao {
    @Id(autoincrement = true)
    private Long id;
    private String url;
    private String desc;
    @Generated(hash = 254623368)
    public DataDao(Long id, String url, String desc) {
        this.id = id;
        this.url = url;
        this.desc = desc;
    }
    @Generated(hash = 2050766327)
    public DataDao() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
