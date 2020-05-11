package cn.enn.wise.platform.mall.util;

import cn.enn.wise.platform.mall.bean.vo.OrderQrCodeVo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/7/30 11:16
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:二维码封装类
 ******************************************/
@Slf4j
public class QrCodeUtil {

    private static final int QRCOLOR = 0xFF000000;
    private static final int BGWHITE = 0xFFFFFFFF;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private static HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
        private static final long serialVersionUID = 1L;
        {
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            put(EncodeHintType.CHARACTER_SET, "utf-8");
            put(EncodeHintType.MARGIN, 0);
        }
    };

    public static OrderQrCodeVo drawLogoQRCode(String qrUrl, String note) {
        log.info("进入绘制二维码方法");
        OrderQrCodeVo orderQrCodeVo= new OrderQrCodeVo();
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix bm = multiFormatWriter.encode(qrUrl, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }

            int width = image.getWidth();
            int height = image.getHeight();
            /*if (StringUtils.isNotEmpty(note)) {

                BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg = outImage.createGraphics();

                outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

                outg.setColor(Color.BLACK);
                outg.setFont(new Font("楷体", Font.BOLD, 30));
                int strWidth = outg.getFontMetrics().stringWidth(note);
                if (strWidth > 399) {

                    String note1 = note.substring(0, note.length() / 2);
                    String note2 = note.substring(note.length() / 2, note.length());
                    int strWidth1 = outg.getFontMetrics().stringWidth(note1);
                    int strWidth2 = outg.getFontMetrics().stringWidth(note2);
                    outg.drawString(note1, 200 - strWidth1 / 2, height + (outImage.getHeight() - height) / 2 + 12);
                    BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D outg2 = outImage2.createGraphics();
                    outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                    outg2.setColor(Color.BLACK);
                    outg2.setFont(new Font("宋体", Font.BOLD, 30));
                    outg2.drawString(note2, 200 - strWidth2 / 2,
                            outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight()) / 2 + 5);
                    outg2.dispose();
                    outImage2.flush();
                    outImage = outImage2;
                } else {
                    outg.drawString(note, 200 - strWidth / 2, height + (outImage.getHeight() - height) / 2 + 12);
                }
                outg.dispose();
                outImage.flush();
                image = outImage;
            }*/
            image.flush();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            byte[] byteArray = os.toByteArray();
            String uploadFile =  FastDFSClientUtil.uploadFile("png", byteArray);
            orderQrCodeVo.setNum(randomcode());
            orderQrCodeVo.setQrCodeUrl(uploadFile);
            return orderQrCodeVo;
        } catch (Exception e) {
            log.error("绘制二维码出错",e);
            throw new RuntimeException(e);
        }
    }

    private static  String randomcode(){
        String randomcode = "";
        String model = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] m = model.toCharArray();
        for (int j = 0; j < 6; j++) {
            char c = m[(int) (Math.random() * 36)];
            if (randomcode.contains(String.valueOf(c))) {
                j--;
                continue;
            }
            randomcode = randomcode + c;
        }
        return randomcode;
    }

}
