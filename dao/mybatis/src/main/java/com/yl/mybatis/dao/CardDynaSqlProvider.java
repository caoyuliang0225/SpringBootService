package com.yl.mybatis.dao;

import com.yl.mybatis.entity.Card;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by Cao Yuliang on 2020-04-21.
 */
public class CardDynaSqlProvider {

    public String insertCard(Card card) {

        return new SQL() {
            {
                INSERT_INTO("card");
                if (card.getNo() != null) {
                    VALUES("no", "#{no}");
                }
            }
        }.toString();
    }
}
