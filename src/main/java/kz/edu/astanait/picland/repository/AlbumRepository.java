package kz.edu.astanait.picland.repository;

import kz.edu.astanait.picland.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> getAlbumsByUserUserId(long userId);
    List<Album> getAlbumsByUserUsername(String username);
    List<Album> getAlbumByAlbumId(long albumId);
    List<Album> getAlbumsByUserUserIdAndIsPrivateFalse(long userId);
}
