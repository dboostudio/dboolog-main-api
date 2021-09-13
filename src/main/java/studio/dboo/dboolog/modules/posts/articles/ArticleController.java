package studio.dboo.dboolog.modules.posts.articles;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studio.dboo.dboolog.modules.annotation.RestControllerLogger;
import studio.dboo.dboolog.modules.posts.articles.dto.ArticleSearchDto;
import studio.dboo.dboolog.modules.posts.categories.CategoryService;

import javax.websocket.server.PathParam;

@RestController
@RestControllerLogger
@RequestMapping("api/articles")
@RequiredArgsConstructor
@Api(tags = {"게시물 CRUD"})
public class ArticleController {

    private final ArticleService articleService;

//    @GetMapping("/{pageNumber}")
//    public ResponseEntity<?> getArticles(@PathVariable Integer pageNumber){
//        System.out.println("pageNumber before Optional : "+pageNumber);
//        Optional.of(pageNumber).orElse(pageNumber = 0);
//        System.out.println("pageNumber after Optional : "+pageNumber);
//        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticles(pageNumber));
//    }
//@PageableDefault(size = 9, sort = "publishedDateTime", direction = Sort.Direction.DESC)

    @GetMapping("/{articleId}")
    public ResponseEntity<?> getArticle(@PathVariable Long articleId){
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticleById(articleId));
    }

    @PostMapping
    public ResponseEntity<?> postArticles(@RequestBody ArticleSearchDto articleSearchDto){
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticles(articleSearchDto));
    }

    @GetMapping("/get-categories")
    public ResponseEntity<?> getCategories(){
//        return ResponseEntity.status(HttpStatus.OK).body(articleService.getCategories());
        return null;
    }
}
