package timeline.hackday.snsbackend.common;

import java.time.LocalDateTime;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.account.AccountRepository;
import timeline.hackday.snsbackend.board.BoardDto;
import timeline.hackday.snsbackend.board.BoardRepository;
import timeline.hackday.snsbackend.board.BoardService;
import timeline.hackday.snsbackend.follow.FollowRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@Ignore
public class BaseControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected AccountRepository accountRepository;

	@Autowired
	protected BoardRepository boardRepository;

	@Autowired
	protected FollowRepository followRepository;

	@Autowired
	protected BoardService boardService;

	protected BoardDto getBoard(Account savedAccount) {
		return BoardDto.builder()
			.title("test title")
			.content("test content")
			.createTime(LocalDateTime.now())
			.accountId(savedAccount.getId())
			.build();
	}

	protected Account getSavedAccount(String username, String password) {
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		return accountRepository.save(account);
	}

}
