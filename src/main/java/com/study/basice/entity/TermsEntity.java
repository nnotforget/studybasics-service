package com.study.basice.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * xxxxx此处为类的描述信息
 *
 * @author zhaoyk
 * @date 2022/5/20
 **/
@Entity
@Table(name = "basice_terms")
public class TermsEntity {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid", unique = true, nullable = false)
    private Long id;

    /**
     * 词
     */
    @Column(name = "words", length = 12, nullable = false)
    private String words;

    /**
     * 解释
     */
    @Column(name = "explanation", length = 512, nullable = false)
    private String explanation;

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

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
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
