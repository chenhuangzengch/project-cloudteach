drop table if exists ct_cloud_disk;

/*==============================================================*/
/* Table: ct_cloud_disk                                         */
/*==============================================================*/
create table ct_cloud_disk
(
   disk_id              varchar(32) not null comment '文件编号，老数据库为自增ID，新库可以使用uuid',
   creator              varchar(32) comment '文件用户编号，老数据库为学校ID+编号',
   user_id              varchar(32) comment '文件所属用户id，如果为创建者，所属用户id=创建者id',
   unit_id              varchar(32) comment '课程编号',
   file_pk              varchar(32) comment '文件唯一性编号',
   file_uri             varchar(64) not null comment 'HDFS文件uri',
   file_type            int comment '云盘文件资源：1其他,2,教案3学案4课件5习题6课程素材',
   name                 varchar(64) comment '文件名',
   description               varchar(512) comment '简介',
   extension            varchar(8) comment '扩展名',
   ext_type             int comment '扩展名类型(0其他,1ppt,2word,3视频,4录音,5图片)',
   ext_icon_type        varchar(16) comment '扩展名对应图标',
   size                 int comment '文件大小',
   add_time             datetime comment '添加时间',
   update_time          datetime comment '修改时间',
   sticky_status        int comment '用户文件的置顶状态 0：不置顶 1：置顶',
   share_status         int comment '分享状态:ShareFail = 3,分享审核不通过;Nomal = 0,正常可分享;Share = 1,审核中; Success = 2审核成功 NotShare = -5,        不可分享   Nomal = 0,              正常可分享  Share = 5,                已经分享 Success = 10            审核成功',
   share_time           datetime comment '最后一次分享的更新',
   audit_instructions   varchar(256) comment '审核说明',
   collect_status       int comment '0：非收藏 1：收藏',
   pid                  varchar(32) comment '如果是收藏来的资源，也会在这张表插入一条记录，但是UFID不为空，表示源文件的ID',
   status               int comment '1，有效，0，无效',
   primary key (disk_id)
);


drop table if exists ct_cloud_disk_share;

/*==============================================================*/
/* Table: ct_cloud_disk_share                                   */
/*==============================================================*/
create table ct_cloud_disk_share
(
   share_id             varchar(32) not null comment '分享id',
   disk_id              varchar(32) not null comment '云盘编号',
   user_id              varchar(32) not null comment '分享用户编号',
   unit_id              varchar(32) comment '课程编号',
   file_pk              varchar(32) comment '文件MD5值',
   file_uri             varchar(64) not null comment 'HDFS文件key',
   file_type            int comment '文件类型',
   name                 varchar(64) comment '文件名',
   description               varchar(512) comment '简介',
   extention            varchar(8) comment '扩展名',
   ext_type             int comment '扩展名类型(0其他,1ppt,2word,3视频,4录音,5图片)',
   ext_icon_type        varchar(16) comment '扩展名对应图标',
   size                 int comment '文件大小',
   add_time             datetime comment '添加时间',
   upate_time           datetime comment '修改时间',
   boutique             int comment '运维人员对分享资源进行加精(置顶)',
   share_time           datetime comment '分享时间',
   audit_instructions   varchar(256) comment '审核原因',
   share_type           int comment 'ALL = 0,                所有人SCHOOL = 1,        学校 AREA = 2              地区 ',
   area_id              varchar(32) comment '地区编号',
   school_id            varchar(32) comment '学校id',
   status               int comment '分享状态:ShareFail = 3,分享审核不通过;Nomal = 0,正常可分享;Share = 1,审核中; Success = 2审核成功',
   primary key (share_id)
);

drop table if exists ct_disk_share_praise;

/*==============================================================*/
/* Table: ct_disk_share_praise                                  */
/*==============================================================*/
create table ct_disk_share_praise
(
   share_id             varchar(32) not null comment '文件编号',
   user_id              varchar(32) not null comment '点赞用户',
   status               int not null comment '点赞状态',
   primary key (share_id, user_id)
);



drop table if exists ct_cloud_disk_share_statistics;

/*==============================================================*/
/* Table: ct_cloud_disk_share_statistics                        */
/*==============================================================*/
create table ct_cloud_disk_share_statistics
(
   share_id             varchar(32) not null comment '文件编号',
   download_times       int comment '下载数',
   save_times           int comment '收藏数',
   read_times           int comment '浏览数',
   praise_times         int comment '点赞总数',
   primary key (share_id)
);

drop table if exists ct_disk_downloads;

/*==============================================================*/
/* Table: ct_disk_downloads                                     */
/*==============================================================*/
create table ct_disk_downloads
(
   download_id          varchar(32) not null comment '下载记录ID',
   file_id              varchar(32) not null comment '文件编号',
   user_id              varchar(32) not null comment '下载用户',
   download_time        datetime comment '下载时间',
   primary key (download_id)
);

