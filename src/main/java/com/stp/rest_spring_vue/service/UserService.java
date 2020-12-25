package com.stp.rest_spring_vue.service;

import com.stp.rest_spring_vue.model.User;
import com.stp.rest_spring_vue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id){
        return userRepository.getOne(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findUserByName(String name){
        return userRepository.findByNameCustomQuery(name);
    }

    public List<User> customSelect(){
        return userRepository.findAllByCustomQuery();
    }

    public User getUserInfo(Long id){
        return userRepository.getUserInfo(id);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void updUserName(String userName, Long userId){
        userRepository.updUser(userName, userId);
    }
}
