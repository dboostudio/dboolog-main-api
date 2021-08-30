package studio.dboo.dboolog.modules.articles;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.dboo.dboolog.modules.annotation.RestControllerLogger;

import java.time.LocalDateTime;

@RestController
@RestControllerLogger
@RequestMapping("api/article")
@RequiredArgsConstructor
@Api(tags = {"게시물 CRUD"})
public class ArticleController {



    /** Logging Data Manipulation **/
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    private LocalDateTime droppedAt;

}
