package com.example.glosor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Glosa {

    private Integer id;
    private String eng;
    private String swe;

    private Category category;

    public Glosa() {
    }

    public Glosa(Integer id, String eng, String swe) {
        this.id = id;
        this.eng = eng;
        this.swe = swe;
    }

    public String capitalizeFirstLetter(@NonNull String str){
        return str.length() == 0 ? str
                : str.length() == 1 ? str.toUpperCase()
                : str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getSwe() {
        return swe;
    }

    public void setSwe(String swe) {
        this.swe = swe;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isNew() {
        return this.id == null;
    }


    @Override
    public String toString() {
        return "Glosa{" +
                "id=" + id +
                ", eng='" + eng + '\'' +
                ", swe='" + swe + '\'' +
                ", category=" + category +
                '}';
    }
}
