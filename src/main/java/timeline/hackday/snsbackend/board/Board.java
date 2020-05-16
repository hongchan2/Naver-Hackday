package timeline.hackday.snsbackend.board;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import timeline.hackday.snsbackend.account.Account;

@Entity
@Getter
@Setter
@Builder
public class Board {

	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty
	private String title;

	@Lob
	@NotEmpty
	private String content;

	@NotNull
	private LocalDateTime createTime;

	@ManyToOne
	@NotNull
	private Account account;
}
