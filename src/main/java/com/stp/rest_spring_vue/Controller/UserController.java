package com.stp.rest_spring_vue.Controller;

import com.stp.rest_spring_vue.model.Card;
import com.stp.rest_spring_vue.model.Institution;
import com.stp.rest_spring_vue.model.User;
import com.stp.rest_spring_vue.service.CardService;
import com.stp.rest_spring_vue.service.InstitutionService;
import com.stp.rest_spring_vue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
public class UserController {

    private final UserService userService;
    private final CardService cardService;
    private final InstitutionService institutionService;

    @Autowired
    public UserController(UserService userService, CardService cardService, InstitutionService institutionService) {
        this.userService = userService;
        this.cardService = cardService;
        this.institutionService = institutionService;
    }

    @GetMapping("/user/getUsers")
    public List<User> findAllUsers() {
        List<User> user = userService.customSelect();
        return user;
    }


    @GetMapping(value = "/user-create/{name}/{password}/{role}")
    public String createUser(@PathVariable("name") String name,
                             @PathVariable("password") String password,
                             @PathVariable("role") String role) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setRole(role);
        userService.saveUser(user);
        return "/user";
    }


    @GetMapping("/user-delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        System.out.println(user.getId_user());
        List<Card> cards = new ArrayList<Card>();
        cards = cardService.selectCardByUserId(user.getId_user());

        for (Card card : cards) {
            cardService.deleteById(card.getId_card());
        }
        userService.deleteById(id);
    }

    @GetMapping("/user-info/{id}/get")
    public Map<String, Object> userInfoForm(@PathVariable("id") Long id) {
        User user = userService.getUserInfo(id);
        List<Card> card = cardService.selectCardFromUser(id);
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("card", card);
        return map;
    }

    @GetMapping("/user-update/{id}/getInfo")
    public User updateUserForm(@PathVariable("id") Long id) {
        User user = userService.getUserInfo(id);
        return user;
    }

    @RequestMapping(value = "/user-update/confirmed", method = RequestMethod.POST)
    public void updateUser(@RequestBody String data) {
        String temp = null;
        try {
            temp = URLDecoder.decode(data, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String id = temp.split(";", 2)[0];
        String name = temp.split(";", 2)[1];
        userService.updUserName(name, Long.parseLong(id));
    }

}

