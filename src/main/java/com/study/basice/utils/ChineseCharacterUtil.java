package com.study.basice.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @Classname ChineseCharacterUtil
 * @Description TODD
 * @Date 2021/11/22 14:09
 */
public class ChineseCharacterUtil {
    public enum PinyinType {
        ALL, //全拼
        ONE, //第一个字全拼
        FIRST, //首字母
        FIRST_ONE //第一个首字母
    }

    public static final HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

    static {
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
    }

    /**
     * @Description 获取首个汉字简拼
     * @Date 13:40 2022/4/15
     * @Param [str]
     */
    public static String toFirstSpell(String str) {
        return toPinyin(str, PinyinType.FIRST_ONE);
    }

    /**
     * @Description 获取汉字简拼
     * @Date 13:40 2022/4/15
     * @Param [str]
     */
    public static String toSpell(String str) {
        return toPinyin(str, PinyinType.FIRST);
    }

    /**
     * @Description 汉字转拼音
     * @Date 11:02 2022/2/23
     * @Param [str, type]
     */
    public static String toPinyin(String str, PinyinType type) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int len = str.length();
        switch (type) {
            case ONE:
            case FIRST_ONE:
                len = 1;
        }

        for (int i = 0; i < len; ++i) {
            char c = str.charAt(i);
            //判断是否为汉字
            if (0x4E00 <= c && c <= 0x9FFF) {
                try {
                    //转为字符串，多音字有多个
                    String[] strArr = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    Object s = strArr[0].charAt(0);
                    if (type == PinyinType.ALL || type == PinyinType.ONE) {
                        s = strArr[0];
                    }

                    //多音字逐个输出
                    stringBuilder.append(s);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(">>>:" + toPinyin("雦", PinyinType.ALL));
    }
}
