package timeline.hackday.snsbackend.timeline;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import timeline.hackday.snsbackend.timeline.projection.TimelineSummary;

@Service
public class TimelineService {

	private final TimelineRepository timelineRepository;

	public TimelineService(TimelineRepository timelineRepository) {
		this.timelineRepository = timelineRepository;
	}

	/*
	 * projection을 사용해 join을 하여 두 번의 쿼리만 발생하도록 함
	 * 1. content  2. total count
	 */
	public Page<TimelineSummary> getTimelinePage(Long accountId, Pageable pageable) {
		return timelineRepository.findByAccount_Id(accountId, pageable);
	}
}
