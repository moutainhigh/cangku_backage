package cn.enn.wise.platform.mall.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.enn.wise.platform.mall.config.WxMaConfiguration;
import cn.enn.wise.platform.mall.util.FastDFSClientUtil;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/24 11:05
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:二维码API
 ******************************************/
@Api(value = "二维码Controller", tags = { "二维码Controller" })
@RestController
@RequestMapping("/code")
public class QrCodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QrCodeController.class);


    private static final double INCH_2_CM = 2.54d;

    /*private WxPayService wxService;

    @Autowired
    public QrCodeController(WxPayService wxService) {
        this.wxService = wxService;
   }
*/

//    @GetMapping("/down")
//    @ResponseBody
//    @ApiOperation(value = "下载二维码", notes = "下载二维码")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "String", name = "phone", value = "手机号", required = true)})
    public void downCode(String phone,HttpServletResponse response) throws IOException{

        try {
            WxMaService server = WxMaConfiguration.getMaService("wx76453a21da3fe5b3");
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append("pages/ticketshome/ticketshome?s=hotel&n="+phone);
            File file =server.getQrcodeService().createQrcode(strBuffer.toString());
            file.createNewFile();
            byte[] buffer =getBytes(file);
            String url = FastDFSClientUtil.uploadFile(UUID.randomUUID().toString()+".jpg", buffer);
//            String qrCodePath="D:\\qrcode.jpg";
            String qrName=phone+".jpg";
            String fileSep="/";
            String filePath="/data/qrcode/";
            String qrCodePath="/data/qrcode/"+qrName;
            String baseCodePath= "/data/qrcode/"+phone+fileSep;
            String backgroundPathA3 = filePath+"A3.jpg";
            String backgroundPathA4 = filePath+"A4.jpg";
            String backgroundPathHB = filePath+"A5.jpg";

            downloadPicture(url,qrCodePath);
            cutImage(qrCodePath,qrCodePath,490,463);
            String phoneUp =phone.substring(7,11);


            File pp = new File(filePath+phone);
            pp.mkdir();

            String pathName3 = baseCodePath+"A3.jpg";
            String pathName4 = baseCodePath+"A4.jpg";
            String pathName5 = baseCodePath+"A5.jpg";

            // 生成本地海报
            overlapImageA3(backgroundPathA3,qrCodePath,phoneUp,pathName3);
            overlapImageA4(backgroundPathA4,qrCodePath,phoneUp,pathName4);
            overlapImageHb(backgroundPathHB,qrCodePath,phoneUp,pathName5);

            File file3 = new File(pathName3);
            File file4 = new File(pathName4);
            File file5 = new File(pathName5);
            //handleDpi(file3,150 , 150);
            //handleDpi(file4,150 , 150);
            //handleDpi(file5,150 , 150);

            saveGridImage(file3);
            saveGridImage(file4);
            saveGridImage(file5);


            fileToZip(baseCodePath,filePath,phone);

            String fileNames = phone+".zip";

            String downPath =filePath+fileNames;

            File imageFile = new File(downPath);
            if (!imageFile.exists()) {
                return;
            }

            //下载的文件携带这个名称
            response.setHeader("Content-Disposition", "attachment;filename=" +fileNames );
            //文件下载类型--二进制文件
            response.setContentType("application/octet-stream");

            try {
                FileInputStream fis = new FileInputStream(downPath);
                byte[] content = new byte[fis.available()];
                fis.read(content);
                fis.close();
                ServletOutputStream sos = response.getOutputStream();
                sos.write(content);
                sos.flush();
                sos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //    deleteDir(baseCodePath);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("qrCodeJob error"+e);
        }

    }
    @PostMapping("/gain")
    @ResponseBody
    @ApiOperation(value = "获取二维码", notes = "获取二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "phone", value = "手机号", required = true)})
    public ResponseEntity unifyCode(String phone,String appId) throws IOException{
        LOGGER.info("unifyCode start >>>>>>>>>>>>>>>>");
        ResponseEntity resultVo = new ResponseEntity();
        try {
            WxMaService server = WxMaConfiguration.getMaService(appId);
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append("pages/ticketshome/ticketshome?s=app&n="+phone);
            File file =server.getQrcodeService().createQrcode(strBuffer.toString());
            file.createNewFile();
            byte[] buffer =getBytes(file);
            String url = FastDFSClientUtil.uploadFile(UUID.randomUUID().toString()+".jpg", buffer);

            resultVo.setResult(GeneConstant.INT_1);
            resultVo.setValue(url);
            LOGGER.info("unifyCode end >>>>>>>>>>>>>>>>");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("unifyCode error"+e);
        }
        return resultVo;
    }


    @PostMapping("/query")
    @ResponseBody
    @ApiOperation(value = "获取二维码", notes = "获取二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "phone", value = "手机号", required = true)})
    public ResponseEntity qrCodeJob(String phone,String appid) throws IOException{
        LOGGER.info("qrCodeJob start >>>>>>>>>>>>>>>>");
        ResponseEntity resultVo = new ResponseEntity();
        try {
            WxMaService server = WxMaConfiguration.getMaService("wx73466bappid979c886373");
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append("pages/ticketshome/ticketshome?s=app&n="+phone);
            File file =server.getQrcodeService().createQrcode(strBuffer.toString());
            file.createNewFile();
            byte[] buffer =getBytes(file);
            String url = FastDFSClientUtil.uploadFile(UUID.randomUUID().toString()+".jpg", buffer);

            resultVo.setResult(GeneConstant.INT_1);
            resultVo.setValue(url);
            LOGGER.info("qrCodeJob end >>>>>>>>>>>>>>>>");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("qrCodeJob error"+e);
        }
        return resultVo;
    }


    @PostMapping("/querys")
    @ResponseBody
    @ApiOperation(value = "获取二维码", notes = "获取二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "querys", dataType = "String", name = "phone", value = "手机号", required = true)})
    public ResponseEntity qrCodeJobWZD(String phone) throws IOException{
        LOGGER.info("qrCodeJobWZD start >>>>>>>>>>>>>>>>");
        ResponseEntity resultVo = new ResponseEntity();
        try {
            WxMaService server = WxMaConfiguration.getMaService("wx76453a21da3fe5b3");
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append("pages/ticketshome/ticketshome?s=app&n="+phone);
            File file =server.getQrcodeService().createQrcode(strBuffer.toString());
            file.createNewFile();
            byte[] buffer =getBytes(file);
            String url = FastDFSClientUtil.uploadFile(UUID.randomUUID().toString()+".jpg", buffer);

            resultVo.setResult(GeneConstant.INT_1);
            resultVo.setValue(url);
            LOGGER.info("qrCodeJobWZD end >>>>>>>>>>>>>>>>");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("qrCodeJobWZD error"+e);
        }
        return resultVo;
    }

    @ApiOperation(value = "上传文件",notes = "上传文件")
    @ResponseBody
    @PostMapping(value = "/uploadFiles",consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public ResponseEntity uploadFiles(List<MultipartFile> file) {
        try {
            if(null == file || file.size() == 0){
                return new ResponseEntity(2,"没有files","");
            }else{
                List<String> urlList = new ArrayList<String>();
                for (MultipartFile files : file) {
                    String url = FastDFSClientUtil.uploadFile(files.getOriginalFilename(), files.getBytes());
                    urlList.add(url);
                }
                return new ResponseEntity(1,"SUCCESS",urlList);
            }
        }catch (Exception e){
            return new ResponseEntity(2,"后台出错","");
        }
    }


    /**
     * 获得指定文件的byte数组
     */
    private byte[] getBytes(File file){
        byte[] buffer = null;
        try {

            FileInputStream fis = new FileInputStream(file);

            ByteArrayOutputStream bos = new ByteArrayOutputStream((int)file.length());

            byte[] b = new byte[(int)file.length()];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }



    public static String overlapImageA3(String backgroundPath,String qrCodePath,String message01,String outPutPath){
        try {
            //设置图片大小
            BufferedImage background = resizeImage(1754,2480, ImageIO.read(new File(backgroundPath)));
            BufferedImage qrCode = resizeImage(320,320,ImageIO.read(new File(qrCodePath)));
            Graphics2D g = background.createGraphics();
            g.setColor(new Color(117,154,195));
            g.setBackground(Color.black);
            g.setFont(new Font("淡灰色",Font.BOLD,35));
            g.drawString(message01,830 ,1100);
            //在背景图片上添加二维码图片
            g.drawImage(qrCode, 716, 730, qrCode.getWidth(), qrCode.getHeight(), null);
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
            BufferedImage background = resizeImage(1240,1754, ImageIO.read(new File(backgroundPath)));
            BufferedImage qrCode = resizeImage(225,225,ImageIO.read(new File(qrCodePath)));
            Graphics2D g = background.createGraphics();
            g.setColor(new Color(117,154,195));
            g.setBackground(Color.black);
            g.setFont(new Font("淡灰色",Font.BOLD,30));
            g.drawString(message01,580 ,780);
            //在背景图片上添加二维码图片
            g.drawImage(qrCode, 508, 520, qrCode.getWidth(), qrCode.getHeight(), null);
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
            BufferedImage qrCode = resizeImage(830,920,ImageIO.read(new File(qrCodePath)));
            Graphics2D g = background.createGraphics();
            g.setColor(new Color(117,154,195));
            g.setBackground(Color.black);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString(message01,1700 ,2860);
            //在背景图片上添加二维码图片
            g.drawImage(qrCode, 1360, 1890, qrCode.getWidth(), qrCode.getHeight(), null);
            g.dispose();
            // ImageIO.write(background, "jpg", new File(outPutPath));
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
    public static void downloadPicture(String urlList,String path) {
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


    /**
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
     * @param sourceFilePath :待压缩的文件路径
     * @param zipFilePath :压缩后存放路径
     * @param fileName :压缩后文件的名称
     * @return
     */
    public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if(sourceFile.exists() == false){
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");
        }else{
            try {
                File zipFile = new File(zipFilePath + "/" + fileName +".zip");
                if(zipFile.exists()){
                    System.out.println(zipFilePath + "目录下存在名字为:" + fileName +".zip" +"打包文件.");
                }else{
                    File[] sourceFiles = sourceFile.listFiles();
                    if(null == sourceFiles || sourceFiles.length<1){
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                    }else{
                        fos = new FileOutputStream(zipFile);
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));
                        byte[] bufs = new byte[1024*10];
                        for(int i=0;i<sourceFiles.length;i++){
                            //创建ZIP实体，并添加进压缩包
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                            zos.putNextEntry(zipEntry);
                            //读取待压缩的文件并写进压缩包里
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, 1024*10);
                            int read = 0;
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){
                                zos.write(bufs,0,read);
                            }
                        }
                        flag = true;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally{
                //关闭流
                try {
                    if(null != bis) bis.close();
                    if(null != zos) zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return flag;
    }


    private static void saveGridImage(File output) throws IOException {
        BufferedImage image = ImageIO.read(output);
        final String formatName = "png";

        for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName(formatName); iw.hasNext();) {
            ImageWriter writer = iw.next();
            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
            IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
            if (metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
                continue;
            }
            setDPI(metadata);
            final ImageOutputStream stream = ImageIO.createImageOutputStream(output);
            try {
                writer.setOutput(stream);
                writer.write(metadata, new IIOImage(image, null, metadata), writeParam);
            } finally {
                stream.close();
            }
            break;
        }
    }


    private static void setDPI(IIOMetadata metadata) throws IIOInvalidTreeException {

        // for PMG, it's dots per millimeter
        double dotsPerMilli = 1.0 * 200 / 10 / INCH_2_CM;

        IIOMetadataNode horiz = new IIOMetadataNode("HorizontalPixelSize");
        horiz.setAttribute("value", Double.toString(dotsPerMilli));

        IIOMetadataNode vert = new IIOMetadataNode("VerticalPixelSize");
        vert.setAttribute("value", Double.toString(dotsPerMilli));

        IIOMetadataNode dim = new IIOMetadataNode("Dimension");
        dim.appendChild(horiz);
        dim.appendChild(vert);

        IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
        root.appendChild(dim);

        metadata.mergeTree("javax_imageio_1.0", root);
    }
}
