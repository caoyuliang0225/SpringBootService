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
public class User implements Serializable {

    private static final long serialVersionUID = -4760205790673173171L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    private Card card;

    @OneToMany
    private List<Book> book;

    @OneToMany
    private List<Order> order;
}
