package studio.dboo.dboolog.modules.posts.articles;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleBringer implements ApplicationRunner {

    private final ArticleService articleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        articleService.getArticlesFromResources();
    }
}
