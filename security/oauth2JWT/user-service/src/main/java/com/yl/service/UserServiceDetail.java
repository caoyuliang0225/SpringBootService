package com.yl.service;


import com.yl.client.AuthServiceClient;
import com.yl.dao.UserDao;
import com.yl.dto.UserLoginDTO;
import com.yl.entity.JWT;
import com.yl.entity.User;
import com.yl.exception.UserLoginException;
import com.yl.utils.BPwdEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Created by Cao Yuliang on 2020/4/15.
 */
@Service
public class UserServiceDetail implements UserDetailsService {

    @Autowired
    private UserDao userRepository;
    @Autowired
    AuthServiceClient client;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User insertUser(String username,String  password){
        User user=new User();
        user.setUsername(username);
        user.setPassword(BPwdEncoderUtil.BCryptPassword(password));
        return userRepository.save(user);
    }

    public UserLoginDTO login(String username, String password){
        User user=userRepository.findByUsername(username);
        if (null == user) {
          throw new UserLoginException("error username");
        }
        if(!BPwdEncoderUtil.matches(password,user.getPassword())){
            throw new UserLoginException("error password");
        }
        // 获取token: clientId:password jwt-user-service:123456 的 Base64编码
        JWT jwt = client.getToken("Basic and0LXVzZXItc2VydmljZToxMjM0NTY=", "password", username, password);
        // 获得用户菜单
        if (jwt == null ) {
            throw new UserLoginException("error internal");
        }
        UserLoginDTO userLoginDTO=new UserLoginDTO();
        userLoginDTO.setJwt(jwt);
        userLoginDTO.setUser(user);

        return userLoginDTO;
    }
}
