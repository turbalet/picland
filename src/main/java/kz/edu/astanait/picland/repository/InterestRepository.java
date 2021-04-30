package kz.edu.astanait.picland.repository;

import kz.edu.astanait.picland.model.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    Interest findByInterestName(String interestName);
    List<Interest> findAllByUsers_Username(String username);
}
