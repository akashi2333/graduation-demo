package fymxy.ckmp_server.service.impl;

import fymxy.ckmp_server.entity.User;
import fymxy.ckmp_server.mapper.UserMapper;
import fymxy.ckmp_server.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fymxy
 * @since 2022-03-26
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
