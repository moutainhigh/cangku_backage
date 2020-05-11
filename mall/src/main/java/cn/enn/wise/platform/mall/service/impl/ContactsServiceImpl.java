package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.autotable.Contacts;
import cn.enn.wise.platform.mall.bean.param.ContactsParam;
import cn.enn.wise.platform.mall.bean.vo.ContactsVo;
import cn.enn.wise.platform.mall.constants.ContactsTicketType;
import cn.enn.wise.platform.mall.mapper.ContactsMapper;
import cn.enn.wise.platform.mall.service.ContactsService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 联系人服务实现
 * @author zsj
 * @date 2019/12/24  14:49
 */
@Service
public class ContactsServiceImpl implements ContactsService {

    /**
     * 成人年龄
     */
    public static final int ADULTAGE = 14;
    /**
     * 儿童年龄
     */
    public static final int UNDERAGE = 6;

    @Autowired
    private ContactsMapper contactsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity saveContacts(ContactsParam contactsParam) {
        checkIdCard(contactsParam.getMemberId(), contactsParam.getIdCard());
        Contacts contacts = new Contacts();
        BeanUtils.copyProperties(contactsParam, contacts);
        contacts.setCreateTime(new Timestamp(System.currentTimeMillis()));
        contacts.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        contactsMapper.insert(contacts);
        return new ResponseEntity();
    }

    public void checkIdCard(Long memberId, String idCard) throws BusinessException {
        List<Contacts> resultList = contactsMapper.selectList(new QueryWrapper<Contacts>().eq("member_id", memberId).eq("id_card", idCard));
        if(CollectionUtils.isNotEmpty(resultList)){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"您的身份证号码已存在！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateContacts(ContactsParam contactsParam) {
        List<Contacts> resultList = contactsMapper.selectList(new QueryWrapper<Contacts>().eq("member_id", contactsParam.getMemberId()).eq("id_card", contactsParam.getIdCard()));
        if(CollectionUtils.isEmpty(resultList) || (resultList.size() == 1 && resultList.get(0).getId().equals(contactsParam.getId()))){
            Contacts contacts = new Contacts();
            BeanUtils.copyProperties(contactsParam, contacts);
            contacts.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            contactsMapper.updateById(contacts);
            return new ResponseEntity();
        }else{
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"您的身份证号码已存在！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<List<ContactsVo>> findAllContacts(Long memberId) {
        List<ContactsVo> list = new ArrayList<>();
        List<Contacts> resultList = contactsMapper.selectList(new QueryWrapper<Contacts>().eq("member_id", memberId));
        if(CollectionUtils.isNotEmpty(resultList)){
            resultList.stream().forEach(x -> {
               ContactsVo contactsVo = new ContactsVo();
               //如果年经增长了   需要更新票型
                if(x.getTicketType().equals(ContactsTicketType.ADULT.value())){
                    //不操作
                }else if(x.getTicketType().equals(ContactsTicketType.CHILDREN.value())){
                    //需验证年龄  14-18岁属于儿童票
                    if(IdcardUtil.getAgeByIdCard(x.getIdCard()) > ADULTAGE){
                        //改为成人票
                        x.setTicketType(ContactsTicketType.ADULT.value());
                        Contacts contacts = new Contacts();
                        contacts.setId(x.getId());
                        contacts.setTicketType(ContactsTicketType.ADULT.value());
                        contactsMapper.updateById(contacts);
                    }
                }else if(x.getTicketType().equals(ContactsTicketType.CARRY.value())){
                    //需验证年龄   携童票不能超过14岁
                    if(IdcardUtil.getAgeByIdCard(x.getIdCard()) > UNDERAGE){
                        //改为儿童票
                        x.setTicketType(ContactsTicketType.CHILDREN.value());
                        Contacts contacts = new Contacts();
                        contacts.setId(x.getId());
                        contacts.setTicketType(ContactsTicketType.CHILDREN.value());
                        contactsMapper.updateById(contacts);
                    }
                }

               BeanUtils.copyProperties(x,contactsVo);
               list.add(contactsVo);
           });
        }
        return ResponseEntity.ok(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<List<ContactsVo>> findContactsByIds(Long memberId, String ids) {
        List<ContactsVo> list = new ArrayList<>();
        String[] findId = ids.split(",");
        List<Contacts> resultList = contactsMapper.selectList(new QueryWrapper<Contacts>().eq("member_id", memberId).in("id", findId));
        if(CollectionUtils.isNotEmpty(resultList)){
            resultList.stream().forEach(x -> {
                ContactsVo contactsVo = new ContactsVo();
                //如果年经增长了   需要更新票型
                if(x.getTicketType().equals(ContactsTicketType.ADULT.value())){
                    //不操作
                }else if(x.getTicketType().equals(ContactsTicketType.CHILDREN.value())){
                    //需验证年龄  14-18岁属于儿童票
                    if(IdcardUtil.getAgeByIdCard(x.getIdCard()) > ADULTAGE){
                        //改为成人票
                        x.setTicketType(ContactsTicketType.ADULT.value());
                        Contacts contacts = new Contacts();
                        contacts.setId(x.getId());
                        contacts.setTicketType(ContactsTicketType.ADULT.value());
                        contactsMapper.updateById(contacts);
                    }
                }else if(x.getTicketType().equals(ContactsTicketType.CARRY.value())){
                    //需验证年龄   携童票不能超过14岁
                    if(IdcardUtil.getAgeByIdCard(x.getIdCard()) > UNDERAGE){
                        //改为儿童票
                        x.setTicketType(ContactsTicketType.CHILDREN.value());
                        Contacts contacts = new Contacts();
                        contacts.setId(x.getId());
                        contacts.setTicketType(ContactsTicketType.CHILDREN.value());
                        contactsMapper.updateById(contacts);
                    }
                }
                BeanUtils.copyProperties(x,contactsVo);
                list.add(contactsVo);
            });
        }
        return ResponseEntity.ok(list);
    }
}
