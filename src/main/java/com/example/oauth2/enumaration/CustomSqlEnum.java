package com.example.oauth2.enumaration;

import lombok.Getter;

@Getter
public enum CustomSqlEnum {
    SAMPLE_SYSTEM_SQL("select * from user where username = ?");

    String sql;

    CustomSqlEnum(String sql) {
        this.sql = sql;
    }
}
