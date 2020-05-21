package timeline.hackday.snsbackend.timeline;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.board.Board;

@Entity
@Getter
@Setter
public class Timeline {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Account account;

	@ManyToOne
	private Board board;
}
