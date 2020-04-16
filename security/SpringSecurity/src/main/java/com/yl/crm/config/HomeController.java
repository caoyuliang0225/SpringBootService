package com.yl.crm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
public class HomeController {

    @RequestMapping(value = "/api/hello", method = RequestMethod.GET)
    public Map<String, String> hello(
            @Value("${spring.application.name}") String appName
    ) {
        return Collections.singletonMap("name", appName);
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public Map<String, String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }

    @RequestMapping(value = "/principal", method = RequestMethod.GET)
    public Principal principal(Principal principal) {
        return principal;
    }
}
