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
public class Book implements Serializable {

    private static final long serialVersionUID = 4776246014454577463L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Order> order;
}
