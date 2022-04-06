package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.Idea;
import fymxy.ckmp_server.service.IdeaService;
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
@RequestMapping("/idea")
@Api(tags = "想法的增删查")
public class IdeaController {
    @Autowired
    IdeaService ideaService;

    @ApiOperation(value = "新建idea操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "加入成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody Idea idea){
        ideaService.save(idea);
        return new Respone(200,"加入成功",null);
    }

    @ApiOperation(value = "删除idea操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "删除成功")
    })
    @DeleteMapping("/delete")
    private Respone delete(@RequestBody Idea idea){
        ideaService.remove(new QueryWrapper<Idea>().eq("id",idea.getId()).eq("idea",idea.getIdea()));
        return new Respone(200,"删除成功",null);
    }

    @ApiOperation(value = "查询idea操作，结果以list字符串形式")
    @ApiOperationSupport(ignoreParameters ="idea.idea")
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功")
    })
    @GetMapping("get")
    private Respone get(Idea idea){
        ArrayList<String> res = new ArrayList<>();
        for (Idea id : ideaService.list(new QueryWrapper<Idea>().eq("id", idea.getId()))) {
            res.add(id.getIdea());
        }
        return new Respone(200,"查询成功",res);
    }
}
