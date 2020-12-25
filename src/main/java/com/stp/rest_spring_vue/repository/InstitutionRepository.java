package com.stp.rest_spring_vue.repository;

import com.stp.rest_spring_vue.model.Card;
import com.stp.rest_spring_vue.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    @Query("select card.institutions from Card card where card.id_card = ?1")
    List<Institution> findInstFromCardCustomQuery(Long cardId);

    @Query("select inst from Institution inst join Card card where card.id_card = ?1")
    List<Institution> selectInstByIdCard(Long userId);

    @Query("select inst from Institution inst where inst.name = ?1")
    Institution selectInstByName(String name);

    @Query("select inst from Institution inst where inst.id = ?1")
    Institution selectInstByNameCustomQuery(Long id);

}
