package com.mkyong.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "BANK")
public class Bank {

    @Id
    @GenericGenerator(
            name = "BANK_ID_SEQ",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "BANK_ID_SEQ"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BANK_ID_SEQ")
    private Long id = -1L;

    @Column(name = "UNIQUE_URL")
    private String uniqueUrl;

    @Column(name = "TITLE")
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueUrl() {
        return uniqueUrl;
    }

    public void setUniqueUrl(String uniqueUrl) {
        this.uniqueUrl = uniqueUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", uniqueUrl='" + uniqueUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
