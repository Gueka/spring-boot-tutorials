package net.gueka.user.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.gueka.user.model.User;
import net.gueka.user.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping(value = "/all")
    public List<User> getAllUsers(){
        return service.allUsers();
    }

    @GetMapping
    public User getUserById(@RequestParam @Valid UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public User add(@RequestBody @Valid User user) {
        return service.save(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        User saved = service.findById(user.getId());
        saved.setName(user.getName());
        saved.setSurname(user.getSurname());
        saved.setTags(user.getTags());
        saved.setLocation(user.getLocation());
        return service.save(user);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam @Valid UUID id) {
        if(service.remove(id)){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


}