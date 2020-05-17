package timeline.hackday.snsbackend.follow;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import timeline.hackday.snsbackend.account.Account;

@Entity
@Getter
@Setter
public class Follow {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Account src;

	@ManyToOne
	private Account dest;
}
