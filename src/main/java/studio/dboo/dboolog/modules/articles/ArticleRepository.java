package studio.dboo.dboolog.modules.articles;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.dboolog.modules.articles.entity.Article;

import javax.transaction.Transactional;

@Transactional
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
