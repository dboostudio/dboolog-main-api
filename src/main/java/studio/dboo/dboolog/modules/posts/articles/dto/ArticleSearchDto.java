package studio.dboo.dboolog.modules.posts.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleSearchDto {

    private String title;

    private String category;

    private Integer size = 10;

    private Integer page = 0;

    private String sortTarget = "createdAt";

    private String sortDirection = "DESC";

    public Pageable pageable(){
        return PageRequest.of(getPage(), getSize(), getSort());
    }

    public Integer getSize(){
        if(this.size < 1){
            this.size = 10;
        }
        return this.size;
    }

    public Integer getPage(){
        if(this.page < 0){
            this.page = 0;
        }
        return this.page;
    }

    private Sort getSort(){
        if(!StringUtils.hasText(sortTarget)){
            sortTarget = "createdAt";
        }

        switch (sortDirection){
            case "ASC" :
                return Sort.by(sortTarget).ascending();
            default:
                return Sort.by(sortTarget).descending();
        }
    }
}
