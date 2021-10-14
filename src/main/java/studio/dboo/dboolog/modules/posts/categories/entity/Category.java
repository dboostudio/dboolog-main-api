package studio.dboo.dboolog.modules.posts.categories.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import studio.dboo.dboolog.modules.posts.articles.entity.Article;
import studio.dboo.dboolog.modules.posts.base.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Category extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    String categoryName;

    String subCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Article> articles;

    public void addArticle(Article article){
        getArticles().add(article);
        article.setCategory(this);
    }
}
