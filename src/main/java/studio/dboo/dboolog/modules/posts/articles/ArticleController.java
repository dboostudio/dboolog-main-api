package studio.dboo.dboolog.modules.posts.articles;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.dboo.dboolog.modules.annotation.RestControllerLogger;

@RestController
@RestControllerLogger
@RequestMapping("api/article")
@RequiredArgsConstructor
@Api(tags = {"게시물 CRUD"})
public class ArticleController {

}
