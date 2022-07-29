package com.study.basice.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 成语
 *
 * @author zhaoyk
 * @date 2022/5/18
 **/
@Entity
@Table(name = "basice_idiom")
public class IdiomEntity {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid", unique = true, nullable = false)
    private Long id;

    /**
     * 成语
     */
    @Column(name = "words", length = 12, nullable = false)
    private String words;

    /**
     * 拼音
     */
    @Column(name = "pinyin", length = 128, nullable = false)
    private String pinyin;

    /**
     * 缩写
     */
    @Column(name = "abbreviation", length = 8)
    private String abbreviation;

    /**
     * 解释
     */
    @Column(name = "explanation", length = 512, nullable = false)
    private String explanation;

    /**
     * 例子
     */
    @Column(name = "example", length = 512, nullable = false)
    private String example;

    /**
     * 起源，由来
     */
    @Column(name = "derivation", length = 512, nullable = false)
    private String derivation;

    /**
     * 近义词
     */
    @Column(name = "near_words", length = 512, nullable = false)
    private String nearWords;

    /**
     * 反义词
     */
    @Column(name = "antisense_words", length = 512, nullable = false)
    private String antisenseWords;

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

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getDerivation() {
        return derivation;
    }

    public void setDerivation(String derivation) {
        this.derivation = derivation;
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

    public String getNearWords() {
        return nearWords;
    }

    public void setNearWords(String nearWords) {
        this.nearWords = nearWords;
    }

    public String getAntisenseWords() {
        return antisenseWords;
    }

    public void setAntisenseWords(String antisenseWords) {
        this.antisenseWords = antisenseWords;
    }
}
