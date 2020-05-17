package timeline.hackday.snsbackend.follow;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.http.MediaType;

import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.common.BaseControllerTest;
import timeline.hackday.snsbackend.common.TestDescription;

public class FollowControllerTest extends BaseControllerTest {

	@Test
	@TestDescription("정상적으로 follow 관계를 생성하는 테스트")
	public void createFollow_Is_Ok() throws Exception {
		// Given
		Account savedAccount1 = getAccountSaved("hongchan", "B31$#23D21$&");
		Account savedAccount2 = getAccountSaved("jiyun", "B31$#23D21$&");

		FollowDto followDto = new FollowDto();
		followDto.setSrcId(savedAccount1.getId());
		followDto.setDestId(savedAccount2.getId());

		// When & Then
		mockMvc.perform(post("/api/follow")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(followDto)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("srcId").value(followDto.getSrcId()))
			.andExpect(jsonPath("destId").value(followDto.getDestId()));
	}

	@Test
	@TestDescription("존재하지 않는 유저를 구독해, follow 관계 생성시 예외가 발생하는 테스트")
	public void createFollow_Bad_Request() throws Exception {
		// Given
		Account savedAccount1 = getAccountSaved("hongchan", "B31$#23D21$&");

		FollowDto followDto = new FollowDto();
		followDto.setSrcId(savedAccount1.getId());
		followDto.setDestId(777L);

		// When & Then
		mockMvc.perform(post("/api/follow")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(followDto)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

}
