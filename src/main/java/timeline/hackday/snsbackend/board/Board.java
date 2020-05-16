package timeline.hackday.snsbackend.board;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import timeline.hackday.snsbackend.account.Account;

@Entity
public class Board {

	@Id
	@GeneratedValue
	private Long id;

	private String title;

	@Lob
	private String content;

	private LocalDateTime createTime;

	@ManyToOne
	private Account account;
}
