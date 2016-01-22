package net.xuele.cloudteach.web.wrapper;

/**
 * Created by Administrator on 2015/6/18 0018.
 * 包装一切保存，修改的返回值
 */
public class ModifyWrapper {

    private int state;   //修改状态

    private int id;     //修改编号

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
