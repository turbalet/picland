package kz.edu.astanait.picland.service;

import kz.edu.astanait.picland.exception.EntityNotFoundException;
import kz.edu.astanait.picland.model.User;
import kz.edu.astanait.picland.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User findUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId.toString()));
    }

    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "username", username));
    }


}
