package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Magic2StuAnsJsonDTO
 *
 * @author panglx
 * @date on 2015/10/23 0023.
 */
public class Magic2StuAnsJsonDTO implements Serializable{

	private static final long serialVersionUID = -458377986239867250L;
	private String queId;//题目id

    private String parentId;//原题id

    private int type;//题目类型

    private Long queTime;//题目用时

    private int rw;//回答对错

    private int sort;//第几套题

    List<String> aIds;//答案id

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }

    public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getQueTime() {
		return queTime;
	}

	public void setQueTime(Long queTime) {
		this.queTime = queTime;
	}

	public List<String> getaIds() {
		return aIds;
	}

	public void setaIds(List<String> aIds) {
		this.aIds = aIds;
	}


    public int getRw() {
        return rw;
    }

    public void setRw(int rw) {
        this.rw = rw;
    }

    @Override
    public String toString() {
        return "Magic2StuAnsJsonDTO{" +
                "aIds=" + aIds +
                ", queId='" + queId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", queTime=" + queTime +
                '}';
    }
}
