package timeline.hackday.snsbackend.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.http.MediaType;

import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.common.BaseControllerTest;
import timeline.hackday.snsbackend.common.TestDescription;

public class BoardControllerTest extends BaseControllerTest {

	@Test
	@TestDescription("정상적으로 게시물 생성하는 테스트")
	public void createBoard_Is_Ok() throws Exception {
		Account savedAccount = getAccount();

		Board board = getBoard(savedAccount);

		mockMvc.perform(post("/api/board")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(board)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("title").value(board.getTitle()))
			.andExpect(jsonPath("content").value(board.getContent()))
			.andExpect(jsonPath("account.username").value(board.getAccount().getUsername()));
	}

	@Test
	@TestDescription("필수 입력 값을 빼먹어 예외가 발생하는 테스트")
	public void createBoard_Bad_Request() throws Exception {
		Account savedAccount = getAccount();

		Board board = getBoard(savedAccount);
		board.setContent(null);

		mockMvc.perform(post("/api/board")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(board)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@TestDescription("이벤트를 정상적으로 수정하는 테스트")
	public void updateBoard_Is_Ok() throws Exception {
		Account savedAccount = getAccount();

		Board savedBoard = boardRepository.save(getBoard(savedAccount));
		savedBoard.setContent("content changed!");

		mockMvc.perform(put("/api/board/{id}", savedBoard.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(savedBoard)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("content").value(savedBoard.getContent()));
	}

	private Board getBoard(Account savedAccount) {
		return Board.builder()
			.title("test title")
			.content("test content")
			.createTime(LocalDateTime.now())
			.account(savedAccount)
			.build();
	}

	private Account getAccount() {
		Account account = new Account();
		account.setUsername("hongchan");
		account.setPassword("1234");
		return accountRepository.save(account);
	}
}
