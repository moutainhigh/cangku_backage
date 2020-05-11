package cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipBaseVo<T>{

    /**
     * 状态　1为成功
     */
    private int status;

    /**
     * 信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;


    public ShipBaseVo(int status) {
        this.status = status;
    }
}