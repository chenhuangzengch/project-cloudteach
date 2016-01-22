package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * 大家分享的预习项展示信息
 * Created by panglx on 2015/7/16 0016.
 */
public class GuidanceItemShareWrapper {
	/**
	 * 预习项信息
	 */
	GuidanceItemShare guidanceItemShare;

	/**
	 * 点赞人数
	 */
	private int praiseUsers;

	/**
	 * 预习项点赞用户信息
	 */
	List<GuidanceItemSharePraise> guidanceItemSharePraises;

	/**
	 * 预习项附件信息
	 */
	List<GuidanceItemFilesDetail> guidanceItemFilesDetails;

	public int getPraiseUsers() {
		return praiseUsers;
	}

	public void setPraiseUsers(int praiseUsers) {
		this.praiseUsers = praiseUsers;
	}

	public GuidanceItemShare getGuidanceItemShare() {
		return guidanceItemShare;
	}

	public void setGuidanceItemShare(GuidanceItemShare guidanceItemShare) {
		this.guidanceItemShare = guidanceItemShare;
	}

	public List<GuidanceItemSharePraise> getGuidanceItemSharePraises() {
		return guidanceItemSharePraises;
	}

	public void setGuidanceItemSharePraises(List<GuidanceItemSharePraise> guidanceItemSharePraises) {
		this.guidanceItemSharePraises = guidanceItemSharePraises;
	}

	public List<GuidanceItemFilesDetail> getGuidanceItemFilesDetails() {
		return guidanceItemFilesDetails;
	}

	public void setGuidanceItemFilesDetails(List<GuidanceItemFilesDetail> guidanceItemFilesDetails) {
		this.guidanceItemFilesDetails = guidanceItemFilesDetails;
	}
}
