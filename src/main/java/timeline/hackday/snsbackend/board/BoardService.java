package timeline.hackday.snsbackend.board;

import java.util.Optional;

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

	public boolean updateBoard(Board board, Long id) {
		Optional<Board> optionalBoard = boardRepository.findById(id);
		if (optionalBoard.isEmpty()) {
			return false;
		}

		Board existingBoard = optionalBoard.get();
		System.out.println(existingBoard.getContent());
		existingBoard.setTitle(board.getTitle());
		existingBoard.setContent(board.getContent());
		return true;
	}
}
