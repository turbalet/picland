package kz.edu.astanait.picland.service;

import kz.edu.astanait.picland.exception.EntityNotFoundException;
import kz.edu.astanait.picland.model.Follower;
import kz.edu.astanait.picland.model.User;
import kz.edu.astanait.picland.repository.FollowerRepository;
import kz.edu.astanait.picland.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class FollowerService {

    private final FollowerRepository followerRepository;

    private final UserRepository userRepository;

    public List<Follower> findUserFollowers(Long userId){
        return followerRepository.findAllByUser_UserId(userId);
    }

    public boolean isFollowing(Long userId, Long followerId){
        return followerRepository.existsByUser_UserIdAndFollower_UserId(userId, followerId);
    }

    public long getUserFollowersCount(Long userId){
        return followerRepository.countByUser_UserId(userId);
    }

    public void doFollow(Long userId, User currentUser){
        if(!isFollowing(userId, currentUser.getUserId())){
            Follower follower = new Follower();
            follower.setFollower(currentUser);
            follower.setUser(userRepository.findById(userId).orElseThrow(
                    () -> new EntityNotFoundException(User.class, "id", userId.toString())
            ));
            followerRepository.save(follower);
        }
    }

    public void doUnFollow(Long userId, Long followerId){
        followerRepository.deleteByUser_UserIdAndFollower_UserId(userId, followerId);
    }

}
