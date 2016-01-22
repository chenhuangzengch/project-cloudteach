package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * GuidanceWorkItemViewDTO
 * 预习作业题目VIEW
 * @author duzg
 * @date 2015/7/17 0002
 */
public class GuidanceWorkItemViewDTO extends GuidanceWorkItemDTO implements Serializable {

    private static final long serialVersionUID = 6300314382977193720L;

    List<GuidanceItemFilesViewDTO> itemFilesList;

    public List<GuidanceItemFilesViewDTO> getItemFilesList() {
        return itemFilesList;
    }

    public void setItemFilesList(List<GuidanceItemFilesViewDTO> itemFilesList) {
        this.itemFilesList = itemFilesList;
    }

    @Override
    public String toString() {
        return "GuidanceWorkItemViewDTO{" +
                "itemFilesList=" + itemFilesList +
                '}';
    }
}