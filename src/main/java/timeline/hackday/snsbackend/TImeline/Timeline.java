package timeline.hackday.snsbackend.TImeline;

import timeline.hackday.snsbackend.Account.Account;
import timeline.hackday.snsbackend.Board.Board;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Timeline {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Board board;
}
