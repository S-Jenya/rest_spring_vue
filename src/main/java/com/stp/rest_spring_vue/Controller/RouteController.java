package com.stp.rest_spring_vue.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RouteController {

    @GetMapping("/")
    public String createIndexForm(){
        return "index";
    }

    @GetMapping("/user")
    public String findAllUsers(){
        return "user-list";
    }

    @GetMapping("/myPage")
    public String goToUserPage(){
        return "user-page";
    }

    @GetMapping("/user-create")
    public String createUserForm(){
        return "user-create";
    }

    @GetMapping("/user-update/{id_user}")
    public String updateUserForm(@PathVariable("id_user") Long id){
        return "user-update";
    }

    @GetMapping("/user-info/{id_user}")
    public String getCardForm(@PathVariable("id_user") Long id){
        return "user-info";
    }

    @GetMapping("/inst-create/{id}")
    public String createInstForm(){
        return "inst-create";
    }

    @GetMapping("/inst-update-page/{id_card}/{id}")
    public String createInstUpdForm(@PathVariable("id_card") String id_card,
                                    @PathVariable("id") String id_inst){
        return "inst-update";
    }

    @GetMapping("/card-create-page/{id_user}")
    public String getCreateCardForm(@PathVariable("id_user") Long id){
        return "card-create";
    }

    @GetMapping("/card-update-page/{id_card}")
    public String createCardUpdForm(@PathVariable("id_card") Long id_card){
        return "card-update";
    }

}
