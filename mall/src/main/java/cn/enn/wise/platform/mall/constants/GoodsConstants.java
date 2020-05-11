package cn.enn.wise.platform.mall.constants;

/**
 * 商品常量类
 *
 * @author caiyt
 */
public class GoodsConstants {
    /**
     * 商品状态枚举类
     */
    public enum GoodsStatusEnum {
        /**
         * 上架
         */
        ON_SHELF((byte) 1),
        /**
         * 下架
         */
        OFF_SHELF((byte) 2),
        /**
         * 已删除
         */
        DELETED((byte) 3);

        GoodsStatusEnum(byte status) {
            this.status = status;
        }

        private byte status;

        public byte value() {
            return this.status;
        }
    }

    /**
     * 商品扩展类状态
     */
    public enum GoodsExtendStatus {
        /**
         * 启用
         */
        ENABLE((byte) 1),
        /**
         * 禁用
         */
        DISABLE((byte) 2),
        /**
         * 删除
         */
        DELETED((byte) 3);

        GoodsExtendStatus(byte status) {
            this.status = status;
        }

        private byte status;

        public byte value() {
            return this.status;
        }
    }

    /**
     * 商品响应码枚举类
     */
    public enum GoodsResCodeEnum {
        /**
         * 商品名称已存在
         */
        GOODS_NAME_EXIST("该商品名称已存在，请更换名称！"),
        GOODS_PROJECT_NOT_EXIST("商品归属项目不存在！"),
        NO_MATCH_RECORDS_FOUND("未查到匹配记录！"),
        ONLY_OFF_SHELF_CAN_MODIFIEF("商品只有下架状态才可以编辑"),
        GOODS_PROJECT_PERIOD_NOT_EXIT("商品归属项目的时间段数据不存在！");

        private String message;

        GoodsResCodeEnum(String message) {
            this.message = message;
        }

        public String value() {
            return this.message;
        }
    }
}
