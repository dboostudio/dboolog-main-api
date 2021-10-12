package studio.dboo.dboolog.modules.posts.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import studio.dboo.dboolog.modules.posts.articles.entity.Article;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleSearchResultDto {
    private Long total;
    private List<Article> articleList;
    private Pageable pageable;
}
