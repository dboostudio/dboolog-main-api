package studio.dboo.dboolog.modules.posts.articles;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studio.dboo.dboolog.modules.annotation.RestControllerLogger;
import studio.dboo.dboolog.modules.posts.articles.dto.ArticleSearchDto;
import studio.dboo.dboolog.modules.posts.articles.entity.Article;
import studio.dboo.dboolog.modules.posts.categories.CategoryService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RestControllerLogger
@RequestMapping("api/articles")
@RequiredArgsConstructor
@Api(tags = {"게시물 CRUD"})
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{articleId}")
    public ResponseEntity<?> getArticle(@PathVariable Long articleId){
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticleById(articleId));
    }

    @PostMapping
    public ResponseEntity<?> getArticles(@RequestBody ArticleSearchDto articleSearchDto){
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticles(articleSearchDto));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createArticles(@RequestBody @Valid Article article){
        return ResponseEntity.status(HttpStatus.OK).body(articleService.createArticle(article));
    }
}
