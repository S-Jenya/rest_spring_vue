package com.stp.rest_spring_vue.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "card")
//@ToString(exclude="user")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card")
    private Long id_card;

    private String headline;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Card_institution",
            joinColumns = { @JoinColumn(name = "card_id") },
            inverseJoinColumns = { @JoinColumn(name = "institution_id") }
    )
    private List<Institution> institutions;

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public String getHeadline() {
        return headline;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }

    public void setId_card(Long id_card) {
        this.id_card = id_card;
    }

    public Long getId_card() {
        return id_card;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addNewInst(Institution institution){
        this.institutions.add(institution);
    }

    public void delInstFromList(Institution institution){
        this.institutions.remove(institution);
    }

    public void delInstByIndexFromList(Integer index){
        this.institutions.remove(index);
    }

    public void cleanInstList(){
        this.institutions.clear();
    }

    public boolean findInListByInstName(Card card, String name){
        for(Institution inst: card.getInstitutions()){
            if(inst.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public Integer getIndexInst(Card card, String name){
        Integer index = 0;
        for(Institution inst: card.getInstitutions()){
            if(inst.getName().equals(name)){
                return index;
            }
            index++;
        }
        return index;
    }

    public void setIdNyll() {
        this.id_card = null;
    }

    public String toString() {
        return "Id: " + this.id_card + "; headline: " + this.headline;
    }
}
