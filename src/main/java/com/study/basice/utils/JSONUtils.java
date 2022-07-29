package com.study.basice.utils;

import com.alibaba.fastjson.JSONArray;
import com.study.basice.entity.DictionaryEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * JSONU 接口类
 */
public class JSONUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JSONUtils.class);

    public static String writeValueAsString(Object obj) {
        if (obj == null) {
            return null;
        }
        String result;
        try {
            result = new ObjectMapper().writeValueAsString(obj);
            return result;
        } catch (JsonProcessingException e) {
            LOG.warn("JSON转换失败！obj:" + obj.toString(), e);
            return null;
        }
    }

    public static <T> T readObject(String jsonString, Class<T> valueType) {
        if (jsonString == null) {
            return null;
        }
        T result;
        try {
            result = new ObjectMapper().readValue(jsonString, valueType);
        } catch (IOException e) {
            LOG.warn("JSON字符串解析报错！json:" + jsonString, e);
            result = null;
        }
        return result;
    }

    public static <T> List<T> readArray(String jsonString, Class<T> elementClasses) {
        if (jsonString == null) {
            return null;
        }
        List<T> result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, elementClasses);
            result = mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            LOG.warn("JSON字符串解析报错！json:" + jsonString, e);
            result = null;
        }
        return result;
    }

    /**
     * 读取本地json文件
     *
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String s = readJsonFile("D:\\workspace\\chinese-xinhua\\data\\word2.json");
        JSONArray jsonArray = JSONArray.parseArray(s);
        List<DictionaryEntity> dictionaryList = jsonArray.toJavaList(DictionaryEntity.class);
        for(DictionaryEntity entity :dictionaryList){
            System.out.println("数组第一项：" + entity.toString());
        }
    }
}
