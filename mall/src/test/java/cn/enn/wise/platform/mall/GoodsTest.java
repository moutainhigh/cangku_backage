package cn.enn.wise.platform.mall;


import cn.enn.wise.platform.mall.service.GoodsExtendService;
import cn.enn.wise.platform.mall.service.GoodsProjectPeriodService;
import cn.enn.wise.platform.mall.service.GoodsProjectService;
import cn.enn.wise.platform.mall.service.GoodsService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Profile({"integrated", "test", "local"})
public class GoodsTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsProjectService projectService;

    @Autowired
    private GoodsProjectPeriodService periodService;

    @Autowired
    private GoodsExtendService extendService;

    @Autowired
    private Gson gson;

    private String token = null;

    @Before
    public void before() throws Exception {
        token = CommonTestUtil.getLoginToken(mockMvc);
    }

    /**
     * 保存商品信息：本单元测试正常保存数据
     *
     * @throws Exception
     */
//    @Test
//    public void save() throws Exception {
//        GoodsReqParam goodsReqParam = new GoodsReqParam();
//        // 从数据库获取一个项目ID
//        List<GoodsProject> goodsProjects = projectService.list();
//        Assert.assertFalse(goodsProjects.isEmpty());
//        long projectId = goodsProjects.get(0).getId();
//        // 商品数据封装
//        goodsReqParam.setProjectId(projectId);
//        goodsReqParam.setResourceId(1L);
//        String goodsName = UUID.randomUUID().toString();
//        goodsReqParam.setGoodsName(goodsName);
//        goodsReqParam.setIsPackage(0);
//        Random random = new Random();
//        int bound = random.nextInt(500);
//        goodsReqParam.setBasePrice(new BigDecimal(bound + 500));
//        goodsReqParam.setCategoryId(1L);
//        goodsReqParam.setMaxNum(random.nextInt(12));
//        goodsReqParam.setSalesUnit("张");
//        int servicePlaceVal = random.nextInt(3);
//        String servicePlace = servicePlaceVal == 1 ? "1" : (servicePlaceVal == 2 ? "2" : "1,2");
//        goodsReqParam.setServicePlace(servicePlace);
//        goodsReqParam.setRules("test_rules");
//        // 调用接口
//        String result = this.mockMvc.perform(post("/good/save")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(JSONObject.toJSONString(goodsReqParam))
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        // 返回结果解析
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//        // 验证结果与期望值
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.SUCCESS_CODE);
//    }
//
//    /**
//     * 保存商品信息：本单元测试因商品名称在非删除商品中已存在而保存失败
//     *
//     * @throws Exception
//     */
//    @Test
//    public void saveFailure() throws Exception {
//        Goods goods = this.getOneGoods(GoodsConstants.GoodsStatusEnum.ON_SHELF.value());
//        if (goods == null) {
//            return;
//        }
//        GoodsReqParam goodsReqParam = new GoodsReqParam();
//        // 从数据库获取一个项目ID
//        List<GoodsProject> goodsProjects = projectService.list();
//        Assert.assertFalse(goodsProjects.isEmpty());
//        long projectId = goodsProjects.get(0).getId();
//        // 商品数据封装
//        goodsReqParam.setProjectId(projectId);
//        goodsReqParam.setResourceId(1L);
//        goodsReqParam.setGoodsName(goods.getGoodsName());
//        goodsReqParam.setIsPackage(0);
//        Random random = new Random();
//        int bound = random.nextInt(500);
//        goodsReqParam.setBasePrice(new BigDecimal(bound + 500));
//        goodsReqParam.setCategoryId(1L);
//        goodsReqParam.setMaxNum(random.nextInt(12));
//        goodsReqParam.setSalesUnit("张");
//        int servicePlaceVal = random.nextInt(3);
//        String servicePlace = servicePlaceVal == 1 ? "1" : (servicePlaceVal == 2 ? "2" : "1,2");
//        goodsReqParam.setServicePlace(servicePlace);
//        // 调用接口
//        String result = this.mockMvc.perform(post("/good/save")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(JSONObject.toJSONString(goodsReqParam))
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        // 返回结果解析
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//        // 验证结果与期望值
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.BUSINESS_ERROR);
//    }
//
//    /**
//     * 根据条件查询商品列表
//     *
//     * @throws Exception
//     */
//    @Test
//    public void testList() throws Exception {
//        ReqPageInfoQry<GoodsReqParam> goodsPageQry = new ReqPageInfoQry<>();
//        goodsPageQry.setPageNum(1L);
//        goodsPageQry.setPageSize(10L);
//        GoodsReqParam goodsReqParam = new GoodsReqParam();
//        goodsReqParam.setGoodsStatus((byte) 1);
//        goodsPageQry.setReqObj(goodsReqParam);
//        String result = this.mockMvc.perform(post("/good/list")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(JSONObject.toJSONString(goodsPageQry))
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        ResponseEntity<ResPageInfoVO<List<GoodsResVO>>> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<ResPageInfoVO<List<GoodsResVO>>>>() {
//                }.getType());
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.SUCCESS_CODE);
//    }
//
//    /**
//     * 从库中查询一个商品
//     *
//     * @return
//     */
//    private Goods getOneGoods(Byte status) {
//        List<Goods> goods = this.getGoodsList(status, 1);
//        if (goods == null) {
//            return null;
//        }
//        return goods.get(0);
//    }
//
//    /**
//     * 从库中查询若干商品
//     *
//     * @return
//     */
//    private List<Goods> getGoodsList(Byte status, int count) {
//        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        if (status != null) {
//            queryWrapper.lambda().eq(Goods::getGoodsStatus, status);
//        }
//        // 分页参数
//        Page<Goods> pageParam = new Page<>(1, count);
//        // 根据条件查询数据库
//        IPage<Goods> dbPageInfo = goodsService.page(pageParam, queryWrapper);
//        if (dbPageInfo == null || dbPageInfo.getRecords() == null || dbPageInfo.getRecords().isEmpty()) {
//            return null;
//        }
//        return dbPageInfo.getRecords();
//    }
//
//    /**
//     * 根据商品ID查询商品详情
//     *
//     * @throws Exception
//     */
//    @Test
//    public void testGetGoodById() throws Exception {
//        Goods goods = this.getOneGoods(GoodsConstants.GoodsStatusEnum.ON_SHELF.value());
//        if (goods == null) {
//            return;
//        }
//        String result = this.mockMvc.perform(get("/good/get/" + goods.getId())
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        ResponseEntity<GoodsResVO> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<GoodsResVO>>() {
//                }.getType());
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.SUCCESS_CODE);
//    }
//
//    /**
//     * 测试商品名称是否存在：该单元返回true
//     *
//     * @throws Exception
//     */
//    @Test
//    public void isGoodsNameExistTrue() throws Exception {
//        Goods goods = this.getOneGoods(GoodsConstants.GoodsStatusEnum.ON_SHELF.value());
//        if (goods == null) {
//            return;
//        }
//        String result = this.mockMvc.perform(get("/good/exist/" + goods.getGoodsName())
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.SUCCESS_CODE);
//        Assert.assertTrue(responseEntity.getValue());
//    }
//
//    /**
//     * 测试商品名称是否存在：该单元返回false
//     *
//     * @throws Exception
//     */
//    @Test
//    public void isGoodsNameExistFalse() throws Exception {
//        Random random = new Random();
//        int nameRandom = random.nextInt(1000);
//        String result = this.mockMvc.perform(get("/good/exist/unit_test_random_" + nameRandom)
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.SUCCESS_CODE);
//        Assert.assertFalse(responseEntity.getValue());
//    }
//
//    /**
//     * 批量上下架商品：本单元测试批量下架商品
//     *
//     * @throws Exception
//     */
//    @Test
//    public void batchSwitchGoodsStatusOff() throws Exception {
//        // 查询已上架的商品列表
//        List<Goods> goodsList = this.getGoodsList(GoodsConstants.GoodsStatusEnum.ON_SHELF.value(), 10);
//        if (goodsList == null || goodsList.isEmpty()) {
//            return;
//        }
//        // 取前几条数据下架
//        if (goodsList.size() > 1) {
//            goodsList = goodsList.subList(0, 2);
//        } else if (goodsList.size() > 0) {
//            goodsList = goodsList.subList(0, 1);
//        }
//        JSONArray jsonArray = new JSONArray();
//        goodsList.stream().forEach(x -> jsonArray.add(x.getId()));
//        // 调用接口
//        String result = this.mockMvc.perform(post("/good/batch/update/"
//                + GoodsConstants.GoodsStatusEnum.OFF_SHELF.value())
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(jsonArray.toJSONString())
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.SUCCESS_CODE);
//    }
//
//    /**
//     * 批量上下架商品：本单元测试批量上架商品
//     *
//     * @throws Exception
//     */
//    @Test
//    public void batchSwitchGoodsStatusOn() throws Exception {
//        // 查询已下架的商品列表
//        List<Goods> goodsList = this.getGoodsList(GoodsConstants.GoodsStatusEnum.OFF_SHELF.value(), 10);
//        if (goodsList == null || goodsList.isEmpty()) {
//            return;
//        }
//        // 取前几条数据上架
//        if (goodsList.size() > 1) {
//            goodsList = goodsList.subList(0, 2);
//        } else if (goodsList.size() > 0) {
//            goodsList = goodsList.subList(0, 1);
//        }
//        JSONArray jsonArray = new JSONArray();
//        goodsList.stream().forEach(x -> jsonArray.add(x.getId()));
//        // 调用接口
//        String result = this.mockMvc.perform(post("/good/batch/update/"
//                + GoodsConstants.GoodsStatusEnum.ON_SHELF.value())
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(jsonArray.toJSONString())
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.SUCCESS_CODE);
//    }
//
//    /**
//     * 批量删除商品（逻辑删除），本单元测试正常删除
//     *
//     * @throws Exception
//     */
//    @Test
//    public void batchDeleteGoods() throws Exception {
//        // 查询已下架的商品列表
//        List<Goods> goodsList = this.getGoodsList(GoodsConstants.GoodsStatusEnum.OFF_SHELF.value(), 10);
//        if (goodsList == null || goodsList.isEmpty()) {
//            return;
//        }
//        // 取前几条数据删除
//        if (goodsList.size() > 1) {
//            goodsList = goodsList.subList(0, 2);
//        } else if (goodsList.size() > 0) {
//            goodsList = goodsList.subList(0, 1);
//        }
//        JSONArray jsonArray = new JSONArray();
//        goodsList.stream().forEach(x -> jsonArray.add(x.getId()));
//        // 调用接口
//        String result = this.mockMvc.perform(post("/good/batch/delete")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(jsonArray.toJSONString())
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.SUCCESS_CODE);
//    }
//
//    /**
//     * 批量删除商品（逻辑删除），本单元测试：已上架商品不能删除
//     *
//     * @throws Exception
//     */
//    @Test
//    public void batchDeleteGoodsFailure() throws Exception {
//        // 查询已上架的商品列表
//        List<Goods> goodsList = this.getGoodsList(GoodsConstants.GoodsStatusEnum.ON_SHELF.value(), 10);
//        if (goodsList == null || goodsList.isEmpty()) {
//            return;
//        }
//        // 取前几条数据删除
//        if (goodsList.size() > 1) {
//            goodsList = goodsList.subList(0, 2);
//        } else if (goodsList.size() > 0) {
//            goodsList = goodsList.subList(0, 1);
//        }
//        JSONArray jsonArray = new JSONArray();
//        goodsList.stream().forEach(x -> jsonArray.add(x.getId()));
//        // 调用接口
//        String result = this.mockMvc.perform(post("/good/batch/delete")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(jsonArray.toJSONString())
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        // 返回结果解析
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//        // 验证结果与期望值
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.BUSINESS_ERROR);
//    }
//
//    /**
//     * 根据商品ID查询商品的运营时段信息
//     *
//     * @throws Exception
//     */
//    @Test
//    public void getGoodsExtendOperationInfo() throws Exception {
//        Goods goods = this.getOneGoods(GoodsConstants.GoodsStatusEnum.ON_SHELF.value());
//        if (goods == null) {
//            return;
//        }
//        // 调用接口
//        String result = this.mockMvc.perform(get("/good/extend/get/" + goods.getId())
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        // 返回结果解析
//        ResponseEntity<List<GoodsExtendOperationResVo>> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<List<GoodsExtendOperationResVo>>>() {
//                }.getType());
//        // 验证结果与期望值
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.SUCCESS_CODE);
//    }
//
//    /**
//     * 更新商品信息，本单元测试正常情况
//     *
//     * @throws Exception
//     */
//    @Test
//    public void updateSuccess() throws Exception {
//        Goods goods = this.getOneGoods(GoodsConstants.GoodsStatusEnum.OFF_SHELF.value());
//        if (goods == null) {
//            return;
//        }
//        GoodsReqParam goodsReqParam = new GoodsReqParam();
//        goodsReqParam.setGoodsId(goods.getId());
//        Random random = new Random();
//        int bound = random.nextInt(500);
//        goodsReqParam.setBasePrice(new BigDecimal(bound + 500));
//        // 调用接口
//        String result = this.mockMvc.perform(post("/good/update")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(JSONObject.toJSONString(goodsReqParam))
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        // 返回结果解析
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//        // 验证结果与期望值
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.SUCCESS_CODE);
//    }
//
//    /**
//     * 更新商品信息，本单元测试异常情况：商品只有下架状态才可以编辑
//     *
//     * @throws Exception
//     */
//    @Test
//    public void updateFailure() throws Exception {
//        Goods goods = this.getOneGoods(GoodsConstants.GoodsStatusEnum.ON_SHELF.value());
//        if (goods == null) {
//            return;
//        }
//        GoodsReqParam goodsReqParam = new GoodsReqParam();
//        goodsReqParam.setGoodsId(goods.getId());
//        Random random = new Random();
//        int bound = random.nextInt(500);
//        goodsReqParam.setBasePrice(new BigDecimal(bound + 500));
//        // 调用接口
//        String result = this.mockMvc.perform(post("/good/update")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(JSONObject.toJSONString(goodsReqParam))
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        // 返回结果解析
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//        // 验证结果与期望值
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.BUSINESS_ERROR);
//    }
//
//    /**
//     * 更新商品信息，本单元测试异常情况：商品名称不能重复
//     *
//     * @throws Exception
//     */
//    @Test
//    public void updateFailureSecond() throws Exception {
//        // 待更新的商品
//        Goods goodsUpdate = this.getOneGoods(GoodsConstants.GoodsStatusEnum.OFF_SHELF.value());
//        // 存在的已上架商品
//        Goods goodsExistEffective = this.getOneGoods(GoodsConstants.GoodsStatusEnum.ON_SHELF.value());
//        if (goodsExistEffective == null) {
//            // 如果上架的商品不存在，则查一条下架的商品
//            goodsExistEffective = this.getOneGoods(GoodsConstants.GoodsStatusEnum.OFF_SHELF.value());
//            // 如果还不存在，则直接跳出
//            if (goodsExistEffective == null) {
//                return;
//            }
//        }
//        GoodsReqParam goodsReqParam = new GoodsReqParam();
//        goodsReqParam.setGoodsId(goodsUpdate.getId());
//        goodsReqParam.setGoodsName(goodsExistEffective.getGoodsName());
//        // 调用接口
//        String result = this.mockMvc.perform(post("/good/update")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(JSONObject.toJSONString(goodsReqParam))
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        // 返回结果解析
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//
//        // 验证结果与期望值
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.BUSINESS_ERROR);
//    }
//
//    /**
//     * 设置时段运营：正常设置
//     *
//     * @throws Exception
//     */
//    @Test
//    public void extendOperation() throws Exception {
//        // 查询下架的商品
//        Goods goods = this.getOneGoods(GoodsConstants.GoodsStatusEnum.OFF_SHELF.value());
//        // 查询其运营时段信息
//        List<GoodsExtend> goodsExtendList = extendService.getGoodsExtendByGoodsId(goods.getId());
//        List<GoodsExtendReqParam> goodsExtendReqParamList = new LinkedList<>();
//        // 去掉一段运营时段信息
//        if (goodsExtendList != null && goodsExtendList.size() > 1) {
//            goodsExtendList.remove(0);
//        }
//        List<GoodsExtendReqParam> extendReqParamList = goodsExtendList.stream().map(x -> {
//            GoodsExtendReqParam param = new GoodsExtendReqParam();
//            BeanUtils.copyProperties(x, param);
//            return param;
//        }).collect(Collectors.toList());
//        goodsExtendReqParamList.addAll(extendReqParamList);
//        // 从数据库获取一个项目ID
//        List<GoodsProject> goodsProjects = projectService.list();
//        Assert.assertFalse(goodsProjects.isEmpty());
//        long projectId = goodsProjects.get(0).getId();
//        // 查询运营时段信息
//        List<ProjectPeriodResVo> periods = periodService.listByProjectId(projectId);
//        Assert.assertFalse(periods.isEmpty());
//        // 添加一段运营时段信息
//        Random random = new Random();
//        ProjectPeriodResVo periodResVo = periods.get(0);
//        GoodsExtendReqParam[] goodsExtendReqParamArr = new GoodsExtendReqParam[]{
//                new GoodsExtendReqParam(null, goods.getId(), periodResVo.getTitle(), (byte) 1,
//                        new BigDecimal(random.nextInt(500) + 500), periodResVo.getId()),
//        };
//        goodsExtendReqParamList.addAll(Arrays.asList(goodsExtendReqParamArr));
//        // 封装请求参数
//        GoodsExtendReqParam goodsExtendReqParam = new GoodsExtendReqParam();
//        goodsExtendReqParam.setGoodsId(goods.getId());
//        goodsExtendReqParam.setGoodsExtendReqParamList(goodsExtendReqParamList);
//        // 调用接口
//        String result = this.mockMvc.perform(post("/good/extend/update")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(JSONObject.toJSONString(goodsExtendReqParam))
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        // 返回结果解析
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//        // 验证结果与期望值
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.SUCCESS_CODE);
//    }
//
//    /**
//     * 设置时段运营：异常设置-非下架商品不能编辑
//     *
//     * @throws Exception
//     */
//    @Test
//    public void extendOperationFailure() throws Exception {
//        // 查询上架的商品
//        Goods goods = this.getOneGoods(GoodsConstants.GoodsStatusEnum.ON_SHELF.value());
//        // 封装请求参数
//        GoodsExtendReqParam goodsExtendReqParam = new GoodsExtendReqParam();
//        goodsExtendReqParam.setGoodsId(goods.getId());
//        // 调用接口
//        String result = this.mockMvc.perform(post("/good/extend/update")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(JSONObject.toJSONString(goodsExtendReqParam))
//                .header("token", token))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        // 返回结果解析
//        ResponseEntity<Boolean> responseEntity = gson.fromJson(result,
//                new TypeToken<ResponseEntity<Boolean>>() {
//                }.getType());
//        // 验证结果与期望值
//        Assert.assertEquals(responseEntity.getResult(), GeneConstant.BUSINESS_ERROR);
//    }
//
//    @Test
//    public void getGoodsByProject() throws Exception {
//        String contentAsString = this.mockMvc.perform(get("/good/getbyproject/1"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        JSONObject jsonObject = JSONObject.parseObject(contentAsString);
//        Integer result = jsonObject.getInteger("result");
//        if(result!=1){
//            throw new RuntimeException("根据项目获取商品接口异常");
//        }
//        System.out.println(jsonObject.getJSONArray("value"));
//    }
//
//    @Test
//    public void getGoodsPeriodByOperationDateAndGoodsId() throws Exception {
//        String contentAsString = this.mockMvc.perform(get("/good/periodidlist")
//        .param("date","2019-07-07")
//        .param("goodsId","1"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        JSONObject jsonObject = JSONObject.parseObject(contentAsString);
//        Integer result = jsonObject.getInteger("result");
//        if(result!=1){
//            throw new RuntimeException("根据项目获取商品接口异常");
//        }
//        System.out.println(jsonObject.getJSONArray("value"));
//    }
}