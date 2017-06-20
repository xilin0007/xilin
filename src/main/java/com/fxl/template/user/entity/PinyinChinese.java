package com.fxl.template.user.entity;

import com.fxl.frame.base.BaseEntity;

public class PinyinChinese extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

    private String pinyin;

    private String pyHeader;

    private String chinese;

    private Integer wordNum;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public String getPyHeader() {
        return pyHeader;
    }

    public void setPyHeader(String pyHeader) {
        this.pyHeader = pyHeader == null ? null : pyHeader.trim();
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese == null ? null : chinese.trim();
    }

    public Integer getWordNum() {
        return wordNum;
    }

    public void setWordNum(Integer wordNum) {
        this.wordNum = wordNum;
    }
}