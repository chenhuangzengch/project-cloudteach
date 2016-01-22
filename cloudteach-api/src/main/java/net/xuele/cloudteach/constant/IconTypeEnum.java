package net.xuele.cloudteach.constant;

/**
 * Created by panglx on 2015/7/7 0007.
 */
public enum IconTypeEnum {

	AUDIO(0,"audio"),
	EXCEL(1,"excel"),
	IMG(2,"img"),
	PDF(3,"pdf"),
	PPT(4,"ppt"),
	RAR(5,"pkg"),
	SWF(6,"swf"),
	TXT(7,"txt"),
	VIDEO(8,"video"),
	WORD(9,"word")

	;

	private Integer extCode;//类型代码
	private String extString;//类型名称
//	private ExtensionTypeEnum extensionType;//对应扩展名类型

	IconTypeEnum( Integer extCode, String extString) {
		this.extString = extString;
		this.extCode = extCode;
	}

	public int getExtCode() {
		return extCode;
	}

	public void setExtCode(int extCode) {
		this.extCode = extCode;
	}

	public String getExtString() {
		return extString;
	}

	public void setExtString(String extString) {
		this.extString = extString;
	}

}
