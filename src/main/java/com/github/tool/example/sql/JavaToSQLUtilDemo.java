package com.github.tool.example.sql;

import com.github.tool.core.sql.JavaToSQLUtil;

public class JavaToSQLUtilDemo {

    /**
     * test
     */
    public static void main(String[] args) throws Exception {
        // 将实体类转换创建表SQL
        JavaToSQLUtil.run("com.github.tool.example.base.entity", "test");
    }

}
