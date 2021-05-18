package kz.edu.astanait.picland.service;

import kz.edu.astanait.picland.exception.EntityNotFoundException;
import kz.edu.astanait.picland.model.Comment;
import kz.edu.astanait.picland.model.Pica;
import kz.edu.astanait.picland.repository.CommentRepository;
import kz.edu.astanait.picland.repository.PicaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    private final PicaRepository picaRepository;

    public List<Comment> findAllComments(){
        return commentRepository.findAll();
    }

    public Comment findComment(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Comment.class, "id", id.toString()));
    }

    public List<Comment> findPicaComments(Long picaId){
        Pica pica = picaRepository.findById(picaId).orElse(null);
        if(pica!=null){
            return commentRepository.findAllByPica_PicaId(picaId);
        }
        throw new EntityNotFoundException(Pica.class, "id", picaId.toString());
    }

    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment){
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId){
        commentRepository.deleteCommentByCommentId(commentId);
    }
}
