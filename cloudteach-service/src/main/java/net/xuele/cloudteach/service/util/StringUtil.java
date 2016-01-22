package net.xuele.cloudteach.service.util;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.Magic2StuAnsJsonDTO;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by duzg on 2015/8/4 0001.
 */
public class StringUtil {

    /**
     * 获取字符串长度，中文算两个字长，其他算一个
     *
     * @param context
     * @return int
     */
    public static int Stringlength(String context) {
        if (context == null) {
            return 0;
        }
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < context.length(); i++) {
            char c = context.charAt(i);
            if (c >= 19968 && c <= 171941) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 获取指定长度的描述内容
     *
     * @param context
     * @param opType  0不截取,1截取
     * @return String
     */
    public static String getFixedLengthContext(String context, int opType) {
        if (opType == 1) {
            if (context == null) {
                return null;
            } else if (context.length() < 60) {
                return context;
            }
            return context.substring(0, Constants.MAX_LIST_CONTEXT_LENGTH);
        } else {
            return context;
        }
    }

    /**
     * 验证描述内容有没有超过规定长度
     *
     * @param context
     * @return boolean
     */
    public static boolean checkContextLength(String context) {
        if (context == null) {
            return true;
        }
        return Stringlength(context) > Constants.MAX_CONTEXT_LENGTH ? false : true;
    }

    /**
     * 验证评论内容有没有超过规定长度
     *
     * @param comment
     * @return boolean 没超过返回true,超过返回false
     */
    public static boolean checkCommentLength(String comment) {
        if (StringUtils.isEmpty(comment)) {
            return true;
        }
        return Stringlength(comment) > Constants.MAX_COMMENT_LENGTH ? false : true;
    }

    /**
     * 将口语作业内容格式化为评语评测所需格式
     *
     * @param content
     * @return
     */
    public static String formatVoiceContent(String content) {
        if (StringUtils.isEmpty(content)) {
            return "";
        }
        Pattern pattern = Pattern.compile("[^0-9a-zA-Z]+");
        Matcher matcher = pattern.matcher(content);
        return matcher.replaceAll(" ");
    }

    /**
     * 保留几位小数
     *
     * @param num
     * @return
     */
    public static Double getDecimal(Double num, int n) {
        BigDecimal b = new BigDecimal(num);
        double value = b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
        return value;
    }

    /**
     * 过滤文件名中的非法字符[只允许中文英文数字下划线]
     * 并截取到规定的长度
     * @param fileName  文件名
     * @param maxLength 长度
     * @return fileName 过滤、截取后的文件名
     */
    public static String fileNameSubstring(String fileName, int maxLength) {
        if (StringUtils.isEmpty(fileName)) {
            return fileName;
        }

        /** 过滤非法字符 */
        fileName = cleanFileName(fileName);

        if (fileName.length() > maxLength) {
            fileName = fileName.substring(0, maxLength) + "……";
        }
        return fileName;
    }

    /**
     * 过滤非法字符
     *      只允许中文英文数字下划线
     * @param name  文件名
     * @return name 过滤后的文件名
     */
    public static String cleanFileName(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        name = name.replaceAll("[^a-zA-Z0-9_\\u4e00-\\u9fa5《》\"“”\\.\\s]", "");

        return name;
    }

    /**
     * 获得uuid
     * @return
     */
    public static String getUUID() {
        final String specialChar = "-";
        return UUID.randomUUID().toString().replace(specialChar, org.apache.commons.lang3.StringUtils.EMPTY);

    }
    /**
     * 全角转半角
     * @param input String.
     * @return 半角字符串
     */
    public static String toDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        String returnString = new String(c);
        //横线转减号（负号）
        returnString = returnString.replaceAll("—","-");
        return returnString;
    }

    /**
     * 半角转全角
     * @param input String.
     * @return 全角字符串.
     */
    public static String toSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    /**
     * 获取随机数
     * @param n
     * @return
     */
    public static int[] getRandomNum(int n,int maxSort){
        int[] sorts = new int[n];
        Random ra =new Random();
        for (int i=0;i<n;i++){
            sorts[i] = ra.nextInt(maxSort);
        }
        return sorts;
    }

    /**
     * 从0~num随机中取出n个不重复的数字
     * @param n
     * @param num
     * @return
     */
    public static List<Integer> getRandomRow(int n,int num){
        //定义一个HashSet集合，用来存储随机数。利用Set集合中元素唯一性来使随机数不重复
        List<Integer> rows=new ArrayList<>();
        //使用for循环来获取随机数
        boolean[]  bool = new boolean[num];
        int row= 0;
        for (int i = 0; rows.size()< n; i++) {
            //创建一个随机数生成器。
            Random rd1=new Random();
            //调用Random类中的nextInt()方法来获取指定范围内的随机数。（0~y）
            do {
                row = rd1.nextInt(num);
            }while (bool[row]);
            bool[row] = true;
            rows.add(row);
        }
        return rows;
    }
    public static void main(String[] args) {
        //System.out.println("==========="+getUUID());
        //System.out.println("==========="+getRandomRow(10,10).toString());
        String ansQueJSON = "[{\"queId\":\"3cd7267c721911e58bfe005056a72866\",\"cs\":\"123\",\"parentId\":\"\",\"type\":\"11\",\"queTime\":\"1213\",\"aIds\":[\"1213\"]}]";
        List<Magic2StuAnsJsonDTO> magic2StuAnsJsonDTOs = JsonUtil.getModifiedMagic2AnsInfo(ansQueJSON);
        System.out.println("==========="+magic2StuAnsJsonDTOs.toArray());
//        String str = "<script>alert('abc')</script>";
//        System.out.println(StringEscapeUtils.escapeHtml(str));
//        System.out.println(StringEscapeUtils.escapeJavaScript(str));
//        System.out.println(StringEscapeUtils.escapeSql(str));
    }
}
