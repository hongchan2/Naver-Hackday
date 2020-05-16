package timeline.hackday.snsbackend.board;

import org.springframework.stereotype.Service;

import timeline.hackday.snsbackend.account.AccountRepository;

@Service
public class BoardService {

	private final BoardRepository boardRepository;

	private final AccountRepository accountRepository;

	public BoardService(BoardRepository boardRepository,
		AccountRepository accountRepository) {
		this.boardRepository = boardRepository;
		this.accountRepository = accountRepository;
	}

	public void createBoard(Board board) {
		boardRepository.save(board);
	}
}
