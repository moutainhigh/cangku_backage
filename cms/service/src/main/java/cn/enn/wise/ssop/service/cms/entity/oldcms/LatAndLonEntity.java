/**  
 * Project Name:encdata-pandaro-api  
 * File Name:LatAndLonBean.java  
 * Package Name:com.encdata.pandaro.entity  
 * Date:2017年11月14日下午12:08:40  
 * Fixed by whz in 2017/10/24 
 *  
*/  
  
package cn.enn.wise.ssop.service.cms.entity.oldcms;

import java.io.Serializable;
import java.util.Date;

/**  
 * 经纬度
 * ClassName: LatAndLonEntity    
 * date: 2017年11月14日 下午12:08:40 
 * @author whz   
 * @since JDK 1.8 
 */
public class LatAndLonEntity implements Serializable{

	/**  
	 * 序列号
	 * FiledName:serialVersionUID
	 */
	private static final long serialVersionUID = 7967687658785763288L;
	/**
	 * id
	 */
	private long id;
	/**
	 * 实体id
	 */
	private long refId;
	/**
	 * 实体名称
	 */
	private String refName;
	/**
	 * 实体id
	 */
	private long ref;
	/**
	 * 维度
	 */
	private double lat;
	/**
	 * 经度
	 */
	private double lon;
	/**
	 * 所属类型 1道路中心2道路左边界3道路右边界4景区边界
	 */
	private int type;
	/**
	 * 经度
	 */
	private double pre;
	/**
	 * 描述
	 */
	private String comment;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	private long scenic;
	/**
	 * 无参构造方法
	 * Creates a new instance of LatAndLonEntity.  
	 * @param lat
	 * @param lon
	 */
	public LatAndLonEntity() {
		super();
	}
	/**
	 * 有参构造方法
	 * Creates a new instance of LatAndLonEntity.  
	 * @param lat
	 * @param lon
	 */
	public LatAndLonEntity(double lat, double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRefId() {
		return refId;
	}
	public void setRefId(long refId) {
		this.refId = refId;
	}
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
	}
	public long getRef() {
		return ref;
	}
	public void setRef(long ref) {
		this.ref = ref;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getPre() {
		return pre;
	}
	public void setPre(double pre) {
		this.pre = pre;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public long getScenic() {
		return scenic;
	}
	public void setScenic(long scenic) {
		this.scenic = scenic;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		long temp;
		temp = Double.doubleToLongBits(lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pre);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (ref ^ (ref >>> 32));
		result = prime * result + (int) (refId ^ (refId >>> 32));
		result = prime * result + ((refName == null) ? 0 : refName.hashCode());
		result = prime * result + (int) (scenic ^ (scenic >>> 32));
		result = prime * result + type;
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LatAndLonEntity other = (LatAndLonEntity) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
			return false;
		if (Double.doubleToLongBits(lon) != Double.doubleToLongBits(other.lon))
			return false;
		if (Double.doubleToLongBits(pre) != Double.doubleToLongBits(other.pre))
			return false;
		if (ref != other.ref)
			return false;
		if (refId != other.refId)
			return false;
		if (refName == null) {
			if (other.refName != null)
				return false;
		} else if (!refName.equals(other.refName))
			return false;
		if (scenic != other.scenic)
			return false;
		if (type != other.type)
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LatAndLonEntity [id=" + id + ", refId=" + refId + ", refName=" + refName + ", ref=" + ref + ", lat="
				+ lat + ", lon=" + lon + ", type=" + type + ", pre=" + pre + ", comment=" + comment + ", updateTime="
				+ updateTime + ", scenic=" + scenic + "]";
	}


	
	
}
  
