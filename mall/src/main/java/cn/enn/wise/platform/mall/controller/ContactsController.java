package cn.enn.wise.platform.mall.controller;

import cn.enn.wise.platform.mall.bean.param.ContactsParam;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.constants.ContactsTicketType;
import cn.enn.wise.platform.mall.service.ContactsService;
import cn.enn.wise.platform.mall.service.OrderService;
import cn.enn.wise.platform.mall.util.AccountValidatorUtil;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.OpenIdAuthRequired;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import cn.hutool.core.util.IdcardUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zsj
 * @date 2019/12/24  13:50
 */
@Api(value = "联系人管理", tags = { "联系人管理" })
@RestController
@RequestMapping("/contacts")
public class ContactsController extends BaseController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ContactsController.class);
    /**
     * 成人年龄
     */
    public static final int ADULTAGE = 14;
    /**
     * 儿童年龄
     */
    public static final int UNDERAGE = 6;
    /**
     * 手机号码正则
     */
    //public static final String REGEX_MOBILE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/saveContacts")
    @OpenIdAuthRequired
    @ApiOperation(value = "保存联系人")
    public ResponseEntity saveContacts (@Value("#{request.getAttribute('currentUser')}") User user, @RequestBody ContactsParam contactsParam)  {
        LOGGER.info("/contacts/saveContacts   保存联系人");
        contactsParam.setMemberId(user.getId());
        validateContactsParam(contactsParam);
        return contactsService.saveContacts(contactsParam);
    }

    @PostMapping("/updateContacts")
    @OpenIdAuthRequired
    @ApiOperation(value = "修改联系人")
    public ResponseEntity updateContacts (@Value("#{request.getAttribute('currentUser')}") User user, @RequestBody ContactsParam contactsParam)  {
        LOGGER.info("/contacts/updateContacts   修改联系人");
        contactsParam.setMemberId(user.getId());
        validateContactsParam(contactsParam);
        if(contactsParam.getId() == null){
            LOGGER.info("=========id不能为空===========");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"id不能为空");
        }
        return contactsService.updateContacts(contactsParam);
    }

    @PostMapping("/findAllContacts")
    @OpenIdAuthRequired
    @ApiOperation(value = "查询所有联系人")
    public ResponseEntity findAllContacts(@Value("#{request.getAttribute('currentUser')}") User user){
        Long memberId = user.getId();
        LOGGER.info("/findAllContacts   查询所有联系人" + memberId);
        return contactsService.findAllContacts(memberId);
    }

    @PostMapping("/findContactsByIds")
    @OpenIdAuthRequired
    @ApiOperation(value = "根据ids查询所有联系人")
    public ResponseEntity findContactsByIds(@Value("#{request.getAttribute('currentUser')}") User user, String ids){
        Long memberId = user.getId();
        //Long memberId = 7L;
        if(StringUtils.isBlank(ids)){
            LOGGER.info("=========请求参数ids不能为空===========");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"请求参数ids不能为空");
        }
        LOGGER.info("/findContactsByIds   根据ids查询所有联系人" + ids + "========" + memberId);
        return contactsService.findContactsByIds(memberId, ids);
    }

    public void validateContactsParam(ContactsParam contactsParam){
        LOGGER.info("=========开始联系人参数验证===========");
        if(contactsParam == null){
            LOGGER.info("=========请求参数不能为空===========");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"请求参数不能为空");
        }
        if(contactsParam.getMemberId() == null){
            LOGGER.info("=========会员id不能为空===========");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"会员id不能为空");
        }
        if(contactsParam.getName() == null){
            LOGGER.info("=========联系人姓名不能为空===========");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"联系人姓名不能为空");
        }
        if(contactsParam.getTicketType() == null){
            LOGGER.info("=========票型不能为空===========");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"票型不能为空");
        }
        if(StringUtils.isNotBlank(contactsParam.getPhoneNum())){
            //手机号是否合法
            if(!contactsParam.getPhoneNum().matches(AccountValidatorUtil.REGEX_MOBILE)){
                LOGGER.info("=========手机号码不合法===========");
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"手机号码不合法");
            }
        }
        if(contactsParam.getIdCard() == null){
            LOGGER.info("=========身份证号码不能为空===========");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"身份证号码不能为空");
        }
        //验证身份证是否合法
        if(!IdcardUtil.isValidCard(contactsParam.getIdCard())){
            LOGGER.info("=========身份证号码不合法===========");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"身份证号码不合法");
        }
        //身份信息和票型不匹配(成人票不需要限制)
        if(contactsParam.getTicketType().equals(ContactsTicketType.ADULT.value())){

        }else if(contactsParam.getTicketType().equals(ContactsTicketType.CHILDREN.value())){
            //请根据年龄区间购票   儿童票  超过14岁不能购买
            if(IdcardUtil.getAgeByIdCard(contactsParam.getIdCard()) > ADULTAGE){
                LOGGER.info("=========请根据年龄区间购票===========");
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"请根据年龄区间购票");
            }
        }else if(contactsParam.getTicketType().equals(ContactsTicketType.CARRY.value())){
            //请根据年龄区间购票    携童票超过6岁不能购买
            if(IdcardUtil.getAgeByIdCard(contactsParam.getIdCard()) > UNDERAGE){
                LOGGER.info("=========请根据年龄区间购票===========");
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"请根据年龄区间购票");
            }
        }else{
            //票型不合法
            LOGGER.info("=========票型不合法===========");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"请输入合法的票型");
        }
    }

    /**
     * 船票状态更新
     */
    @PostMapping("/shipTicketSynchro")
    public void shipTicketSynchro(){
        LOGGER.info("==========开始船票同步===========");
        orderService.shipTicketSync();
        LOGGER.info("==========船票同步完成===========");
    }

}
