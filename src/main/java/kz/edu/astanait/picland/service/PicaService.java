package kz.edu.astanait.picland.service;

import kz.edu.astanait.picland.exception.EntityNotFoundException;
import kz.edu.astanait.picland.model.Pica;
import kz.edu.astanait.picland.model.User;
import kz.edu.astanait.picland.repository.PicaRepository;
import kz.edu.astanait.picland.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PicaService {

    private final PicaRepository picaRepository;

    private final UserRepository userRepository;

    public List<Pica> findAllPicas(){
        return picaRepository.findAll();
    }

    public List<Pica> findPicasByTitle(String title){
        return picaRepository.findAllByTitleContainingIgnoreCase(title);
    }

    public Pica findPica(Long picaId){
        return picaRepository.findById(picaId)
                .orElseThrow(() -> new EntityNotFoundException(Pica.class, "id", picaId.toString()));
    }

    public List<Pica> findUserPicas(String username){
        User user = userRepository.findUserByUsername(username).orElse(null);
        if(user != null) {
            return picaRepository.findAllByUser_Username(username);
        }
        throw new EntityNotFoundException(User.class, "username", username);
    }

    public void savePica(Pica pica){
        picaRepository.save(pica);
    }

    public Pica updatePica(Pica pica){
        return picaRepository.save(pica);
    }

    public void deletePica(Long picaId){
        picaRepository.deleteById(picaId);
    }
}
