package timeline.hackday.snsbackend.timeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import timeline.hackday.snsbackend.timeline.projection.TimelineSummary;

@Controller
@RequestMapping(value = "/api/timeline", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimelineController {

	@Autowired
	TimelineService timelineService;

	@GetMapping("/{accountId}")
	@ResponseBody
	public Page<TimelineSummary> getTimeline(@PathVariable Long accountId, Pageable pageable) {
		return timelineService.getTimelinePage(accountId, pageable);
	}
}
