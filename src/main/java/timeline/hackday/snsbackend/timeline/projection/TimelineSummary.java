package timeline.hackday.snsbackend.timeline.projection;

import java.time.LocalDateTime;

public interface TimelineSummary {

	Long getBoard_Id();

	String getBoard_Title();

	String getBoard_Content();

	LocalDateTime getBoard_CreateTime();

	AccountSummary getBoard_Account();

	interface AccountSummary {

		Long getId();

		String getUsername();
	}

}
