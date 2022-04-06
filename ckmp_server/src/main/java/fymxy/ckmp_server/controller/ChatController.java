package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.Chat;
import fymxy.ckmp_server.service.ChatService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fymxy
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/chat")
@Api(tags = "聊天记录插入和查询")
public class ChatController {
    @Autowired
    ChatService chatService;

    @ApiOperation(value = "插入聊天记录")
    @ApiOperationSupport(ignoreParameters = {"chat.sendDate"})
    @ApiResponses({
            @ApiResponse(code = 200,message = "发送成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody Chat chat){
        chat.setSendDate(new Date());
        chatService.save(chat);
        return new Respone(200,"发送成功",null);
    }

    @ApiOperation(value = "查询聊天记录，返回结果封装在data中")
    @ApiOperationSupport(ignoreParameters = {"chat.sendDate","chat.sid","chat.message"})
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功 返回聊天记录")
    })
    @GetMapping("/findMessages")
    private Respone findMessages(Chat chat){
        List<Chat> chatList = chatService.list(new QueryWrapper<Chat>()
                .eq("send_all", chat.getSendAll())
                .eq("rid", chat.getRid()));
        //聊天记录，需要返回
        chatList.forEach(System.out::println);
        chatList.sort((a,b)->a.getSendDate().before(b.getSendDate())?1:-1);
        return new Respone(200,"查询成功",chatList);
    }
}
