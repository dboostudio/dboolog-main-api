package studio.dboo.dboolog.modules.posts.articles;

import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import studio.dboo.dboolog.modules.posts.articles.dto.ArticleSearchDto;
import studio.dboo.dboolog.modules.posts.articles.dto.ArticleSearchResultDto;
import studio.dboo.dboolog.modules.posts.articles.entity.Article;
import studio.dboo.dboolog.modules.posts.categories.CategoryRepository;
import studio.dboo.dboolog.modules.posts.categories.entity.Category;
import studio.dboo.dboolog.modules.posts.tags.entity.Tag;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();

    public void getArticlesFromResources() throws IOException {
        Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath:/static/articles/**/*.md");
        String[] sampleTags = {"a", "b", "c"};
        for(Resource resource : resources){

            File file = resource.getFile();
            String fileName = resource.getFile().getName();
            String categoryNm = resource.getFile().getPath().split("articles/")[1].split("/")[0];
            List<Tag> tags = new ArrayList<>();

            Parser parser = Parser.builder().build();
            Node document = parser.parseReader(new FileReader(file));
            HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
            TextContentRenderer textContentRenderer = TextContentRenderer.builder().build();

            String content = htmlRenderer.render(document);

            if(!fileName.equals(categoryNm)){
                System.out.println(categoryNm);
                Article article = Article.builder()
                        .title(fileName)
                        .content(content)
                        .build();

                Optional<Category> category;
                if(categoryRepository.existsByCategoryName(categoryNm)){
                    category = categoryRepository.findByCategoryName(categoryNm);
                } else {
                    category = Optional.of(createNewCategoryWithFirstArticle(
                            Category.builder().categoryName(categoryNm).build(), article));
                }
                category.get().addArticle(article);
                categoryRepository.save(category.get());
            }
        }
    }

    private Category createNewCategoryWithFirstArticle(Category category, Article article) {
        Set<Article> articles = new HashSet<>();
        articles.add(article);
        category.setArticles(articles);
        return category;
    }

    public ArticleSearchResultDto getArticles(ArticleSearchDto articleSearchDto) {
        Page<Article> articlesPages = articleRepository.findAll(articleSearchDto.pageable());
        return articlePageToResultDto(articlesPages);
    }

    // TODO - Page객체를 map했을시에는, objectmapper가 Page객체의 content에까지 적용이안되서 이렇게 했는데... 좋은 방법 있으면 바꿔야할듯
    private ArticleSearchResultDto articlePageToResultDto(Page<Article> articlePage) {
        List<Article> articles = new ArrayList<>();
        articlePage.stream().forEach(article -> { articles.add(article); });

        return ArticleSearchResultDto.builder()
                .articleList(articles)
                .total(articlePage.getTotalElements())
                .pageable(articlePage.getPageable())
                .build();
    }

    public Article getArticleById(Long articleId) {
        Optional<Article> findById = articleRepository.findArticleById(articleId);
        Article article = findById.orElseThrow(() -> new RuntimeException("해당 게시물은 존재하지 않습니다."));
        return article;
    }
}
