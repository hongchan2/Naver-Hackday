package timeline.hackday.snsbackend.Follow;

import timeline.hackday.snsbackend.Account.Account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Follow {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Account src;

    @ManyToOne
    private Account dest;
}
