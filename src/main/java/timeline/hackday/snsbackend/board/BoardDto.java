package timeline.hackday.snsbackend.board;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

	@NotEmpty
	private String title;

	@NotEmpty
	private String content;

	@NotNull
	private LocalDateTime createTime;

	@NotNull
	private Long accountId;

}
