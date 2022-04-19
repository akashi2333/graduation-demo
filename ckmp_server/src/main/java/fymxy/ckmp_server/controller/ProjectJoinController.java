package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.ProjectJoin;
import fymxy.ckmp_server.service.ProjectJoinService;
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
@RequestMapping("/project-join")
@Api(tags = "project申请 进进进 的增删查")
public class ProjectJoinController {
    @Autowired
    ProjectJoinService projectJoinService;

    @ApiOperation(value = "申请加群操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "申请成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody ProjectJoin projectJoin){
        projectJoinService.save(projectJoin);
        return new Respone(200,"申请成功",null);
    }

    @ApiOperation(value = "删除对应申请加群操作的记录（即处理了此请求）")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @DeleteMapping("/delete")
    private Respone delete(@RequestBody ProjectJoin projectJoin){
        projectJoinService.remove(new QueryWrapper<ProjectJoin>().eq("uid",projectJoin.getUid()).eq("pid",projectJoin.getPid()));
        return new Respone(200,"处理成功",null);
    }

    @ApiOperation(value = "根据pid查询想进群人员id，返回int的list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"projectJoin.uid"})
    @GetMapping("/getByProjectId")
    private Respone getByProjectId( ProjectJoin projectJoin){
        ArrayList<Integer> res = new ArrayList<>();
        for (ProjectJoin exit : projectJoinService.list(new QueryWrapper<ProjectJoin>()
                .eq("pid", projectJoin.getPid()))) {
            res.add(exit.getUid());
        }
        return new Respone(200,"处理成功",res);
    }

    @ApiOperation(value = "根据uid查询想进的群，返回int的list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"projectJoin.pid"})
    @GetMapping("/getByUserId")
    private Respone getByUserId( ProjectJoin projectJoin){
        ArrayList<Integer> res = new ArrayList<>();
        for (ProjectJoin exit : projectJoinService.list(new QueryWrapper<ProjectJoin>()
                .eq("uid", projectJoin.getUid()))) {
            res.add(exit.getPid());
        }
        return new Respone(200,"处理成功",res);
    }
}
