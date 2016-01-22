package net.xuele.cloudteach.service.common;

import net.xuele.cloudteach.service.CloudTeachService;

import java.util.List;

/**
 * Created by cm.wang on 2015/9/8 0008.
 */
public class SendNotify implements Runnable{

    public CloudTeachService cloudTeachService;

    public List<String> studentIdList;

    public String title;

    public String content;

    public String userId;

    public String userIcon;

    public SendNotify(CloudTeachService cloudTeachService, List<String> studentIdList, String title, String content, String userId, String userIcon) {
        this.cloudTeachService = cloudTeachService;
        this.studentIdList = studentIdList;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.userIcon = userIcon;
    }

    @Override
    public void run() {
        cloudTeachService.sendWarnSubNotify(studentIdList, title, content, userId, userIcon);
    }
}
