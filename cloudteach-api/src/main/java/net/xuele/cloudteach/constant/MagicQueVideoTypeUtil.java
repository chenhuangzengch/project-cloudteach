package net.xuele.cloudteach.constant;

/**
 * MagicQueVideoTypeUtil
 *
 * @author panglx
 * @date on 2015/8/19 0019.
 */
public class MagicQueVideoTypeUtil {

	private static MagicQueVideoTypeUtil instance = new MagicQueVideoTypeUtil();

	public MagicQueVideoTypeUtil() {
	}

	public static MagicQueVideoTypeUtil getInstance() {
		return instance;
	}

	/**
	 * 判断是否pc端类型
	 * @param extension
	 * @return
	 */
	public Integer getIsPcType(String extension) {
		return MagicQueVideoType.getIsPcType(extension.toLowerCase());
	}

	/**
	 * 判断是否手机端类型
	 * @param extension
	 * @return
	 */
	public Integer getIsMobileType(String extension) {
		return MagicQueVideoType.getIsMobileType(extension.toLowerCase());
	}

}
