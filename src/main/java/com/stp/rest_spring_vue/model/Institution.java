package com.stp.rest_spring_vue.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "institution")
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "institution_id")
    private Long id;

    public List<Card> getCards() {
        return cards;
    }

    @Column(unique = true)
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = { CascadeType.ALL },
            fetch = FetchType.EAGER,
            mappedBy = "institutions")
    @JsonIgnore
    private List<Card> cards;

    public Institution() {    }
    public Institution(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String toString() {
        return this.name;
    }
}
