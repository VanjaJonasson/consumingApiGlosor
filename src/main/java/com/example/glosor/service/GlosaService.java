package com.example.glosor.service;

import com.example.glosor.entities.Category;
import com.example.glosor.entities.Glosa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GlosaService {

    @Autowired
    private RestTemplate restTemplate;

    public List getAllGlosor() {
        List<Glosa> glosor = restTemplate.getForObject("http://localhost:8081/glosor", ArrayList.class);
        return glosor;
    }


    public void deleteGlosa(Integer id){
        restTemplate.delete("http://localhost:8081/glosa/" + id);
    }

    public Optional<Glosa> glosaToEdit(int id){
        Glosa glosaToEdit = restTemplate.getForObject("http://localhost:8081/glosa/" + id, Glosa.class);
        return Optional.ofNullable(glosaToEdit);
    }

    public Glosa getGlosaInCat(int cat){
        Glosa englosa = restTemplate.getForObject("http://localhost:8081/cat/" + cat, Glosa.class);
        return englosa;
    }

   public boolean checkAnswer(Glosa glosa, String answer){
        if (glosa.getEng().equalsIgnoreCase(answer)) {
            return true;
        } else {
            return false;
        }
    }

    public String wrongAnswer(Glosa englosa, String answer){
        String a = englosa.capitalizeFirstLetter(englosa.getSwe()) + " = " + englosa.getEng() + ". \nDu svarade: " + answer + ".";
        return a;
    }

    public String getCatName(int i) {
        Glosa catName = restTemplate.getForObject("http://localhost:8081/glosa/" + i, Glosa.class);
        String name = String.valueOf(catName.getCategory());
        return name;
    }

    /* doesn´t work after redoing apiGlosor
    public List getAllGlosorInCat(int num) {
        List<Glosa> glosorInCat = restTemplate.getForObject("http://localhost:8081/glosorincat?num=/" + num, ArrayList.class);
        return glosorInCat;
    }
    */


    public List<Category> getCategories() {
        List<Category> categories = restTemplate.getForObject("http://localhost:8081/categories", ArrayList.class);
        return categories;
    }

    public void save(Glosa glosa, String category) {
        if (glosa.isNew()) {
            restTemplate.postForObject("http://localhost:8081/glosa/" + category, glosa, Glosa.class);
        }
        else {
            restTemplate.put("http://localhost:8081/glosa/" + category, glosa, Glosa.class);
        }
    }
}
