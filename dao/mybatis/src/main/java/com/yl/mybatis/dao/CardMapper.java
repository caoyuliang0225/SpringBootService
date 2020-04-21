package com.yl.mybatis.dao;

import com.yl.mybatis.entity.Card;
import org.apache.ibatis.annotations.*;

/**
 * Created by Cao Yuliang on 2020-04-21.
 */
@Mapper
public interface CardMapper {

    @Select("select * from card where id = #{id}")
    Card findById(@Param("id") Long id);

    @InsertProvider(type = CardDynaSqlProvider.class, method = "insertCard")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Card save(Card card);
}
