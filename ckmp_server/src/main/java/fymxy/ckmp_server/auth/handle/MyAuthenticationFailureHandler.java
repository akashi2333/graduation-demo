package fymxy.ckmp_server.auth.handle;


import fymxy.ckmp_server.auth.utils.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO 其实处理登录成功和退出有两种方式，一种写给handler处理，另外是重写方法
 * 登录失败处理方式
 * @Author: crush
 * @Date: 2021-09-09 9:22
 * version 1.0
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseUtils.result(response,200,"登录失败！！");
    }
}
