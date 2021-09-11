package studio.dboo.dboolog.modules.posts.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.dboolog.modules.posts.comments.entity.Comment;

import javax.transaction.Transactional;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
