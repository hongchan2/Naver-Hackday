package timeline.hackday.snsbackend.board;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
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
}
