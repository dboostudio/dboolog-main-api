package studio.dboo.dboolog.modules.posts.articles;

import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import studio.dboo.dboolog.modules.posts.articles.entity.Article;
import studio.dboo.dboolog.modules.posts.tags.entity.Tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();

    public void getArticlesFromResources() throws IOException {
        Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath:/static/articles/**/*.md");
        Parser parser = Parser.builder().build();
        String[] sampleTags = {"a", "b", "c"};
        for(Resource resource : resources){
            File file = resource.getFile();
            String fileName = resource.getFile().getName();
            String category = resource.getFile().getPath().split("articles/")[1].split("/")[0];
            List<Tag> tags = new ArrayList<>();

            System.out.println("fileName : " + fileName);
            System.out.println("category : " + category);

            if(!fileName.equals(category)){
                Article article = Article.builder()
                        .title(fileName)
                        .category(category)
                        .build();
                articleRepository.save(article);
            }

//            for(String tag : sampleTags){
//                tags.add(Tag.builder()
//                        .article(articleRepository.findArticleTagByTitle(fileName))
//                        .build());
//            }

        }
    }
}
