package com.stp.rest_spring_vue.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stp.rest_spring_vue.model.Card;
import com.stp.rest_spring_vue.model.Institution;
import com.stp.rest_spring_vue.model.User;
import com.stp.rest_spring_vue.service.CardService;
import com.stp.rest_spring_vue.service.InstitutionService;
import com.stp.rest_spring_vue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CardController {

    private final CardService cardService;
    private final UserService userService;
    private final InstitutionService institutionService;

    @Autowired
    public CardController(CardService cardService, ObjectMapper mapper, UserService userService, InstitutionService institutionService) {
        this.cardService = cardService;
        this.userService = userService;
        this.institutionService = institutionService;
    }

    @RequestMapping(value="/new-card/confirmed", method = RequestMethod.POST)
    public void createNewCard(@RequestBody String data){
        String temp = null;
        try{
            temp = URLDecoder.decode(data, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Long id = Long.parseLong(temp.split(";", 2)[0]);
        String name = temp.split(";", 2)[1];

        User user = userService.getUserInfo(id);
        Card card = new Card();
        card.setHeadline(name);
        card.setUser(user);
        cardService.saveCard(card);
    }

    @GetMapping("/card-update/{id_card}")
    public Card updateCardForm(@PathVariable("id_card") Long id){
        Card card = cardService.findCardById(id);
        return card;
    }

    @RequestMapping(value="/update-card/confirmed", method = RequestMethod.POST)
    public String updateCard(@RequestBody String data){
        String temp = null;
        try{
            temp = URLDecoder.decode(data, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Long id = Long.parseLong(temp.split(";", 2)[0]);
        String name = temp.split(";", 2)[1];

        cardService.updCard(name, id);
        return "redirect:user";
    }

    @GetMapping(value="/del-card/{idCard}")
    public String DelCard(@PathVariable("idCard") Long id){
        Card card = cardService.findCardById(id);
        List<String> instNameInCard = new ArrayList<String>();

        for(Institution institution: card.getInstitutions()){
            instNameInCard.add(institution.getName());
        }

        card.cleanInstList();
        cardService.saveCard(card);
        cardService.deleteById(id);

        /* Выполним очистку из таблици Институты */
        for (String oldInstName: instNameInCard){
            Institution inst = institutionService.selectInstByName(oldInstName);
            if(inst.getCards().size() == 0){
                institutionService.deleteById(inst.getId());
            }
        }

        return "redirect:user";
    }


}
