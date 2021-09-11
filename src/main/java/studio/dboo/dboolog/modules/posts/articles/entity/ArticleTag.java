package studio.dboo.dboolog.modules.posts.articles.entity;

import studio.dboo.dboolog.modules.posts.tags.entity.Tag;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class ArticleTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name="tag_id")
    private Tag tag;
}
