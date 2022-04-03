package fymxy.ckmp_server.service.impl;

import fymxy.ckmp_server.entity.Chat;
import fymxy.ckmp_server.mapper.ChatMapper;
import fymxy.ckmp_server.service.ChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fymxy
 * @since 2022-03-26
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {

}
