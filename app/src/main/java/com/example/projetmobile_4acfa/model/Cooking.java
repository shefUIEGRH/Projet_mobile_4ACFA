package com.example.projetmobile_4acfa.model;

public class Cooking {

    private String nom;
    private String categorie;
    private String temps;
    private String personnes;
    private String difficult;
    private String photo;

    public String getName(){
        return nom;
    }

    public void setName(String name) {
        this.nom = name;
    }

    public String getCategory(){
        return categorie;
    }

    public void setCategory(String category) {
        this.categorie = category;
    }

    public String getTime(){
        return temps;
    }

    public void setTime(String time) {
        this.temps = time;
    }

    public String getPerson(){
        return personnes;
    }

    public void setPerson(String p) {
        this.personnes = p;
    }

    public String getDiff(){
        return difficult;
    }

    public void setDiff(String diff) {
        this.difficult = diff;
    }

    public String getImg(){
        return photo;
    }

    public void setImg(String img) {
        this.photo = img;
    }
}
