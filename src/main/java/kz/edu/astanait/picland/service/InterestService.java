package kz.edu.astanait.picland.service;

import kz.edu.astanait.picland.exception.EntityNotFoundException;
import kz.edu.astanait.picland.model.Interest;
import kz.edu.astanait.picland.model.User;
import kz.edu.astanait.picland.repository.InterestRepository;
import kz.edu.astanait.picland.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class InterestService {

    private final InterestRepository interestRepository;

    private final UserRepository userRepository;

    public List<Interest> findAllInterests(){
        return interestRepository.findAll();
    }

    public Interest findInterest(Long interestId){
        return interestRepository.findById(interestId)
                .orElseThrow(() -> new EntityNotFoundException(Interest.class, "id", interestId.toString()));
    }

    public List<Interest> findUserInterests(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null) {
            return interestRepository.findAllByUsers_Username(username);
        }
        throw new EntityNotFoundException(User.class, "username", username);
    }

    public void saveInterest(Interest interest){
        interestRepository.save(interest);
    }

    public Interest updateInterest(Interest interest){
        return interestRepository.save(interest);
    }

    public void deleteInterest(Long id){
        interestRepository.deleteById(id);
    }





}
