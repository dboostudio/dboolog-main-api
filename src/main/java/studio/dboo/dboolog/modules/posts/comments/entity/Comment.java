package studio.dboo.dboolog.modules.posts.comments.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import studio.dboo.dboolog.modules.posts.articles.entity.Article;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {

    @Id @GeneratedValue
    Long id;

    String authorid; //account > userId

    @ManyToOne
    Article article;

    String content;

    Boolean isPrivate;

    /** Logging Data Manipulation **/
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

}
