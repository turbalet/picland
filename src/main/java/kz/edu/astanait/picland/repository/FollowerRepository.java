package kz.edu.astanait.picland.repository;

import kz.edu.astanait.picland.model.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    long countByUser_UserId(Long id);
    long countByFollower_UserId(Long id);
    boolean existsByUser_UserIdAndFollower_UserId(Long userId, Long followerId);
    List<Follower> findAllByUser_UserId(Long userId);
    void deleteByUser_UserIdAndFollower_UserId(Long userId, Long followerId);
}
