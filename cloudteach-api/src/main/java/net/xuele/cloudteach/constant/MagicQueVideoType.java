package net.xuele.cloudteach.constant;

import java.util.EnumSet;
import java.util.HashMap;

/**
 * MagicQueVideoType
 *
 * @author panglx
 * @date on 2015/8/22 0022.
 */
public enum MagicQueVideoType {
	FLV("flv",1,0),
	MP4("mp4",1,1),
	MKV("mkv",1,1),
	;


	private String extension;
	private Integer isPcType;
	private Integer isMobileType;

	private static HashMap<String, Integer> mapper1 = new HashMap<>();
	static {
		for (MagicQueVideoType etm : EnumSet.allOf(MagicQueVideoType.class)) {
			mapper1.put(etm.extension, etm.isPcType);
		}
	}

	private static HashMap<String, Integer> mapper2 = new HashMap<>();
	static {
		for (MagicQueVideoType etm : EnumSet.allOf(MagicQueVideoType.class)) {
			mapper2.put(etm.extension, etm.isMobileType);
		}
	}

	public static int getIsPcType(String extension) {
		return mapper1.get(extension)!=null?mapper1.get(extension):0;
	}
	public static int getIsMobileType(String extension) {
		return mapper1.get(extension)!=null?mapper1.get(extension):0;
	}

	MagicQueVideoType(String extension, Integer isPcType,Integer isMobileType) {
		this.extension = extension;
		this.isPcType = isPcType;
		this.isMobileType = isMobileType;
	}




}
