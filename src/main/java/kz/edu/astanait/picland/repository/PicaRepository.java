package kz.edu.astanait.picland.repository;

import kz.edu.astanait.picland.model.Pica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PicaRepository extends JpaRepository<Pica, Long> {
    List<Pica> findAllByUser_Username(String username);
    List<Pica> findAllByTitleContainingIgnoreCase(String title);
    List<Pica> findAllByInterest_InterestIdIn(Collection<Long> interestIds);
}
