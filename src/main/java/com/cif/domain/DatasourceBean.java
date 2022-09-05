package com.cif.domain;

/**
 * Description: 数据源实体
 *
 * @author liutianshuo
 * @Date 2022/8/25
 * @Version 1.0
 **/
public class DatasourceBean {

    private int id;
    private String name;
    private String description;

    public DatasourceBean(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
