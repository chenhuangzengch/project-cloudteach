package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

public class TeacherWorkItemViewDTO extends TeacherWorkItemDTO implements Serializable {

    private static final long serialVersionUID = 3011091814703494461L;

    List<BankItemFilesViewDTO> itemFilesList;

    public List<BankItemFilesViewDTO> getItemFilesList() {
        return itemFilesList;
    }

    public void setItemFilesList(List<BankItemFilesViewDTO> itemFilesList) {
        this.itemFilesList = itemFilesList;
    }

    @Override
    public String toString() {
        return "TeacherWorkItemViewDTO{" +
                "itemFilesList=" + itemFilesList +
                '}';
    }
}