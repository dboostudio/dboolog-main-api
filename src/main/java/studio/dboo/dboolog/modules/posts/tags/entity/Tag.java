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
import studio.dboo.dboolog.modules.posts.base.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Tag extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "tag")
    private List<ArticleTag> article = new ArrayList<>();

}
