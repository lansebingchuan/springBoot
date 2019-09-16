package com.zht;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zht
 * @create 2019-09-10 14:53
 */

public class DateDate {//每一条sql都是这个一个类

    public String methName;

    public String sql;


    public String getMethName() {
        return methName;
    }

    public void setMethName(String methName) {
        this.methName = methName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
    /**@param methName id
     * @param  sql  sql语句
     */
    public DateDate(String methName, String sql) {
        this.methName = methName;
        this.sql = sql;
    }
}
