package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/12/1 0001.
 * 学情作业评分等级DTO
 */
public class LearningInfoScoreLevelDTO<T> implements Serializable {
    private static final long serialVersionUID = -2898059991487745997L;

    /* 同步课堂85-100分级别,教师作业A(1)评分等级 */
    private T levelA;
    /* 同步课堂70-84分级别,教师作业B(2)评分等级 */
    private T levelB;
    /* 同步课堂55-69分级别,教师作业C(3)评分等级 */
    private T levelC;
    /* 同步课堂55分以下级别,教师作业D(4)评分等级 */
    private T levelD;

    public T getLevelA() {
        return levelA;
    }

    public void setLevelA(T levelA) {
        this.levelA = levelA;
    }

    public T getLevelB() {
        return levelB;
    }

    public void setLevelB(T levelB) {
        this.levelB = levelB;
    }

    public T getLevelC() {
        return levelC;
    }

    public void setLevelC(T levelC) {
        this.levelC = levelC;
    }

    public T getLevelD() {
        return levelD;
    }

    public void setLevelD(T levelD) {
        this.levelD = levelD;
    }

    @Override
    public String toString() {
        return "LearningInfoScoreLevelDTO{" +
                "levelA=" + levelA +
                ", levelB=" + levelB +
                ", levelC=" + levelC +
                ", levelD=" + levelD +
                '}';
    }
}
