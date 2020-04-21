package com.yl.mybatis.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Cao Yuliang on 2020-04-21.
 */
@Data
@Entity
public class Card implements Serializable {

    private static final long serialVersionUID = 8810863786911203216L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long no;

    @OneToOne
    private User user;
}
