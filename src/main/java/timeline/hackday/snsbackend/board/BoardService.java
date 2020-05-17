package timeline.hackday.snsbackend.board;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class BoardService {

	private final BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
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
		existingBoard.setTitle(board.getTitle());
		existingBoard.setContent(board.getContent());
		boardRepository.save(existingBoard);
		return true;
	}

	public boolean deleteBoard(Long id) {
		Optional<Board> optionalBoard = boardRepository.findById(id);
		if (optionalBoard.isEmpty()) {
			return false;
		}

		boardRepository.deleteById(id);
		return true;
	}
}
