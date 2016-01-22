package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * Created by Administrator on 2015/7/30 0030.
 */
public class CloudDiskResponseWrapper {
    private String state;
    private List<CloudDiskResponse> resources;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<CloudDiskResponse> getResources() {
        return resources;
    }

    public void setResources(List<CloudDiskResponse> resources) {
        this.resources = resources;
    }
}
