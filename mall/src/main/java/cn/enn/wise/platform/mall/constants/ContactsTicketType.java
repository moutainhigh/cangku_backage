package cn.enn.wise.platform.mall.constants;

/**
 * 联系人类型  101-成人  203-儿童  404-携童
 * @author zsj
 * @date 2019/12/25  9:26
 */
public enum ContactsTicketType {

    /**
     * 成人票
     */
    ADULT(101),
    /**
     * 儿童票
     */
    CHILDREN(203),
    /**
     * 携童票
     */
    CARRY(404);

    private Integer status;

    ContactsTicketType(Integer status){
        this.status = status;
    }

    public Integer value(){
        return this.status;
    }
}
