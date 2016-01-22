package net.xuele.cloudteach.service.vo;

import javax.xml.bind.annotation.XmlElement;

/**
 * zhihuan.cai �½��� 2015/10/12 0012.
 */

public class ResourcesVo {

    private String fileType;
    private String ex;
    private String name;
    private String fileCode;

    @XmlElement(name = "type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    @XmlElement(name = "nm")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "code")
    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }
}