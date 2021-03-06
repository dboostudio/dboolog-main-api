package studio.dboo.dboolog.modules.posts.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.dboolog.modules.posts.categories.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryName(String categoryName);
    Optional<Category> findByCategoryName(String categoryNm);

}
