package studio.dboo.dboolog.modules.posts.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studio.dboo.dboolog.modules.posts.categories.entity.Category;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
