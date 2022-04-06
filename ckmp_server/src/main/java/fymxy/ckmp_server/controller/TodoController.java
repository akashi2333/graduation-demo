package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.Todo;
import fymxy.ckmp_server.service.TodoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fymxy
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/todo")
@Api(tags = "todo的增删查")
public class TodoController {
    @Autowired
    TodoService todoService;

    @ApiOperation(value = "新建todo操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "加入成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody Todo todo){
        todoService.save(todo);
        return new Respone(200,"加入成功",null);
    }

    @ApiOperation(value = "删除todo操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "删除成功")
    })
    @DeleteMapping("/delete")
    private Respone delete(@RequestBody Todo todo){
        todoService.remove(new QueryWrapper<Todo>().eq("id",todo.getId()).eq("todo",todo.getTodo()));
        return new Respone(200,"删除成功",null);
    }


    @ApiOperation(value = "查询todo操作,结果以list字符串形式")
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功")
    })
    @ApiOperationSupport(ignoreParameters = {"todo.todo"})
    @GetMapping("getById")
    private Respone getById(Todo todo){
        ArrayList<String> list = new ArrayList<>();
        for (Todo id : todoService.list(new QueryWrapper<Todo>().eq("id", todo.getId()))) {
            list.add(id.getTodo());
        }
        return new Respone(200,"查询成功",list);


    }

}
