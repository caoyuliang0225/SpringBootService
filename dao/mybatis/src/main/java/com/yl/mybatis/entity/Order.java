package com.yl.mybatis.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Cao Yuliang on 2020-04-21.
 */
@Data
@Entity
public class Order implements Serializable {

    private static final long serialVersionUID = 5871950018312604030L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String no;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Book> book;
}
