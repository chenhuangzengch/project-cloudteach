package net.xuele.cloudteach.view;

/**
 * SRResourceStatisticsNumView
 * 区划下的资源分类统计数量
 * @author duzg
 * @date on 2015/8/5.
 */
public class SRResourceStatisticsNumView{

    /**
     * 类型号
     */
    private Integer typeCode;

    /**
     * 对应资源数
     */
    private Integer typeNum;

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(Integer typeNum) {
        this.typeNum = typeNum;
    }
}
