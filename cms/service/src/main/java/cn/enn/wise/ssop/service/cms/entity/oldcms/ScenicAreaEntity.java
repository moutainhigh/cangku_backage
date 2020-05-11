/**  
 * Project Name:encdata-pandaro-api  
 * File Name:ScenicareaEntity.java  
 * Package Name:com.encdata.pandaro.entity  
 * Date:2017年11月14日下午1:34:12  
 * Fixed by whz in 2017/10/24 
 *  
*/  
  
package cn.enn.wise.ssop.service.cms.entity.oldcms;

import cn.enn.wise.ssop.service.cms.entity.oldcms.LatAndLonEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**  
 * 简述功能
 * ClassName: ScenicareaEntity    
 * date: 2017年11月14日 下午1:34:12 
 * @author whz   
 * @since JDK 1.8 
 */
public class ScenicAreaEntity implements Serializable{

	/**  
	 * 序列号
	 * FiledName:serialVersionUID
	 */
	private static final long serialVersionUID = 8878864561620373521L;
	/**
	 * id
	 */
	private long id;
	/**
	 * 编码
	 */
	private String code;

	/**
	 * city code
	 */
	private String cityCode;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 英文名称
	 */
	private String enName;
	/**
	 * 所在城市
	 */
	private CityEntity city;
	/**
	 * 是否可用
	 */
	private int isUsed;
	/**
	 * 维度
	 */
	private double lat;
	/**
	 * 经度
	 */
	private double lon;
	/**
	 * 精度
	 */
	private double pre;
	/**
	 * 详情
	 */
	private String detail;
	/**
	 * 语音
	 */
	private String redio;
	/**
	 * 语音
	 */
	private int state;
	/**
	 * 比例尺
	 */
	private int scale;
	/**
	 * 级别
	 */
	private String gradeName;
	/**
	 * 图片
	 */
	private String picture;
	/**
	 * 图片
	 */
	private String icon;
	/**
	 * 描述
	 */
	private String comment;
	/**
	 * 更新时间
	 */
	private Date updateTime;
//	/**
//	 * 景区资源
//	 */
//	private List<ScenicResEntity> scenicRes = new ArrayList<ScenicResEntity>();
	/**
	 * 边界经纬度
	 */
	private List<LatAndLonEntity> sides = new ArrayList<LatAndLonEntity>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public CityEntity getCity() {
		return city;
	}
	public void setCity(CityEntity city) {
		this.city = city;
	}
	public int getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
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
	public double getPre() {
		return pre;
	}
	public void setPre(double pre) {
		this.pre = pre;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getRedio() {
		return redio;
	}
	public void setRedio(String redio) {
		this.redio = redio;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
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
	public List<LatAndLonEntity> getSides() {
		return sides;
	}
	public void setSides(List<LatAndLonEntity> sides) {
		this.sides = sides;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + ((enName == null) ? 0 : enName.hashCode());
		result = prime * result + ((gradeName == null) ? 0 : gradeName.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + isUsed;
		long temp;
		temp = Double.doubleToLongBits(lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((picture == null) ? 0 : picture.hashCode());
		temp = Double.doubleToLongBits(pre);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((redio == null) ? 0 : redio.hashCode());
		result = prime * result + scale;
		result = prime * result + ((sides == null) ? 0 : sides.hashCode());
		result = prime * result + state;
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
		ScenicAreaEntity other = (ScenicAreaEntity) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		if (enName == null) {
			if (other.enName != null)
				return false;
		} else if (!enName.equals(other.enName))
			return false;
		if (gradeName == null) {
			if (other.gradeName != null)
				return false;
		} else if (!gradeName.equals(other.gradeName))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (id != other.id)
			return false;
		if (isUsed != other.isUsed)
			return false;
		if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
			return false;
		if (Double.doubleToLongBits(lon) != Double.doubleToLongBits(other.lon))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (picture == null) {
			if (other.picture != null)
				return false;
		} else if (!picture.equals(other.picture))
			return false;
		if (Double.doubleToLongBits(pre) != Double.doubleToLongBits(other.pre))
			return false;
		if (redio == null) {
			if (other.redio != null)
				return false;
		} else if (!redio.equals(other.redio))
			return false;
		if (scale != other.scale)
			return false;
		if (sides == null) {
			if (other.sides != null)
				return false;
		} else if (!sides.equals(other.sides))
			return false;
		if (state != other.state)
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
		return "ScenicAreaEntity [id=" + id + ", code=" + code + ", name=" + name + ", enName=" + enName + ", city="
				+ city + ", isUsed=" + isUsed + ", lat=" + lat + ", lon=" + lon + ", pre=" + pre + ", detail=" + detail
				+ ", redio=" + redio + ", state=" + state + ", scale=" + scale + ", gradeName=" + gradeName
				+ ", picture=" + picture + ", icon=" + icon + ", comment=" + comment + ", updateTime=" + updateTime
				 + ", sides=" + sides + "]";
	}


}
  
