package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.ChannelListParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ChannelParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ChannelDTO;
import cn.enn.wise.ssop.service.promotions.consts.ChannelEnum;
import cn.enn.wise.ssop.service.promotions.mapper.ChannelMapper;
import cn.enn.wise.ssop.service.promotions.mapper.ChannelRuleMapper;
import cn.enn.wise.ssop.service.promotions.model.Channel;
import cn.enn.wise.ssop.service.promotions.model.ChannelRule;
import cn.enn.wise.ssop.service.promotions.util.GeneConstant;
import cn.enn.wise.ssop.service.promotions.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.param.QueryPage;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.CHANNEL_NAME_ISEXIST;
import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.CHANNEL_NULL_ISEXIST;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;


/**
 * @author jiabaiye
 * 渠道管理
 */
@Service("channelService")
public class ChannelService extends ServiceImpl<ChannelMapper, Channel> {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ChannelMapper channelMapper;

    @Autowired
    ChannelRuleMapper channelRuleMapper;

//    @Autowired
//    GoodsExtendFacade goodsExtendFacade;

    public QueryData<ChannelDTO> getChannelList(ChannelListParam channelParam){


        LambdaQueryWrapper<Channel> wrapper = new LambdaQueryWrapper<>();


        //渠道名称
        if(!StringUtils.isEmpty(channelParam.getChannelName())){
            wrapper.like(Channel::getChannelName, channelParam.getChannelName());
        }
        //商品名称
        if(!StringUtils.isEmpty(channelParam.getGoodsName())){
            //获取商品id列表
//            R<List<GoodsExtendAllDTO>> goodsExtendList =goodsExtendFacade.getGoodsExtendList(null,null,null,channelParam.getGoodsName());
//            List<GoodsExtendAllDTO> goodsExtendAllDTOList = goodsExtendList.getData();
//            List list = new ArrayList();
//            for(GoodsExtendAllDTO goodsExtendAllDTO:goodsExtendAllDTOList){
//                list.add(goodsExtendAllDTO.getId());
//            }
//            channelRuleMapper.selectList(new QueryWrapper<ChannelRule>().in("", list));
        }
        //状态
        if(channelParam.getState()!=GeneConstant.BYTE_ALL){
            wrapper.eq(Channel::getState,channelParam.getState());
        }
        //操作人
        if(!StringUtils.isEmpty(channelParam.getUpdateUserName())){
            wrapper.like(Channel::getUpdatorName,channelParam.getUpdateUserName());
        }
        //渠道类型
        if(channelParam.getChannelType()!=GeneConstant.BYTE_ALL){
            wrapper.eq(Channel::getChannelType,channelParam.getChannelType());
        }
        //是否有可用政策
        if(channelParam.getIshaveRule()!=GeneConstant.BYTE_ALL){
            wrapper.eq(Channel::getIshaveRule,channelParam.getIshaveRule());
        }
        wrapper.orderByDesc(Channel::getCreateTime);
        IPage<Channel> page = this.page(new QueryPage<>(channelParam), wrapper);
        return new QueryData<ChannelDTO>(page, this::toSimpleLicenceDTO);

    }


    public List<ChannelDTO> getNameAndIdList(Byte channelType) {
        List<Channel> list = channelMapper.selectList(new QueryWrapper<Channel>().eq("state","1").eq("isdelete","1").eq("channel_type",channelType));
        List<ChannelDTO> channelDTOS = new ArrayList<>();
        list.forEach(c->{
            ChannelDTO channelDTO = new ChannelDTO();
            BeanUtils.copyProperties(c,channelDTO,getNullPropertyNames(c));
            channelDTOS.add(channelDTO);
        });

        return channelDTOS;
    }

    public R addChannel(ChannelParam channelParam) {
        //判断渠道名是否重复
        Channel channel = channelMapper.selectOne(new QueryWrapper<Channel>().eq("channel_name",channelParam.getChannelName()));
        if(channel!=null){
            CHANNEL_NAME_ISEXIST.assertFail();
        }
        channel=new Channel();
        BeanUtils.copyProperties(channelParam,channel,getNullPropertyNames(channelParam));
        channelMapper.insert(channel);
        channel = channelMapper.selectOne(new QueryWrapper<Channel>().eq("channel_name",channel.getChannelName()));
        if(channel.getChannelType().equals(GeneConstant.BYTE_1)){
            String code = "ZY"+String.format("%05d",channel.getId());
            channel.setCode(code);
        }else{
            String code = "FX"+String.format("%05d",channel.getId());
            channel.setCode(code);
        }
        channelMapper.updateById(channel);
        ChannelDTO channelDTO = new ChannelDTO();
        BeanUtils.copyProperties(channel,channelDTO,getNullPropertyNames(channel));
        return new R<>(channelDTO);
    }

    public R updateChannel(ChannelParam channelParam) {
        //判断渠道名是否重复
        Channel channel = channelMapper.selectOne(new QueryWrapper<Channel>().eq("channel_name",channelParam.getChannelName()));
        if(channel!=null&&!channel.getId().equals(channelParam.getId())){
            CHANNEL_NAME_ISEXIST.assertFail();
        }
        List<ChannelRule> rules = channelRuleMapper.selectList(new QueryWrapper<ChannelRule>().eq("isdelete",1).eq("channel_id",channel.getId()));
        if(rules==null||rules.size()<1){
            channelParam.setState(GeneConstant.BYTE_2);
        }
        channel=new Channel();
        BeanUtils.copyProperties(channelParam,channel,getNullPropertyNames(channelParam));
        channelMapper.updateById(channel);
        channel = channelMapper.selectOne(new QueryWrapper<Channel>().eq("id",channel.getId()));
        ChannelDTO channelDTO = new ChannelDTO();
        BeanUtils.copyProperties(channel,channelDTO,getNullPropertyNames(channel));
//        return new R<>(toFormat(channelDTO));
        return new R<>(channelDTO);
    }

    public R channelDetail(Long id) {
        //判断渠道是否存在
        Channel channel = channelMapper.selectOne(new QueryWrapper<Channel>().eq("id",id));
        if(channel==null){
            CHANNEL_NULL_ISEXIST.assertFail(id);
        }
        ChannelDTO channelDTO = new ChannelDTO();
        BeanUtils.copyProperties(channel,channelDTO,getNullPropertyNames(channel));
        return new R<>(channelDTO);
    }

    public boolean editChannelState(Long id,Byte state){
        Channel channel = this.getById(id);
        if(channel==null){
            CHANNEL_NULL_ISEXIST.assertFail(id);
        }
        channel.setState(state);
        return this.updateById(channel);
    }

    private ChannelDTO toSimpleLicenceDTO(Channel channel) {
        ChannelDTO dto = new ChannelDTO();
        BeanUtils.copyProperties(channel,dto,getNullPropertyNames(channel));
        return dto;
    }

    public ChannelDTO toFormat(ChannelDTO channelDTO){
        channelDTO.setStateLabel(ChannelEnum.getName("stateLabel",channelDTO.getState().toString()));
        channelDTO.setChannelTypeLabel(ChannelEnum.getName("channelTypeLabel",channelDTO.getState().toString()));
        if (channelDTO.getTag()!=null&& !"".equals(channelDTO.getTag())) {
            channelDTO.setTag(ChannelEnum.getName("tag",channelDTO.getTag()));
        }
        if(channelDTO.getRegisterResource()!=null&& !"".equals(channelDTO.getRegisterResource())){
            channelDTO.setRegisterResource(ChannelEnum.getName("registerResource",channelDTO.getRegisterResource()));
        }
        if(channelDTO.getAppRegister()!=null&& !"".equals(channelDTO.getAppRegister())){
            channelDTO.setAppRegister(ChannelEnum.getName("appRegister",channelDTO.getAppRegister()));
        }
        if(channelDTO.getOperation()!=null&& !"".equals(channelDTO.getOperation())){
            channelDTO.setOperation(ChannelEnum.getName("operation",channelDTO.getOperation()));
        }
        if(channelDTO.getDocking()!=null&& !"".equals(channelDTO.getDocking())){
            channelDTO.setDocking(ChannelEnum.getName("docking",channelDTO.getDocking()));
        }
        if(channelDTO.getSettlement()!=null&& !"".equals(channelDTO.getSettlement())){
            channelDTO.setSettlement(ChannelEnum.getName("settlement",channelDTO.getSettlement()));
        }
        return channelDTO;
    }
}
