package timeline.hackday.snsbackend.Board;

import timeline.hackday.snsbackend.Account.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Board {

    @Id @GeneratedValue
    private Long id;

    private String title;

    @Lob
    private String content;

    private LocalDateTime createTime;

    @ManyToOne
    private Account account;
}
