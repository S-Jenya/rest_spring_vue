package com.stp.rest_spring_vue.Controller;

import com.stp.rest_spring_vue.model.Card;
import com.stp.rest_spring_vue.model.Institution;
import com.stp.rest_spring_vue.model.User;
import com.stp.rest_spring_vue.service.CardService;
import com.stp.rest_spring_vue.service.InstitutionService;
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
public class InstitutionController {

    private final InstitutionService institutionService;
    private final CardService cardService;

    @Autowired
    public InstitutionController(InstitutionService institutionService, CardService cardService) {
        this.institutionService = institutionService;
        this.cardService = cardService;
    }


//    @GetMapping("/inst-create/{id}")
//    public String createCardForm(@PathVariable("id") Long id, Model model){
//        Card card = cardService.findById(id);
//        model.addAttribute("card", card);
//        return "inst-create";
//    }

    @RequestMapping(value="/create-inst/confirmed", method= RequestMethod.POST)
    public String updateCard(@RequestBody String data){
        String temp = null;
        try{
            temp = URLDecoder.decode(data, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Long id = Long.parseLong(temp.split(";", 2)[0]);
        String name = temp.split(";", 2)[1];

        Card card = cardService.findCardById(id);

        if(card.findInListByInstName(card, name)){
            return "redirect:user";
        }

        Institution inst = institutionService.selectInstByName(name);
        if(inst != null) {  /*уже существует*/
            card.addNewInst(inst);
        } else {
            Institution newInst = new Institution();
            newInst.setName(name);
            card.addNewInst(newInst);
        }

        cardService.saveCard(card);
        return "redirect:user";
    }


    @GetMapping("/inst-update/{id_card}/{id_inst}/getInfo")
    public Institution getInstForm(@PathVariable("id_card") Long id_card,
                                   @PathVariable("id_inst") Long id_inst) {
        Institution institution = institutionService.selectInstByName(id_inst);
        return institution;
    }

    @GetMapping("/inst-update/{id}")
    public String updateInstForm(@PathVariable("id") Long id,
                                 @RequestParam("old_name") String oldName,
                                 Model model){
        Card card = cardService.findById(id);
        model.addAttribute("card", card);
        model.addAttribute("inst", oldName);
        return "inst-update";
    }

    @RequestMapping(value="/update-inst/confirmed", method = RequestMethod.POST)
    public String updateInst(@RequestBody String data){
        String temp = null;
        try{
            temp = URLDecoder.decode(data, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Long id = Long.parseLong(temp.split(";", 3)[0]);
        String oldName = temp.split(";", 3)[1];
        String name = temp.split(";", 3)[2];

        Card card = cardService.findCardById(id);

        if(card.findInListByInstName(card, name)){
            return "redirect:user";
        }

        /* Удаляем из коллекции старое имя */
        for(Institution institution: card.getInstitutions()){
            if(institution.getName().equals(oldName)){
                card.getInstitutions().remove(institution);
                break;
            }
        }

        /* Новое имя уже существует? */
        Institution inst = institutionService.selectInstByName(name);
        if(inst != null) {  /*уже существует*/
            card.addNewInst(inst);
        } else { /* имя не существует. Создадим новый объект */
            Institution newInst = new Institution();
            newInst.setName(name);
            card.addNewInst(newInst);
        }
        cardService.saveCard(card);

        inst = institutionService.selectInstByName(oldName);
        if(inst.getCards().size() == 0){
            institutionService.deleteById(inst.getId());
        }

        return "redirect:user";
    }

    @GetMapping("/inst-del/{id}/{name}")
    public void deleteInst(@PathVariable("id") Long id, @PathVariable("name") String name){
        Card card = cardService.findCardById(id);

        /* Удаляем из коллекции старок имя */
        for(Institution institution: card.getInstitutions()){
            if(institution.getName().equals(name)){
                card.getInstitutions().remove(institution);
                break;
            }
        }
        cardService.saveCard(card);

        Institution inst = institutionService.selectInstByName(name);
        if(inst.getCards().size() == 0){
            institutionService.deleteById(inst.getId());
        }
    }

}
