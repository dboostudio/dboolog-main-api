package studio.dboo.dboolog.modules.posts.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.dboolog.modules.posts.categories.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
