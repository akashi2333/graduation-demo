package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.ProjectExit;
import fymxy.ckmp_server.entity.TeamExit;
import fymxy.ckmp_server.entity.User;
import fymxy.ckmp_server.service.TeamExitService;
import fymxy.ckmp_server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fymxy
 * @since 2022-04-06
 */
@RestController
@RequestMapping("/team-exit")
@Api(tags = "team申请 退退退 的增删查")
public class TeamExitController {

    @Autowired
    TeamExitService teamExitService;
    @Autowired
    UserService userService;

    @ApiOperation(value = "申请退 团队 操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "申请成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody TeamExit teamExit){
        if (teamExitService.list(new QueryWrapper<TeamExit>()
                .eq("uid",teamExit.getUid())
                .eq("tid",teamExit.getTid()))!=null){
            return new Respone(200,"已申请",null);
        }
        teamExitService.save(teamExit);
        return new Respone(200,"申请成功",null);
    }

    @ApiOperation(value = "删除对应申请退 团队 操作的记录（即处理了此请求）")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @DeleteMapping("/delete")
    private Respone delete( TeamExit teamExit){
        teamExitService.remove(new QueryWrapper<TeamExit>().eq("uid",teamExit.getUid()).eq("tid",teamExit.getTid()));
        return new Respone(200,"处理成功",null);
    }

    @ApiOperation(value = "根据tid查询 退团队 人员id，返回user的list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"uid"})
    @GetMapping("/getByTeamId")
    private Respone getByTeamId(TeamExit teamExit){
        ArrayList<User> res = new ArrayList<>();
        for (TeamExit exit : teamExitService.list(new QueryWrapper<TeamExit>()
                .eq("tid", teamExit.getTid()))) {
            res.add(userService.getById(exit.getUid()));
        }
        return new Respone(200,"处理成功",res);
    }

    @ApiOperation(value = "根据uid查询退的团队，返回int的list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"tid"})
    @GetMapping("/getByUserId")
    private Respone getByUserId(TeamExit teamExit){
        ArrayList<Integer> res = new ArrayList<>();
        for (TeamExit exit : teamExitService.list(new QueryWrapper<TeamExit>()
                .eq("uid", teamExit.getUid()))) {
            res.add(exit.getTid());
        }
        return new Respone(200,"处理成功",res);
    }

}
