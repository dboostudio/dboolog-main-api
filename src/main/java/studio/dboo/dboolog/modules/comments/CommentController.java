package studio.dboo.dboolog.modules.comments;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.dboo.dboolog.modules.annotation.RestControllerLogger;

@RestController
@RestControllerLogger
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Api(tags = {"코멘트 CRUD"})
public class CommentController {

}
