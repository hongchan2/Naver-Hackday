package timeline.hackday.snsbackend.board;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/board", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {

	@Autowired
	BoardService boardService;

	@PostMapping
	public ResponseEntity createBoard(@RequestBody @Valid Board board, Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		boardService.createBoard(board);
		return ResponseEntity.ok(board);
	}

	@PutMapping("/{id}")
	public ResponseEntity updateBoard(@PathVariable Long id,
		@RequestBody @Valid Board board, Errors errors) {

		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		boolean isComplete = boardService.updateBoard(board, id);
		return isComplete ? ResponseEntity.ok(board) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteBorad(@PathVariable Long id) {
		boolean isComplete = boardService.deleteBoard(id);
		return isComplete ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
}
