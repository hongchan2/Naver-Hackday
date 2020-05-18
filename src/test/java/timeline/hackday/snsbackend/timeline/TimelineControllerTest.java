package timeline.hackday.snsbackend.timeline;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.IntStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.board.Board;
import timeline.hackday.snsbackend.common.BaseControllerTest;
import timeline.hackday.snsbackend.common.TestDescription;
import timeline.hackday.snsbackend.follow.Follow;

public class TimelineControllerTest extends BaseControllerTest {

	@Autowired
	TimelineRepository timelineRepository;

	@Test
	@TestDescription("Timeline을 5개씩 첫 번쩨 페이지를 조회하는 테스트")
	public void getTimelineList() throws Exception {
		// Given
		// 1. user1은 user2 ~ user9 까지 follow
		Account user1 = getSavedAccount("user1", "$$$$");

		IntStream.range(2, 10).forEach(i -> {
			Account savedAccount = getSavedAccount("user" + i, "$$$$");

			Follow follow = new Follow();
			follow.setSrc(user1);
			follow.setDest(savedAccount);
			followRepository.save(follow);

			// 2. user2 ~ user9 가 각각 5개의 게시물 생성
			IntStream.range(1, 6).forEach(j -> {
				Board board = boardService.mapToBoard(getBoard(savedAccount)).get();
				board.setTitle(board.getTitle() + j);
				boardRepository.save(board);

				/*
					원래는 board를 게시하면 배치 서비스에서 타임라인에 추가해주나,
					테스트 진행을 위해 일일히 타임라인에 추가
				 */
				Timeline timeline = new Timeline();
				timeline.setAccount(user1);
				timeline.setBoard(board);
				timelineRepository.save(timeline);
			});
		});

		// When & Then
		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/timeline/{id}", user1.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.param("page", "0")
			.param("size", "5")
			.param("sort", "board_CreateTime,DESC"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("content[0].board_Title").value("test title5"))
			.andExpect(jsonPath("content[0].board_Account.username").value("user9"))
			.andDo(document("get-timeline-list",
				pathParameters(
					parameterWithName("id").description("조회할 사용자의 ID")
				),
				requestParameters(
					parameterWithName("page").description("요청할 페이지"),
					parameterWithName("size").description("요청할 페이지 크기"),
					parameterWithName("sort").description("페이징에 적용할 정렬 기준")
				)
			));
		;
	}
}
