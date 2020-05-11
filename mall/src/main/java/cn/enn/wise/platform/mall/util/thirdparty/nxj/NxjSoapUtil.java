package cn.enn.wise.platform.mall.util.thirdparty.nxj;

import com.google.common.collect.Lists;
import encdata1.PFTMXBindingStub;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author baijie
 * @date 2020-02-11
 */
public class NxjSoapUtil {
    // 测试环境地址
   // public static String USER_NAME = "100019";
  //public static String PASSWORD = "8fd46f8a187f6665ae716087a0a377d1";

    public static String USER_NAME = "355135";
    public static String PASSWORD = "bb1eb0746dc9e5c9e8bd2db3dc219046";

    public static String N = "0";
    public static String M = "1000";

    public static PFTMXBindingStub binding;

    static {
        try {
            binding = (PFTMXBindingStub) new encdata1.PFTMXLocator().getPFTMXPort();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws RemoteException {
//        String xml = binding.PFT_Order_Submit( NxjSoapUtil.USER_NAME,
//                NxjSoapUtil.PASSWORD,
//                "73038",
//                "189931",
//                "20200228162615598094170r",
//                "100",
//                "1",
//                "2020-02-28",
//                "白杰",
//                "15303786335",
//                "15303786335",
//                "1",
//                "2",
//                "0",
//                null,
//                null,
//                "0",
//                "0",
//                "6970",
//                "41138119970328391X",
//                "");

//        String xml = binding.get_ScenicSpot_List(USER_NAME, PASSWORD, N, M);
        String realTimeStorage = binding.getRealTimeStorage(USER_NAME, PASSWORD, "954117", "246040", "2020-03-04", "2020-03-04"
        );
        System.out.println(realTimeStorage);
    }

    public static List<Object> getGoodList(){
        List<Object> resultList = Lists.newArrayList();
        try {
            /* 获取景区列表 */
            String xml = binding.get_ScenicSpot_List(USER_NAME, PASSWORD, N, M);
            String json = XmlHelper.xml2json(xml);
            List<Object> list = XmlHelper.parseJson2List(json);
            for (Object item : list) {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Map<String, Object> map = (HashMap) item;
                String uuid = map.get("UUid").toString();
                System.out.println("景区uuid is: "+uuid);
                /* 获取景区详情 */
                String scenicInfo = binding.get_ScenicSpot_Info(USER_NAME, PASSWORD, uuid);
                String scenicJson = XmlHelper.xml2json(scenicInfo);
                List<Object> scenicList = XmlHelper.parseJson2List(scenicJson);
                for(Object scenic:scenicList){
                    Map<String,Object> params = (HashMap)scenic;
                    System.out.println("景区详情："+params);
                    params.put("uuid",uuid);
                    /* 景区入库--暂不需要 */
                    //Integer spotId=nxjTicketOrderService.insertScenic(params);
                    /* 获取门票列表 */
                    List<Object> ticketList=getTicketList(uuid);
                    System.out.println("门票列表："+ticketList);
                    //去除无效的门票返回信息
                    if(!ticketList.get(0).toString().contains("UUerrorcode=105")){
                        System.out.println("有效门票列表："+ticketList);
                        resultList.addAll(ticketList);
                    }
                }
            }
        } catch (java.rmi.RemoteException ex) {
            ex.printStackTrace();
        }
        return resultList;
    }

    /**
     * 获取门票列表
     * @param scenicId 景区id
     * @return 门票
     * @throws RemoteException 异常
     */
    public static List<Object> getTicketList(String scenicId) throws RemoteException {

        String xml= binding.get_Ticket_List(USER_NAME, PASSWORD,scenicId,null);
        String json = XmlHelper.xml2json(xml);
        return XmlHelper.parseJson2List(json);

    }
}
