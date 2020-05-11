/**  
 * Project Name:encdata-commom  
 * File Name:FastDFSClient.java  
 * Package Name:com.encdata.common.core.util.fastdfs  
 * Date:2017年12月6日下午4:19:29  
 * Fixed by whz in 2017/10/24 
 *  
*/

package cn.enn.wise.ssop.service.cms.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * fastdfs工具类 ClassName: FastDFSClientUtil date: 2017年12月6日 下午4:19:29
 * 
 * @author whz
 * @since JDK 1.8
 */
@Slf4j
@Service("FastDFSClientUtil")
public class FastDFSClientUtil {


	@Value("${fileserver_domain}")
	String fileserverDomain;
	/**
	 * 配置文件路径
	 */
	private static final String CONFIG_FILENAME = "/fastDFS.conf";
//	private static final String CONFIG_FILENAME = "D:\\picture\\fastDFS.conf";
	/**
	 * 配置文件路径
	 */
	private static final String SELF_CONFIG_FILENAME = "commomconf/conf.properties";
	/**
	 * 路径分隔符
	 */
	private static final String SEPERATOR = "/";

	/**
	 * fastdfs客户端对象
	 */
	private static StorageClient1 storageClient1 = null;
	/**
	 * 初始化客户端FastDFS Client
	 */
	public FastDFSClientUtil(){
		try {
			log.info("FastDFSClientUtil is initing client...");

			// ClientGlobal.init(CONFIG_FILENAME);
			// 使用配置信息初始全局信息
			ClientGlobal.initByProperties(CONFIG_FILENAME);
			// 初始化客户端
			TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
			TrackerServer trackerServer = trackerClient.getConnection();
			if (trackerServer == null) {
				throw new IllegalStateException("getConnection return null");
			}

			StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
			if (storageServer == null) {
				throw new IllegalStateException("getStoreStorage return null");
			}
			// 获取客户端对象
			storageClient1 = new StorageClient1(trackerServer, storageServer);

			log.info("FastDFSClientUtil initing client finished");
		} catch (Exception e) {
			log.error("error : FastDFSClientUtil init client error...", e);
		}
	}
	/**
	 * 上传文件
	 * 
	 * @param file
	 *            文件对象
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws MyException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String uploadFile(File file, String fileName) throws FileNotFoundException, IOException, MyException {
		return uploadFile(file, fileName, null);
	}

	/**
	 * 批量上传文件
	 * @Title uploadFiles 
	 * @param picFile
	 * @return
	 * @throws IOException
	 * @throws MyException
	 * @since JDK 1.8  
	 * @throws
	 */
	public String uploadFiles(MultipartFile picFile) throws IOException, MyException {
		String s = picFile.getOriginalFilename();
		byte[] bytes =  picFile.getBytes();
		return uploadFile(s, bytes);
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            文件对象
	 * @param uploadFileByte
	 *            文件名
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	public String uploadFile(String s, byte[] uploadFileByte) throws IOException, MyException {
		String[] upload_file = storageClient1.upload_file(uploadFileByte, s.substring(s.lastIndexOf(".") + 1), null);
		StringBuilder b = new StringBuilder();
		for (String str : upload_file) {
			b.append(str).append(SEPERATOR);
		}
		s = b.substring(0, b.length() - 1);
//		InetSocketAddress address = ClientGlobal.g_tracker_group.tracker_servers[0];
		return fileserverDomain + SEPERATOR + s;
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            文件对象
	 * @param fileName
	 *            文件名
	 * @param metaList
	 *            文件元数据
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws MyException
	 */
	public String uploadFile(File file, String fileName, Map<String, String> metaList)
			throws FileNotFoundException, IOException, MyException {

		byte[] buff = IOUtils.toByteArray(new FileInputStream(file));
		NameValuePair[] nameValuePairs = null;
		if (metaList != null) {
			nameValuePairs = new NameValuePair[metaList.size()];
			int index = 0;
			for (Iterator<Map.Entry<String, String>> iterator = metaList.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, String> entry = iterator.next();
				String name = entry.getKey();
				String value = entry.getValue();
				nameValuePairs[index++] = new NameValuePair(name, value);
			}
		}
		String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
		return fileserverDomain+SEPERATOR+storageClient1.upload_file1(buff, extName, nameValuePairs);

	}

	/**
	 * 获取文件元数据
	 * @param fileId
	 *            文件ID
	 * @return
	 * @throws MyException 
	 * @throws IOException 
	 */
	public Map<String, String> getFileMetadata(String fileId) throws IOException, MyException {
			NameValuePair[] metaList = storageClient1.get_metadata1(fileId);
			if (metaList != null) {
				HashMap<String, String> map = new HashMap<String, String>();
				for (NameValuePair metaItem : metaList) {
					map.put(metaItem.getName(), metaItem.getValue());
				}
				return map;
			}
		
		return null;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileId
	 *            文件ID
	 * @return 删除失败返回-1，否则返回0
	 */
	public int deleteFile(String fileId) {
		try {
			return storageClient1.delete_file1(fileId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 下载文件
	 * 
	 * @param fileId
	 *            文件ID（上传文件成功后返回的ID）
	 * @param outFile
	 *            文件下载保存位置
	 * @return
	 */
	public void downloadFile(String fileId, File outFile) {
		FileOutputStream fos = null;
		try {
			byte[] content = storageClient1.download_file1(fileId);
			fos = new FileOutputStream(outFile);
			IOUtils.write(content, fos);
		} catch (Exception e) {
			log.error("error downloadFile error...");
			throw new RuntimeException(e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					log.error("error downloadFile error...");
					throw new RuntimeException(e);
				}
			}
		}
	}
//	public void main(String[] args) {
//		File f = new File("C:\\Users\\Lenovo\\Desktop\\新建文件夹\\1557284657(1).png");
//		String name = "1557284657(1).png";
//		try {
//			String filePath = uploadFile(f,name);
//			System.out.println(filePath);
//		} catch (IOException | MyException e) {
//			e.printStackTrace();  
//		}
//	}
}
