package studio.dboo.dboolog.modules.posts.articles.entity;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import studio.dboo.dboolog.modules.posts.base.BaseTimeEntity;
import studio.dboo.dboolog.modules.posts.categories.entity.Category;
import studio.dboo.dboolog.modules.posts.comments.entity.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Article extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private String title;

    @ManyToOne
    private Category category;

    @Lob
    private String content;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleTag> tag = new ArrayList<>();

}
