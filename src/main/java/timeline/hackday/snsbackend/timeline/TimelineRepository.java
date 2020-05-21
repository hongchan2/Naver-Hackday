package timeline.hackday.snsbackend.timeline;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import timeline.hackday.snsbackend.timeline.projection.TimelineSummary;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {

	Page<TimelineSummary> findByAccount_Id(Long accountId, Pageable pageable);

	void deleteByBoard_Account_Id(Long id);

	// To do test
	long countByAccount_Id(Long accountId);

}
