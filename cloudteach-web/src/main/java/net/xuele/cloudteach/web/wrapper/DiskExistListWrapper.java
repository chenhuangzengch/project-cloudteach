package net.xuele.cloudteach.web.wrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DiskExistListWrapper
 *
 * @author sunxh
 * @date 15/8/18
 */
public class DiskExistListWrapper {
    private List<DiskExistWrapper> data;

    public List<DiskExistWrapper> getData() {
        return data;
    }

    public void setData(List<DiskExistWrapper> data) {
        this.data = data;
    }

    public Map<String,Boolean> getMap(){
        Map<String,Boolean> map = new HashMap<>();

        for (DiskExistWrapper diskExistWrapper : data) {
            map.put(diskExistWrapper.getFileKey(),diskExistWrapper.getExist());
        }

        return map;
    }
}
