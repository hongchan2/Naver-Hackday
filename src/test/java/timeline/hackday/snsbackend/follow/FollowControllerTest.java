package timeline.hackday.snsbackend.follow;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.IntStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.common.BaseControllerTest;
import timeline.hackday.snsbackend.common.TestDescription;

public class FollowControllerTest extends BaseControllerTest {

	@Autowired
	FollowService followService;

	@Test
	@TestDescription("정상적으로 follow 관계를 생성하는 테스트")
	public void createFollow_Is_Ok() throws Exception {
		// Given
		Account savedAccount1 = getSavedAccount("hongchan", "B31$#23D21$&");
		Account savedAccount2 = getSavedAccount("jiyun", "B31$#23D21$&");

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
		Account savedAccount1 = getSavedAccount("hongchan", "B31$#23D21$&");

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

	@Test
	@TestDescription("40개의 following을 10개씩 첫 번쩨 페이지 조회하는 테스트")
	public void getFollowingList_Is_Ok() throws Exception {
		// Given (user1은 user2 ~ user39 까지 follow)
		Account user1 = getSavedAccount("user1", "$$$$");

		IntStream.range(2, 40).forEach(i -> {
			Account savedAccount = getSavedAccount("user" + i, "$$$$");

			Follow follow = new Follow();
			follow.setSrc(user1);
			follow.setDest(savedAccount);
			followRepository.save(follow);
		});

		// When & Then
		mockMvc.perform(get("/api/following/{id}", user1.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.param("page", "0")
			.param("size", "10")
			.param("sort", "dest_Id,ASC"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("content[0].dest_Username").value("user2"));
	}

	@Test
	@TestDescription("40개의 following을 10개씩 첫 번쩨 페이지 조회하는 테스트")
	public void getFollowerList_Is_Ok() throws Exception {
		// Given (user2 ~ user39 는 user1을 follow)
		Account user1 = getSavedAccount("user1", "$$$$");

		IntStream.range(2, 40).forEach(i -> {
			Account savedAccount = getSavedAccount("user" + i, "$$$$");

			Follow follow = new Follow();
			follow.setSrc(savedAccount);
			follow.setDest(user1);
			followRepository.save(follow);
		});

		// When & Then
		mockMvc.perform(get("/api/follower/{id}", user1.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.param("page", "0")
			.param("size", "10")
			.param("sort", "src_Id,ASC"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("content[0].src_Username").value("user2"));
	}

	@Test
	@TestDescription("정상적으로 follow 관계를 삭제하는 테스트")
	public void deleteFollow_Is_Ok() throws Exception {
		// Given
		Account savedAccount1 = getSavedAccount("hongchan", "B31$#23D21$&");
		Account savedAccount2 = getSavedAccount("jiyun", "B31$#23D21$&");

		FollowDto followDto = new FollowDto();
		followDto.setSrcId(savedAccount1.getId());
		followDto.setDestId(savedAccount2.getId());

		followService.follow(followDto);

		// When & Then
		mockMvc.perform(delete("/api/follow/{srcId}/{destId}", followDto.getSrcId(), followDto.getDestId())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@TestDescription("존재하지 않는 follow 관계를 삭제해 예외가 발생하는 테스트")
	public void deleteFollow_Not_Found() throws Exception {
		// Given
		Account savedAccount1 = getSavedAccount("hongchan", "B31$#23D21$&");
		Account savedAccount2 = getSavedAccount("jiyun", "B31$#23D21$&");

		FollowDto followDto = new FollowDto();
		followDto.setSrcId(savedAccount1.getId());
		followDto.setDestId(savedAccount2.getId());

		followService.follow(followDto);

		// When & Then
		mockMvc.perform(delete("/api/follow/{destId}/{srcId}", followDto.getDestId(), followDto.getSrcId())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNotFound());
	}

}
