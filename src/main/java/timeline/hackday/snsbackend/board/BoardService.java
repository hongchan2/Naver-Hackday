package timeline.hackday.snsbackend.board;

import java.util.Optional;

import org.springframework.stereotype.Service;

import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.account.AccountRepository;
import timeline.hackday.snsbackend.batch.IBatchService;

@Service
public class BoardService {

	private final BoardRepository boardRepository;

	private final AccountRepository accountRepository;

	private final IBatchService batchService;

	public BoardService(BoardRepository boardRepository,
		AccountRepository accountRepository, IBatchService batchService) {
		this.boardRepository = boardRepository;
		this.accountRepository = accountRepository;
		this.batchService = batchService;
	}

	public Long createBoard(BoardDto boardDto) {
		Optional<Board> optionalBoard = mapToBoard(boardDto);

		if (optionalBoard.isEmpty()) {
			return -1L;
		}

		Board board = boardRepository.save(optionalBoard.get());
		Long boardId = board.getId();

		// Request to batch service (팔로우하는 유저들의 타임라인에 게시물을 추가)
		batchService.addTimelinesToFollowee(boardId, board.getAccount().getId());
		return boardId;
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

		Board board = optionalBoard.get();
		boardRepository.deleteById(board.getId());

		/*
			Request to batch service (팔로우하는 유저들의 타임라인에 게시물을 삭제)
			TODO - Set cascade option 고려해보기
		 */
		batchService.removeTimelinesToFollowee(board.getId(), board.getAccount().getId());

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
