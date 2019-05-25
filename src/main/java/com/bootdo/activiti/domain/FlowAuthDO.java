package com.bootdo.activiti.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 流程发起人授权
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-05-22 16:16:26
 */
public class FlowAuthDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//流程id
	private Long flowId;
	//流程名称
	private String flowName;
	//用户id
	private Long userId;
	//用户名称
	private String userName;
	//创建者id
	private Long createId;
	//创建者
	private String createBy;
	//创建时间
	private Date createDate;
	//更新着id
	private Long updateId;
	//更新者
	private String updateBy;
	//更新时间
	private Date updateDate;

	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：流程id
	 */
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}
	/**
	 * 获取：流程id
	 */
	public Long getFlowId() {
		return flowId;
	}
	/**
	 * 设置：流程名称
	 */
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	/**
	 * 获取：流程名称
	 */
	public String getFlowName() {
		return flowName;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：用户名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户名称
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：创建者id
	 */
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	/**
	 * 获取：创建者id
	 */
	public Long getCreateId() {
		return createId;
	}
	/**
	 * 设置：创建者
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建者
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：更新着id
	 */
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	/**
	 * 获取：更新着id
	 */
	public Long getUpdateId() {
		return updateId;
	}
	/**
	 * 设置：更新者
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：更新者
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

    @Override
    public String toString() {
        return "FlowAuthDO{" +
                "id=" + id +
                ", flowId=" + flowId +
                ", flowName='" + flowName + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", createId=" + createId +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateId=" + updateId +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                '}';
    }
}
