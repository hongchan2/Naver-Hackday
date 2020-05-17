package timeline.hackday.snsbackend.board;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.common.BaseControllerTest;
import timeline.hackday.snsbackend.common.TestDescription;

public class BoardControllerTest extends BaseControllerTest {

	@Autowired
	BoardService boardService;

	@Test
	@TestDescription("정상적으로 게시물 생성하는 테스트")
	public void createBoard_Is_Ok() throws Exception {
		// Given
		Account savedAccount = getAccountSaved("hongchan", "B31$#23D21$&");

		BoardDto boardDto = getBoard(savedAccount);

		// When & Then
		mockMvc.perform(post("/api/board")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardDto)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(header().exists(HttpHeaders.LOCATION))
			.andExpect(jsonPath("title").value(boardDto.getTitle()))
			.andExpect(jsonPath("content").value(boardDto.getContent()))
			.andExpect(jsonPath("accountId").value(boardDto.getAccountId()))
			.andDo(document("create-board",
				requestFields(
					fieldWithPath("title").description("게시물의 제목"),
					fieldWithPath("content").description("게시물의 내용"),
					fieldWithPath("createTime").description("게시물의 작성 시간 (LocalDateTime)"),
					fieldWithPath("accountId").description("게시물의 작성자 ID")
				),
				responseHeaders(
					headerWithName(HttpHeaders.LOCATION).description("새로 생성된 게시물 URL")
				)
			));
	}

	@Test
	@TestDescription("필수 입력 값을 빼먹어 예외가 발생하는 테스트")
	public void createBoard_Bad_Request() throws Exception {
		// Given
		Account savedAccount = getAccountSaved("hongchan", "B31$#23D21$&");

		BoardDto boardDto = getBoard(savedAccount);
		boardDto.setContent(null);

		// When & Then
		mockMvc.perform(post("/api/board")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardDto)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@TestDescription("이벤트를 정상적으로 수정하는 테스트")
	public void updateBoard_Is_Ok() throws Exception {
		// Given
		Account savedAccount = getAccountSaved("hongchan", "B31$#23D21$&");

		BoardDto boardDto = getBoard(savedAccount);
		Board savedBoard = boardRepository.save(boardService.mapToBoard(boardDto).get());
		boardDto.setContent("content changed!");

		// When & Then
		mockMvc.perform(put("/api/board/{id}", savedBoard.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(boardDto)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("content").value(boardDto.getContent()))
			.andDo(document("update-board"));
	}

	@Test
	@TestDescription("이벤트를 정상적으로 삭제하는 테스트")
	public void deleteBoard_Is_Ok() throws Exception {
		// Given
		Account savedAccount = getAccountSaved("hongchan", "B31$#23D21$&");

		BoardDto boardDto = getBoard(savedAccount);
		Board savedBoard = boardRepository.save(boardService.mapToBoard(boardDto).get());

		// When & Then
		mockMvc.perform(delete("/api/board/{id}", savedBoard.getId())
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andDo(document("delete-board"));
	}

	@Test
	@TestDescription("존재하지 않는 이벤트 삭제를 시도해 예외가 발생하는 테스트")
	public void deleteBoard_Not_Found() throws Exception {
		// Given
		Account savedAccount = getAccountSaved("hongchan", "B31$#23D21$&");

		BoardDto boardDto = getBoard(savedAccount);
		Board savedBoard = boardRepository.save(boardService.mapToBoard(boardDto).get());

		// When & Then
		mockMvc.perform(delete("/api/board/7777")
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNotFound());
	}

}
