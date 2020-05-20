package timeline.hackday.snsbackend;

import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.account.AccountRepository;
import timeline.hackday.snsbackend.board.BoardDto;
import timeline.hackday.snsbackend.board.BoardService;
import timeline.hackday.snsbackend.follow.FollowDto;
import timeline.hackday.snsbackend.follow.FollowService;

@Component
public class InitDataRunner implements ApplicationRunner {

	private final AccountRepository accountRepository;

	private final BoardService boardService;

	private final FollowService followService;

	public InitDataRunner(AccountRepository accountRepository, BoardService boardService,
		FollowService followService) {
		this.accountRepository = accountRepository;
		this.boardService = boardService;
		this.followService = followService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// user1 ~ user1000 생성
		IntStream.range(1, 1001).forEach(this::createAccount);
		System.out.println("1) 유저 생성 완료");

		// user1 ~ user2은 100개의 게시물을 가짐
		IntStream.range(1, 3).forEach(i ->
			IntStream.range(1, 101).forEach(j -> this.createBoard(i))
		);
		System.out.println("2) 게시물 생성 완료");

		/*
			user11 ~ user1000 이 user1 ~ user 2을 follow
			-> user1 ~ user2은 990명을 팔로워를 가짐
		 */
		LongStream.range(1, 3).forEach(i ->
			LongStream.range(11, 1001).forEach(j -> this.createFollow(j, i))
		);
		System.out.println("3) 팔로우 관계 생성 완료");

		// user1 ~ user2는 10개의 게시물을 추가로 생성
		IntStream.range(1, 3).forEach(i ->
			IntStream.range(1, 10).forEach(j -> this.createBoard(i))
		);
		System.out.println("4) 게시물 추가 생성 완료");

	}

	private void createAccount(int i) {
		Account account = new Account();
		account.setUsername("user" + i);
		account.setPassword("$$$$");
		accountRepository.save(account);
	}

	private void createBoard(int i) {
		Account account = accountRepository.findByUsername("user" + i);

		BoardDto boardDto = BoardDto.builder()
			.title("test title")
			.content("test content")
			.createTime(LocalDateTime.now())
			.accountId(account.getId())
			.build();
		boardService.createBoard(boardDto);
	}

	private void createFollow(Long srcId, Long destId) {
		FollowDto followDto = FollowDto.builder()
			.srcId(srcId)
			.destId(destId)
			.build();
		followService.follow(followDto);
	}
}
