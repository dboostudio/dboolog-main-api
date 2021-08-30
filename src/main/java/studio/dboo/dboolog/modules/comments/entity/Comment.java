package studio.dboo.dboolog.modules.comments.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import studio.dboo.dboolog.modules.accounts.entity.Account;
import studio.dboo.dboolog.modules.articles.entity.Article;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class Comment {
    Account username;

    @ManyToOne
    Article article;

    String content;

    /** Logging Data Manipulation **/
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    private LocalDateTime droppedAt;


}
