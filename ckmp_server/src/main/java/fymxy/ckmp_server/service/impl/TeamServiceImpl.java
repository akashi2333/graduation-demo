package fymxy.ckmp_server.service.impl;

import fymxy.ckmp_server.entity.Team;
import fymxy.ckmp_server.mapper.TeamMapper;
import fymxy.ckmp_server.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fymxy
 * @since 2022-04-04
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

}
