package timeline.hackday.snsbackend.board;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.account.AccountRepository;
import timeline.hackday.snsbackend.batch.IBatchService;

@Service
public class BoardService {

	private final BoardRepository boardRepository;

	private final AccountRepository accountRepository;
	
	@Autowired
	private IBatchService batchService;

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

		/*
			TODO - Call batch service (팔로우하는 유저들의 타임라인에 게시물을 추가)
			Request type
			{
			  "accountId": 0,
			  "boardId": 0
			}
		 */
		Board board = boardRepository.save(optionalBoard.get());
		batchService.addTimelinesToFollowee(board.getId(), board.getAccount().getId());
		return board.getId();
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

		/*
			TODO <Set cascade option> Or <Call batch service> (팔로우하는 유저들의 타임라인에 게시물을 삭제)
		 */

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
