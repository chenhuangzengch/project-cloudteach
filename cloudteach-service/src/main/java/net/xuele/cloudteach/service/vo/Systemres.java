package net.xuele.cloudteach.service.vo;

import java.util.List;

/**
 * Systemres
 *
 * @author sunxh
 * @date 15/10/25
 */
public class Systemres {

    private int status;
    private List<SystemResources> resources;


    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


    public void setResources(List<SystemResources> resources) {
        this.resources = resources;
    }

    public List<SystemResources> getResources() {
        return resources;
    }

}
