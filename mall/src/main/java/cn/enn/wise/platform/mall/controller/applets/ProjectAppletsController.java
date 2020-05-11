package cn.enn.wise.platform.mall.controller.applets;

import cn.enn.wise.platform.mall.bean.param.GoodsProjectParam;
import cn.enn.wise.platform.mall.bean.vo.ProjectVo;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.ProjectAppletsService;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 小程序项目Api
 *
 * @author baijie
 * @date 2019-11-04
 */
@RestController
@RequestMapping("/project")
@Api("小程序项目相关Api")
@Slf4j
public class ProjectAppletsController extends BaseController {

    static String KEY_PREFIX = "project_info";

    @Resource
    private ProjectAppletsService projectAppletsService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @PostMapping("/info")
    @ApiOperation("小程序获取项目详情")
    public ResponseEntity<ProjectVo> getProjectInfo(@RequestBody GoodsProjectParam goodsProjectParam,@RequestHeader("companyId")Long companyId){

        String paramString = JSONObject.toJSONString(goodsProjectParam);
        String redisKey = KEY_PREFIX+":"+paramString;

        String cacheData = redisTemplate.opsForValue().get(redisKey);
        if(StringUtils.isEmpty(cacheData)){

        goodsProjectParam.setCompanyId(companyId);
        ProjectVo info = projectAppletsService.getProjectInfoById(goodsProjectParam);
        redisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(info),60*60*8, TimeUnit.SECONDS);
            return new ResponseEntity<>(info);
        }else {
            ProjectVo projectVo = JSONObject.parseObject(cacheData, ProjectVo.class);

            log.info("缓存数据===");

            return new ResponseEntity<>(projectVo);
        }


    }
}
