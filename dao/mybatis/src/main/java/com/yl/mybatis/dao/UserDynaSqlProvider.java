package com.yl.mybatis.dao;

import com.yl.mybatis.entity.User;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by Cao Yuliang on 2020-04-21.
 */
public class UserDynaSqlProvider {

    public String insertUser(User user) {

        return new SQL() {
            {
                INSERT_INTO("user");
                if (user.getName() != null) {
                    VALUES("name", "#{name}");
                }
            }
        }.toString();
    }

    public String updateUser(User user) {

        return new SQL() {
            {
                UPDATE("user");
                if (user.getName() != null) {
                    SET("name = #{name}");
                }
                if (user.getCard() != null) {
                    SET("card_id = #{card.id}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
