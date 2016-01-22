package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtTeacherWorkItem;

import java.util.List;

public class TeacherWorkItemView extends CtTeacherWorkItem {
    List<BankItemFilesView> itemFilesList;

    public List<BankItemFilesView> getItemFilesList() {
        return itemFilesList;
    }

    public void setItemFilesList(List<BankItemFilesView> itemFilesList) {
        this.itemFilesList = itemFilesList;
    }
}