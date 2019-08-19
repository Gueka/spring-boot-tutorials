package net.gueka.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.gueka.user.model.User;
import net.gueka.user.repository.UserRepository;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public List<User> allUsers() {
        return repository.findAll();
    }

    public User save(User user) {
        return repository.save(user);
    }

	public User findById(UUID id) {
		return repository.findById(id).get();
	}

	public Boolean remove(UUID id) {
        try{
            repository.deleteById(id);
            return true;
        }catch(IllegalArgumentException e){
            log.error("User with id '{}' was not found", id, e);
            return false;
        }
	}
}