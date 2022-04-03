package fymxy.ckmp_server.service.impl;

import fymxy.ckmp_server.entity.Todo;
import fymxy.ckmp_server.mapper.TodoMapper;
import fymxy.ckmp_server.service.TodoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TodoServiceImpl extends ServiceImpl<TodoMapper, Todo> implements TodoService {

}
