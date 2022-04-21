package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.Project;
import fymxy.ckmp_server.entity.Team;
import fymxy.ckmp_server.entity.UserProject;
import fymxy.ckmp_server.service.ProjectService;
import fymxy.ckmp_server.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.RescaleOp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fymxy
 * @since 2022-04-04
 */
@RestController
@RequestMapping("/team")
@Api(tags = "team 的增改查删（其中timestamp为java的date形式）")
public class TeamController {
    @Autowired
    TeamService teamService;
    @Autowired
    ProjectService projectService;

    @ApiOperation(value = "新建team操作")
    @ApiOperationSupport(ignoreParameters = {"team.tid","team.timestamp","team.isowner"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "创建成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody MultipartFile file,Team team) {
        team.setTimestamp(new Date());
        int count = teamService.count();

        System.out.println(count);
        team.setTid(count+1);

        InputStream ins;
        try {
            ins = file.getInputStream();
            byte[] buffer=new byte[1024];
            int len=0;
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            while((len=ins.read(buffer))!=-1){
                bos.write(buffer,0,len);
            }
            bos.flush();
            team.setImg( bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }


        //可以没有，看前端
        team.setIsowner(1);
        teamService.save(team);
        return new Respone(200, "创建成功", team);
    }

    @ApiOperation(value = "修改team操作,前端需做核实拥有者身份")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功")
    })
    @PutMapping("/update")
    private Respone update(@RequestBody Team team) {
        if (teamService.updateById(team)) {
            return new Respone(200, "修改成功", null);
        } else {
            return new Respone(400, "修改失败", null);
        }
    }

    @ApiOperation(value = "根据uid查询拥有的team操作")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功")
    })
    @ApiOperationSupport(ignoreParameters = {
            "tid",
            "isowner",
            "name",
            "brief",
            "timestamp"})
    @GetMapping("/getMyTeamDetail")
    private Respone getMyTeamDetail( Team team) {
        List<Team> res = teamService.list(new QueryWrapper<Team>().eq("uid", team.getUid()).eq("isowner", 1));
        return new Respone(200, "查询成功", res);
    }

    @ApiOperation(value = "根据uid查询加入的team操作")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功")
    })
    @ApiOperationSupport(ignoreParameters = {
            "tid",
            "isowner",
            "name",
            "brief",
            "timestamp"})
    @GetMapping("/getJoinTeamDetail")
    private Respone getJoinTeamDetail(Team team) {
        List<Team> res = teamService.list(new QueryWrapper<Team>().eq("uid", team.getUid()));
        return new Respone(200, "查询成功", res);
    }

    @ApiOperation(value = "某人离开团队,团队拥有者不能离开")
    @ApiResponses({
            @ApiResponse(code = 200,message = "删除成功")
    })
    @ApiOperationSupport(ignoreParameters = {
            "team.isowner",
            "team.name",
            "team.brief",
            "team.timestamp"})
    @DeleteMapping("/delete")
    private Respone delete(@RequestBody Team team){
        // TODO: 2022/4/5 团队主人离开团队怎么办
        teamService.remove(new QueryWrapper<Team>().eq("uid",team.getUid()).eq("tid",team.getTid()));
        return new Respone(200,"删除成功",null);
    }

    @ApiOperation(value = "某人加入团队")
    @ApiResponses({
            @ApiResponse(code = 200,message = "加入成功")
    })
    @ApiOperationSupport(ignoreParameters = {
            "team.isowner",
            "team.name",
            "team.brief",
            "team.timestamp"})
    @PostMapping("/delete")
    private Respone addMember(@RequestBody Team team){
        if (teamService.getOne(new QueryWrapper<Team>().
                eq("tid",team.getTid()).
                eq("uid",team.getUid()))
                !=null){
            return new Respone(200,"已经加入",false);
        }
        Team joinTeam = teamService.getOne(new QueryWrapper<Team>().eq("tid",team.getTid()));
        joinTeam.setIsowner(0);
        joinTeam.setUid(team.getUid());
        teamService.save(joinTeam);
        return new Respone(200,"加入成功",true);
    }


    @ApiOperation(value = "根据tid查询团队详情")
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功")
    })
    @ApiOperationSupport(ignoreParameters = {
            "uid",
            "isowner",
            "name",
            "brief",
            "timestamp"})
    @GetMapping("/getByTid")
    private Respone getByTid(Team team){
        Team byId = teamService.getOne(new QueryWrapper<Team>().eq("tid",team.getTid()));
        return new Respone(200,"查询成功",byId);
    }


    @ApiOperation(value = "根据tid查询拥有的project（下属成员拥有的）")
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功")
    })
    @ApiOperationSupport(ignoreParameters = {
            "uid",
            "isowner",
            "name",
            "brief",
            "timestamp"})
    @GetMapping("/getProjectByTid")
    private Respone getProjectByTid(Team team){
        HashSet<Project> ans = new HashSet<>();
        for (Team tid : teamService.list(new QueryWrapper<Team>().eq("tid",team.getTid()))) {
            ans.addAll(projectService.list(new QueryWrapper<Project>(  ).eq("uid",tid.getUid())));
        }
        return new Respone(200,"查询成功",ans);
    }

    @ApiOperation(value = "得到所有team")
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功")
    })
    @ApiOperationSupport(ignoreParameters = {
            "uid",
            "isowner",
            "name",
            "brief",
            "timestamp"})
    @GetMapping("/getAllTeam")
    private Respone getAllTeam(){
        HashSet<Team> ans = new HashSet<>
                (teamService.list(new QueryWrapper<Team>()
                        .eq("isowner", "1")));
        return new Respone(200,"查询成功",ans);
    }
}
