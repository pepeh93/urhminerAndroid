package com.urh.model;

public class Pool {
    private String name;
    public String getName() {
        return name;
    }
    public String getLastCheckedAt() {
        return last_checked_at;
    }
    public Integer getStatus() {
        return status;
    }
    private String last_checked_at;
    private Integer status;

}
