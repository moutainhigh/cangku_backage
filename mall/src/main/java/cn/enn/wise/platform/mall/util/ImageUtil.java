package cn.enn.wise.platform.mall.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by zj on 2018/10/18.
 */
public class ImageUtil {

    public static void main(String[] args) {

        String qrCodePath = "D:\\qrcode.jpg";
        String backgroundPath = "D:\\hb1.jpg";
        String phone ="2508";
        String userName="15810633897";
        String outPutPath="D:\\"+userName+".jpg";

        // 保存网络图片
        downloadPicture("http://travel.enn.cn/group1/M00/00/4B/CiaAUl1FZ8iACtUUAADceM5EaBg976.jpg",qrCodePath);


        cutImage(qrCodePath,qrCodePath,490,463);

        // 生成本地海报
        overlapImageHb(backgroundPath,qrCodePath,phone,outPutPath);
    }

    public static String overlapImageA3(String backgroundPath,String qrCodePath,String message01,String outPutPath){
        try {
            //设置图片大小
            BufferedImage background = resizeImage(2480,3508, ImageIO.read(new File(backgroundPath)));
            BufferedImage qrCode = resizeImage(490,490,ImageIO.read(new File(qrCodePath)));
            Graphics2D g = background.createGraphics();
            g.setColor(Color.BLACK);
            g.setBackground(Color.black);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString(message01,1190 ,1560);
            //在背景图片上添加二维码图片
            g.drawImage(qrCode, 995, 1030, qrCode.getWidth(), qrCode.getHeight(), null);
            g.dispose();
            ImageIO.write(background, "jpg", new File(outPutPath));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String overlapImageA4(String backgroundPath,String qrCodePath,String message01,String outPutPath){
        try {
            //设置图片大小
            BufferedImage background = resizeImage(2480,3508, ImageIO.read(new File(backgroundPath)));
            BufferedImage qrCode = resizeImage(490,490,ImageIO.read(new File(qrCodePath)));
            Graphics2D g = background.createGraphics();
            g.setColor(Color.BLACK);
            g.setBackground(Color.black);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString(message01,1190 ,1560);
            //在背景图片上添加二维码图片
            g.drawImage(qrCode, 995, 1030, qrCode.getWidth(), qrCode.getHeight(), null);
            g.dispose();
            ImageIO.write(background, "jpg", new File(outPutPath));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String overlapImageHb(String backgroundPath,String qrCodePath,String message01,String outPutPath){
        try {
            //设置图片大小
            BufferedImage background = resizeImage(3543,9449, ImageIO.read(new File(backgroundPath)));
            BufferedImage qrCode = resizeImage(490,463,ImageIO.read(new File(qrCodePath)));
            Graphics2D g = background.createGraphics();
            g.setColor(Color.BLACK);
            g.setBackground(Color.black);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString(message01,1500 ,2560);
            //在背景图片上添加二维码图片
            g.drawImage(qrCode, 1300, 1900, qrCode.getWidth(), qrCode.getHeight(), null);
            g.dispose();
            ImageIO.write(background, "jpg", new File(outPutPath));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage resizeImage(int x, int y, BufferedImage bfi){
        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(
                bfi.getScaledInstance(x, y, Image.SCALE_SMOOTH), 0, 0, null);
        return bufferedImage;
    }

    //链接url下载图片
    private static void downloadPicture(String urlList,String path) {
        URL url = null;
        int imageNumber = 0;

        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            String imageName = path;

            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context=output.toByteArray();
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片裁剪
     * @param srcImageFile 图片裁剪地址
     * @param result 图片输出文件夹
     * @param destWidth 图片裁剪宽度
     * @param destHeight 图片裁剪高度
     */
    public static void cutImage(String srcImageFile, String result,
                                      int destWidth, int destHeight) {
        try {
            Iterator iterator = ImageIO.getImageReadersByFormatName("JPEG");/*PNG,BMP*/
            ImageReader reader = (ImageReader)iterator.next();/*获取图片尺寸*/
            InputStream inputStream = new FileInputStream(srcImageFile);
            ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rectangle = new Rectangle(0,0, destWidth, destHeight);/*指定截取范围*/
            param.setSourceRegion(rectangle);
            BufferedImage bi = reader.read(0,param);
            ImageIO.write(bi, "JPEG", new File(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
