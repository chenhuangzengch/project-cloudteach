package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 大家分享的预习项显示DTO
 * Created by panglx on 2015/7/15 0015.
 */
public class GuidanceItemShareShowDTO implements Serializable {
	private static final long serialVersionUID = -5333779950126509928L;
	/**
	 * 预习项信息
	 */
	GuidanceItemShareDTO guidanceItemShareDTO;
	/**
	 * 点赞人数
	 */
	private int praiseUsers;
	
	/**
	 * 预习项附件信息
	 */
	List<GuidanceItemFilesDetailDTO> guidanceItemFilesDetailDTOs;

	/**
	 * 预习项点赞用户信息
	 */
	List<GuidanceItemSharePraiseDTO> guidanceItemSharePraiseDTOs;

	public int getPraiseUsers() {
		return praiseUsers;
	}

	public void setPraiseUsers(int praiseUsers) {
		this.praiseUsers = praiseUsers;
	}

	public GuidanceItemShareDTO getGuidanceItemShareDTO() {
		return guidanceItemShareDTO;
	}

	public void setGuidanceItemShareDTO(GuidanceItemShareDTO guidanceItemShareDTO) {
		this.guidanceItemShareDTO = guidanceItemShareDTO;
	}

	public List<GuidanceItemFilesDetailDTO> getGuidanceItemFilesDetailDTOs() {
		return guidanceItemFilesDetailDTOs;
	}

	public void setGuidanceItemFilesDetailDTOs(List<GuidanceItemFilesDetailDTO> guidanceItemFilesDetailDTOs) {
		this.guidanceItemFilesDetailDTOs = guidanceItemFilesDetailDTOs;
	}

	public List<GuidanceItemSharePraiseDTO> getGuidanceItemSharePraiseDTOs() {
		return guidanceItemSharePraiseDTOs;
	}

	public void setGuidanceItemSharePraiseDTOs(List<GuidanceItemSharePraiseDTO> guidanceItemSharePraiseDTOs) {
		this.guidanceItemSharePraiseDTOs = guidanceItemSharePraiseDTOs;
	}

	@Override
	public String toString() {
		return "GuidanceItemShareShowDTO{" +
				"guidanceItemShareDTO=" + guidanceItemShareDTO +
				", praiseUsers=" + praiseUsers +
				", guidanceItemFilesDetailDTOs=" + guidanceItemFilesDetailDTOs +
				", guidanceItemSharePraiseDTOs=" + guidanceItemSharePraiseDTOs +
				'}';
	}
}
