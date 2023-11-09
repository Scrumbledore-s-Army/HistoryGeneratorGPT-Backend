package dat3.openai_demo.repository;

import dat3.openai_demo.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRespository extends JpaRepository<Story, Integer> {
}
