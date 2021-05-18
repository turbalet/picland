package kz.edu.astanait.picland.repository;

import kz.edu.astanait.picland.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPica_PicaId(Long id);
    void deleteCommentByCommentId(Long id);
}
