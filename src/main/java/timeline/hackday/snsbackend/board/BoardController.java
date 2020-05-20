package timeline.hackday.snsbackend.board;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import timeline.hackday.snsbackend.batch.IBatchService;

@Controller
@RequestMapping(value = "/api/board", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {

	@Autowired
	BoardService boardService;
	

	@PostMapping
	public ResponseEntity createBoard(@RequestBody @Valid BoardDto boardDto, Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		Long createdBoardId = boardService.createBoard(boardDto);
		// Account가 존재하지 않음
		if (createdBoardId == -1L) {
			return ResponseEntity.badRequest().build();
		}
		 
		
		WebMvcLinkBuilder selfLinkBuilder = linkTo(BoardController.class).slash(createdBoardId);
		URI selfUri = selfLinkBuilder.toUri();
		return ResponseEntity.created(selfUri).body(boardDto);
	}

	@PutMapping("/{boardId}")
	public ResponseEntity updateBoard(@PathVariable Long boardId,
		@RequestBody @Valid BoardDto boardDto, Errors errors) {

		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		boolean isComplete = boardService.updateBoard(boardDto, boardId);
		return isComplete ? ResponseEntity.ok(boardDto) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{boardId}")
	public ResponseEntity deleteBorad(@PathVariable Long boardId) {
		boolean isComplete = boardService.deleteBoard(boardId);
		return isComplete ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
}
