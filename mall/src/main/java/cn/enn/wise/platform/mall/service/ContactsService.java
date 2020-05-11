package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.param.ContactsParam;
import cn.enn.wise.platform.mall.bean.vo.ContactsVo;
import cn.enn.wise.platform.mall.util.ResponseEntity;

import java.util.List;

/**
 * 联系人
 * @author zsj
 * @date 2019/12/24  14:43
 */
public interface ContactsService {

    /**
     * 保存联系人
     * @param contactsParam
     * @return
     */
    ResponseEntity saveContacts(ContactsParam contactsParam);

    /**
     * 修改联系人
     * @param contactsParam
     * @return
     */
    ResponseEntity updateContacts(ContactsParam contactsParam);

    /**
     * 查询所有联系人
     * @param memberId
     * @return
     */
    ResponseEntity<List<ContactsVo>> findAllContacts(Long memberId);

    /**
     * 根据ids查询这个用户的联系人
     * @param memberId
     * @param ids
     * @return
     */
    ResponseEntity<List<ContactsVo>> findContactsByIds(Long memberId, String ids);

}
