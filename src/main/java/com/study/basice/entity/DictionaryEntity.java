package com.study.basice.entity;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * 新华字典
 *
 * @author zhaoyk
 * @date 2022/5/17
 **/
@Entity
@Table(name = "basice_dictionary")
public class DictionaryEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid", unique = true, nullable = false)
    private Long id;

    /**
     * 汉字
     */
    @Column(name = "words", length = 8, nullable = false)
    private String words;

    /**
     * 繁体字
     */
    @Column(name = "oldword", length = 8, nullable = false)
    private String oldword;

    /**
     * 笔画数
     */
    @Column(name = "strokes", length = 4, nullable = false)
    private int strokes;

    /**
     * 拼音
     */
    @Column(name = "pinyin", length = 32, nullable = false)
    private String pinyin;

    @Column(name = "pinyin4j", length = 32, nullable = false)
    private String pinyin4j;

    /**
     * 部首
     */
    @Column(name = "radicals", length = 4, nullable = false)
    private String radicals;

    /**
     * 解释
     */
    @Column(name = "explanation", length = 512, nullable = false)
    private String explanation;

    /**
     * 更多解释
     */
    @Column(name = "more", length = 1024, nullable = false)
    private String more;

    /**
     * 是否启用(0否1是)
     */
    @Column(name = "it_enabled", nullable = false)
    private Integer enabled = 1;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "post_date", length = 20, updatable = false)
    private Date postDate;

    /**
     * 最后修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", length = 20)
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getOldword() {
        return oldword;
    }

    public void setOldword(String oldword) {
        this.oldword = oldword;
    }

    public int getStrokes() {
        return strokes;
    }

    public void setStrokes(int strokes) {
        this.strokes = strokes;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPinyin4j() {
        return pinyin4j;
    }

    public void setPinyin4j(String pinyin4j) {
        this.pinyin4j = pinyin4j;
    }

    public String getRadicals() {
        return radicals;
    }

    public void setRadicals(String radicals) {
        this.radicals = radicals;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
