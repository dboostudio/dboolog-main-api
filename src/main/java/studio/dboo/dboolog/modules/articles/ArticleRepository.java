package studio.dboo.dboolog.modules.articles;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.dboolog.modules.articles.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
