package studio.dboo.dboolog.modules.posts.articles.entity;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import studio.dboo.dboolog.modules.posts.categories.entity.Category;
import studio.dboo.dboolog.modules.posts.comments.entity.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Article {

    @Id @GeneratedValue
    private Long id;

    private String title;

//    @ManyToOne
//    private Category category;

    private String category;

    @Lob
    private String content;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "article")
    private List<ArticleTag> tag = new ArrayList<>();

    /** Logging Data Manipulation **/
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

}
