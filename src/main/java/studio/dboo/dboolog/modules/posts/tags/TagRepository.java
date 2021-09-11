package studio.dboo.dboolog.modules.posts.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.dboolog.modules.posts.tags.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
