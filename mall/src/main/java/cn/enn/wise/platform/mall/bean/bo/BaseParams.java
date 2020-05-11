package cn.enn.wise.platform.mall.bean.bo;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/8/1 11:24
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
public class BaseParams {

    private String phoneNum;
    private String context;

    public BaseParams() {
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    public String toString() {
        return "BaseParams [phoneNum=" + this.phoneNum + ", context=" + this.context + "]";
    }
}
