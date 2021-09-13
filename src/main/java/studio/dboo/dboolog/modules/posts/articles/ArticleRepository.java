package studio.dboo.dboolog.modules.posts.articles;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.dboolog.modules.posts.articles.entity.Article;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findArticleById(Long articleId);
    Optional<Article> findArticlesByCategory(String category);
//    String[] findAllBy
}
