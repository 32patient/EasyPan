package com.easypan.entity.enums;


public enum FileDelFlagEnums {
    DEL(0, "删除"),
    RECYCLE (1, "回收站"),
    USING(2, "使用中");;

    private Integer flag;
    private String desc;

    FileDelFlagEnums(Integer flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

    public Integer getFlag() {
        return flag;
    }

    public String getDesc() {
        return desc;
    }
}
