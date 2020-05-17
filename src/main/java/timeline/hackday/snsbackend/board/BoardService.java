package timeline.hackday.snsbackend.board;

import java.util.Optional;

import org.springframework.stereotype.Service;

import timeline.hackday.snsbackend.account.Account;
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

	public Long createBoard(BoardDto boardDto) {
		Optional<Board> optionalBoard = mapToBoard(boardDto);

		if (optionalBoard.isEmpty()) {
			return -1L;
		}

		return boardRepository.save(optionalBoard.get()).getId();
	}

	public boolean updateBoard(BoardDto boardDto, Long boardId) {
		Optional<Board> optionalBoard = boardRepository.findById(boardId);
		if (optionalBoard.isEmpty()) {
			return false;
		}

		Board existingBoard = optionalBoard.get();
		existingBoard.setTitle(boardDto.getTitle());
		existingBoard.setContent(boardDto.getContent());
		boardRepository.save(existingBoard);
		return true;
	}

	public boolean deleteBoard(Long boardId) {
		Optional<Board> optionalBoard = boardRepository.findById(boardId);
		if (optionalBoard.isEmpty()) {
			return false;
		}

		boardRepository.deleteById(boardId);
		return true;
	}

	public Optional<Board> mapToBoard(BoardDto boardDto) {
		Board board = Board.builder()
			.title(boardDto.getTitle())
			.content(boardDto.getContent())
			.createTime(boardDto.getCreateTime())
			.build();

		Optional<Account> optionalAccount = accountRepository.findById(boardDto.getAccountId());
		if (optionalAccount.isEmpty()) {
			return Optional.empty();
		}

		board.setAccount(optionalAccount.get());
		return Optional.of(board);
	}
}
