package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.ProjectResource;
import fymxy.ckmp_server.entity.Team;
import fymxy.ckmp_server.entity.TeamResource;
import fymxy.ckmp_server.entity.UserProject;
import fymxy.ckmp_server.service.TeamResourceService;
import fymxy.ckmp_server.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/team-resource")
@Api(tags = "团队资源的增查")
public class TeamResourceController {
    @Autowired
    TeamResourceService teamResourceService;
    @Autowired
    TeamService teamService;

    @Value("${file.basedir}")
    String basePath;


    @ApiOperation(value = "上传文件操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "上传成功")
    })
    @ApiOperationSupport(ignoreParameters = {"teamResource.resourceName","teamResource.timestamp"})
    @PostMapping("/add")
    public Respone uploadFile(@RequestBody MultipartFile file,@RequestParam int tid,@RequestParam int uid) throws IOException {
        if (!isMember(tid,uid)){
            return new Respone(200,"未加入团队",null);
        }
        String originalFilename = file.getOriginalFilename();
        TeamResource teamResource = new TeamResource();
        teamResource.setTid(tid);
        teamResource.setUid(uid);
        teamResource.setResourceName(originalFilename);
        teamResource.setTimestamp(new Date());

        teamResourceService.save(teamResource);
        file.transferTo(new File(basePath+originalFilename));
        return new Respone(200,"上传成功",null);

    }

    @ApiOperation(value = "下载文件操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "下载成功")
    })
    @ApiOperationSupport(ignoreParameters = {"timestamp"})
    @GetMapping("/download")
    public Respone downloadFile(TeamResource teamResource , HttpServletResponse response) throws IOException {
        TeamResource one = teamResourceService.getOne(new QueryWrapper<TeamResource>()
                .eq("uid", teamResource.getUid())
                .eq("tid", teamResource.getTid())
                .eq("resource_name", teamResource.getResourceName())
        );

        File file = new File(basePath +one.getResourceName());
        OutputStream outputStream=null;
        InputStream inputStream=null;
        BufferedInputStream bufferedInputStream=null;
        byte[] bytes=new byte[1024];
        // 获取输出流
        // TODO: 2022/4/18 此时返回的header的文件名存在中文为乱码的情况
        response.setHeader("Content-Disposition", "attachment;filename=" +  new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));

        //存在报错
//        response.setContentType("application/force-download");

        inputStream=new  FileInputStream(file);
        bufferedInputStream=new BufferedInputStream(inputStream);
        outputStream = response.getOutputStream();
        int i=bufferedInputStream.read(bytes);
        while (i!=-1){
            outputStream.write(bytes,0,i);
            i=bufferedInputStream.read(bytes);
        }
        inputStream.close();
        outputStream.close();
        bufferedInputStream.close();
        return new Respone(200,"下载成功",null);
    }

    @ApiOperation(value = "获取文件列表，根据tid")
    @ApiResponses({
            @ApiResponse(code = 200,message = "获取成功")
    })
    @ApiOperationSupport(ignoreParameters = {
            "resourceName",
            "uid",
            "timestamp"})
    @GetMapping("/getFileList")
    public Respone getFileList(TeamResource teamResource){
        List<TeamResource> list = teamResourceService.list(new QueryWrapper<TeamResource>().eq("tid", teamResource.getTid()));
        return new Respone(200,"获取成功",list);
    }

    @ApiOperation(value = "删除文件操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "删除成功")
    })
    @ApiOperationSupport(ignoreParameters = {"timestamp"})
    @DeleteMapping("/delete")
    public Respone downloadFile(TeamResource teamResource){
        File file = new File(basePath + teamResource.getResourceName());
        if (!file.exists()){
            return new Respone(200,"已删除",null);
        }
        teamResourceService.remove(new QueryWrapper<TeamResource>()
                .eq("tid",teamResource.getTid())
                .eq("uid",teamResource.getUid())
                .eq("resource_name",teamResource.getResourceName()));
        file.delete();
        return new Respone(200,"删除成功",null);
    }


    private Boolean isMember(int tid,int uid){
        ArrayList<Integer> joinedTeamId = new ArrayList<>();
        for (Team joinedTeam : teamService.list(new QueryWrapper<Team>()
                .eq("uid", uid))) {
            joinedTeamId.add(joinedTeam.getTid());
        }
        return joinedTeamId.contains(tid);
    }
}
