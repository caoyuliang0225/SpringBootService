package com.yl.demo.controller;

import com.yl.demo.handler.fanout.PullQueue1;
import com.yl.demo.handler.fanout.PullQueue2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Cao Yuliang on 2020-03-30.
 */
@RestController
public class TestController {

    @Autowired
    private PullQueue1 pullQueue1;
    @Autowired
    private PullQueue2 pullQueue2;

    @RequestMapping(value = "/pull/queue1", method = RequestMethod.GET)
    public String pullQueue1() throws Exception {
        this.pullQueue1.pullMessage();
        return "OK";
    }

    @RequestMapping(value = "/pull/queue2", method = RequestMethod.GET)
    public String pullQueue2() throws Exception {
        this.pullQueue2.pullMessage();
        return "OK";
    }
}
