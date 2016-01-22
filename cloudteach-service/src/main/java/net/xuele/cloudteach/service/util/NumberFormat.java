package net.xuele.cloudteach.service.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字计算工具类
 * Created by duzg on 2015/7/21 0002.
 */
public class NumberFormat {

    /**
     * 给参数返回指定小数点后 a 位的四舍五入
     * @param sourceData 要取舍的原数据
     * @param a 小数点 后的 位数（如：10：小数点后1位；100：小数据后2位以此类推）
     * @return 舍取后的 数据
     */
    public static float getFloatRound(double sourceData,int a) {
        double result = 0;
        double temp = Math.pow(10, a);
        result = Math.round(sourceData*temp)/temp;
        return (float)result;
    }

    /**
     * 数字年级转成中文年级
     * @param grade 数字年级
     * @return 中文年级
     */
    public static String getChineseGrade(int grade) {
        String chineseGrade = "";
        switch (grade){
            case 1:
                chineseGrade = "一年级";
                break;
            case 2:
                chineseGrade = "二年级";
                break;
            case 3:
                chineseGrade = "三年级";
                break;
            case 4:
                chineseGrade = "四年级";
                break;
            case 5:
                chineseGrade = "五年级";
                break;
            case 6:
                chineseGrade = "六年级";
                break;
            case 7:
                chineseGrade = "七年级";
                break;
            case 8:
                chineseGrade = "八年级";
                break;
            case 9:
                chineseGrade = "九年级";
                break;
            case 10:
                chineseGrade = "十年级";
                break;
            case 11:
                chineseGrade = "十一年级";
                break;
            case 12:
                chineseGrade = "十二年级";
                break;
        }
        return chineseGrade;
    }

    /**
     * 整数除法转百分数
     * @param a
     * @param b
     * @param n
     * @return
     */
    public static String divide(int a,int b,String n) {
        BigDecimal b1 = new BigDecimal(a);

        BigDecimal b2 = new BigDecimal(b);

        DecimalFormat df = new DecimalFormat(n);
        return df.format(b1.divide(b2, 4,BigDecimal.ROUND_HALF_EVEN).doubleValue());
    }
    public static void main(String[] args) {
        System.out.println(NumberFormat.getFloatRound(100.0 * 2 / 55, 0));
        System.out.println(NumberFormat.getFloatRound(3.636, 1));
        System.out.println(NumberFormat.divide(2, 3,"0%"));


    }
}
