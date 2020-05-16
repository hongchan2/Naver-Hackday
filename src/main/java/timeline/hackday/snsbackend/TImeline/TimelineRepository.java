package timeline.hackday.snsbackend.TImeline;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {
}
