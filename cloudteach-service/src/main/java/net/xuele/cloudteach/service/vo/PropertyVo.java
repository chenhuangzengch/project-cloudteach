package net.xuele.cloudteach.service.vo;

/**
 * 课件内容中的文件属性对象
 */
public class PropertyVo {
    //type=1-5
    private String fileType;
    private String from;
    private String code;
    private String width;
    private String ex;
    private String height;
    private String studentName;
    //type=6
    private Integer size;
    private Long color;
    private Boolean bold;
    private Boolean underline;
    private Boolean italic;


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getColor() {
        return color;
    }

    public void setColor(Long color) {
        this.color = color;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public Boolean getUnderline() {
        return underline;
    }

    public void setUnderline(Boolean underline) {
        this.underline = underline;
    }

    public Boolean getItalic() {
        return italic;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        /*StringBuilder dataStr=new StringBuilder();
//        dataStr.append("<property>");
        dataStr.append("<code>").append(code).append("</code>");
        dataStr.append("<ex>").append(ex).append("</ex>");
        dataStr.append("<from>").append(from).append("</from>");
        dataStr.append("<studentName>").append(studentName).append("</studentName>");
        dataStr.append("<fileType>").append(fileType).append("</fileType>");
        dataStr.append("<width>").append(width).append("</width>");
        dataStr.append("<height>").append(height).append("</height>");
//        dataStr.append("</property>");*/
        return "<![CDATA[<code>dfdasfd</code>]]>";
    }
}