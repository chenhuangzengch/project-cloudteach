package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Magic2WorkUnitsRateDTO
 *
 * @author panglx
 * @date on 2015/12/24 0024.
 */
public class Magic2WorkUnitsRateDTO implements Serializable{
	private static final long serialVersionUID = 3716406020822783076L;
	/**
	 * 课程id
	 */
	private String unitId;

	/**
	 * 课程对应的百分比
	 */
	private List<Magic2WorkBookRateDTO> ratesList;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public List<Magic2WorkBookRateDTO> getRatesList() {
		return ratesList;
	}

	public void setRatesList(List<Magic2WorkBookRateDTO> ratesList) {
		this.ratesList = ratesList;
	}
}
