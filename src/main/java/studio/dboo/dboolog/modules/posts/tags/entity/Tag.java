package studio.dboo.dboolog.modules.posts.tags.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import studio.dboo.dboolog.modules.posts.articles.entity.ArticleTag;
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
public class Tag {

    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "tag")
    private List<ArticleTag> article = new ArrayList<>();

    /** Logging Data Manipulation **/
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    private LocalDateTime droppedAt;
}
