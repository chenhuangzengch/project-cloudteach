package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yong.zhang on 2015/8/19 0019.
 */
public class SRCloudDiskExcelDTO<T> implements Serializable{

    private static final long serialVersionUID = -4147103112952081179L;

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
