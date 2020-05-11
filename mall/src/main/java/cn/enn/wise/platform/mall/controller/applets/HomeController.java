package cn.enn.wise.platform.mall.controller.applets;

import cn.enn.wise.platform.mall.bean.param.GoodsProjectParam;
import cn.enn.wise.platform.mall.bean.vo.GoodsProjectVo;
import cn.enn.wise.platform.mall.bean.vo.TagAppletsVo;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.TagCategoryService;
import cn.enn.wise.platform.mall.service.WzdGoodsAppletsService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.WX.WxPayUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 小程序首页接口
 *
 * @author baijie
 * @date 2019-10-29
 */
@RestController
@RequestMapping("/home")
@Api("小程序首页相关接口")
public class HomeController extends BaseController {


    @Autowired
    private TagCategoryService tagCategoryService;

    @Autowired
    private WzdGoodsAppletsService wzdGoodsAppletsService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/category")
    @ApiOperation("首页获取分类接口")
    public ResponseEntity<List<TagAppletsVo>> getCategoryList(){

        String redisKey = "category_list";
        String cacheData = redisTemplate.opsForValue().get(redisKey);
        if(StringUtils.isEmpty(cacheData)){
            List<TagAppletsVo> tagVoList = tagCategoryService.getTagVoList();
            redisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(tagVoList));

            return new ResponseEntity<>(tagVoList);

        }else {
            String cacheDataString = redisTemplate.opsForValue().get(redisKey);
            List<TagAppletsVo> list = JSONObject.parseObject(cacheDataString, List.class);
            return new ResponseEntity<>(list);
        }

    }

    @PostMapping("/tag/goods")
    @ApiOperation("根据tagId获取商品和项目")
    public ResponseEntity<List<GoodsProjectVo>> getGoodsOrProjectList(@RequestBody GoodsProjectParam goodsProjectParam ,@RequestHeader("companyId")Long companyId){

        String paramString = JSONObject.toJSONString(goodsProjectParam);
        String redisKey = "tag_goods_list:"+paramString;

        String redisCacheData = redisTemplate.opsForValue().get(redisKey);

        if(StringUtils.isEmpty(redisCacheData)){

            goodsProjectParam.setCompanyId(companyId);
            List<GoodsProjectVo> projectVoList = wzdGoodsAppletsService.listByTag(goodsProjectParam);
            redisTemplate.opsForValue().set(redisKey,JSONObject.toJSONString(projectVoList),60*60*8, TimeUnit.SECONDS);

            return new ResponseEntity<>(projectVoList);
        }else {

            List<GoodsProjectVo> list = JSONObject.parseObject(redisCacheData, List.class);
            return new ResponseEntity<>(list);

        }

    }

    @PostMapping("/tag/project")
    @ApiOperation("根据tagId获取项目列表")
    public ResponseEntity<List<GoodsProjectVo>> getProjectListByTag(@RequestBody GoodsProjectParam goodsProjectParam ,@RequestHeader("companyId")Long companyId){
        goodsProjectParam.setCompanyId(companyId);

        List<GoodsProjectVo> projectVoList = wzdGoodsAppletsService.projectListByTag(goodsProjectParam);

        return new ResponseEntity<>(projectVoList);
    }


    @GetMapping("/category/get")
    @ApiOperation("获取分类Id")
    public ResponseEntity<String> getCategoryId(){

        String categoryId = redisTemplate.opsForValue().get("categoryId");
        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS,categoryId);
    }

    @GetMapping("/category/update")
    @ApiOperation("更新分类Id")
    public ResponseEntity<String> updateCategoryId(String id){

        redisTemplate.opsForValue().set("categoryId",id);

        return new ResponseEntity<>(GeneConstant.SUCCESS,id);
    }


    @GetMapping("/login")
    @ApiOperation("登录")
    public ResponseEntity login(String id) throws Exception{

        Map<String,String> map= WxPayUtils
                .refund("20200117132325767902789r",
                        "20200117132325767902789tk",
                        "30000",
                        "30000");

        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"退款",map);
    }
}
