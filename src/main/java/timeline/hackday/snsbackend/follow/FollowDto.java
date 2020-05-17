package timeline.hackday.snsbackend.follow;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {

	@NotNull
	private Long srcId;

	@NotNull
	private Long destId;

}
