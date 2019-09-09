package net.gueka.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import net.gueka.user.exception.BadFormatException;
import net.gueka.user.exception.NotFoundException;
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

    public User update(User user) {
        if(user.getId() != null){
            User saved = findById(user.getId());
            if(saved != null){
                saved.setName(!StringUtils.isEmpty(user.getName()) ? user.getName() : saved.getName());
                saved.setSurname(!StringUtils.isEmpty(user.getSurname()) ? user.getSurname() : saved.getSurname());
                saved.setTags(user.getTags() != null ? user.getTags() : saved.getTags());
                saved.setLocation(user.getLocation() != null ? user.getLocation() : saved.getLocation());
                return repository.save(saved);
            }else{
                throw new NotFoundException("User not found. id: " + user.getId().toString());
            }
        }
        throw new BadFormatException("User id parameter not found.");
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