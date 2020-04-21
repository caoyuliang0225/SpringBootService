package com.yl.mybatis.controller;

import com.yl.mybatis.dao.CardMapper;
import com.yl.mybatis.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Cao Yuliang on 2020-04-21.
 */
@RestController
public class CardController {

    @Autowired
    private CardMapper cardMapper;

    @RequestMapping(value = "/card", method = RequestMethod.POST)
    public Card save(@RequestBody Card card) {
        Card card2 = this.cardMapper.save(card);
        return card2;
    }
}
