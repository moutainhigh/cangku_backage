/**  
 * Project Name:encdata-pandaro-api  
 * File Name:CityEntity.java  
 * Package Name:com.encdata.pandaro.entity  
 * Date:2017年11月14日下午1:37:50  
 * Fixed by whz in 2017/10/24 
 *  
*/  
  
package cn.enn.wise.ssop.service.cms.entity.oldcms;

import java.io.Serializable;
import java.util.Date;

/**  
 * 简述功能
 * ClassName: CityEntity    
 * date: 2017年11月14日 下午1:37:50 
 * @author whz   
 * @since JDK 1.8 
 */
public class CityEntity implements Serializable{

	/**  
	 * 城市
	 * FiledName:serialVersionUID
	 */
	private static final long serialVersionUID = -1960218023923035444L;
	/**
	 * id
	 */
	private long id;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String comment;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		CityEntity other = (CityEntity) obj;
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
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
		return "CityEntity [id=" + id + ", code=" + code + ", name=" + name + ", comment=" + comment + ", updateTime="
				+ updateTime + "]";
	}
	
}
  
