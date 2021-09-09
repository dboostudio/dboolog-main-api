package studio.dboo.dboolog.modules.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.dboolog.modules.categories.entity.Category;

import javax.transaction.Transactional;

@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
