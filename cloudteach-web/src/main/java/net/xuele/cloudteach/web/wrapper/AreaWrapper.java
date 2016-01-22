package net.xuele.cloudteach.web.wrapper;

/**
 * AreaWrapper
 *
 * @author sunxh
 * @date 2015/7/9 0009
 */
public class AreaWrapper {

    /**
     * 地区编号
     */
    private String areaId;

    /**
     * 地区名称
     */
    private String name;

    public AreaWrapper(String areaId, String name) {
        this.areaId = areaId;
        this.name = name;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
