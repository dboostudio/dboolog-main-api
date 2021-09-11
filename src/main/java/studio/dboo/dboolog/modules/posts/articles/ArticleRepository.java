package studio.dboo.dboolog.modules.posts.articles;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.dboolog.modules.posts.articles.entity.Article;
import studio.dboo.dboolog.modules.posts.articles.entity.ArticleTag;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findArticleByTitle(String title);

    List<ArticleTag> findArticleTagByTitle(String title);
}
