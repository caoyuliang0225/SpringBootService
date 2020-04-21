package com.yl.mybatis.dao;

import com.yl.mybatis.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

/**
 * Created by Cao Yuliang on 2020-04-21.
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    @Results({
        @Result(id=true,column="id",property="id"),
        @Result(column="name",property="name"),
        @Result(column="card_id",property="card",
            one=@One(
                select="com.yl.mybatis.dao.CardMapper.findById",
                fetchType= FetchType.EAGER))
    })
    User findById(@Param("id") Long id);

    @InsertProvider(type = UserDynaSqlProvider.class, method = "insertUser")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id")
    Long save(User user);

    @SelectProvider(type = UserDynaSqlProvider.class, method = "updateUser")
    void update(User user);
}
