package timeline.hackday.snsbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.account.AccountRepository;
import timeline.hackday.snsbackend.follow.Follow;
import timeline.hackday.snsbackend.follow.FollowRepository;

@Component
public class DataRunner implements ApplicationRunner {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	FollowRepository followRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// IntStream.range(1, 100).forEach(this::createAccount);
		// LongStream.range(2, 40).forEach(this::createFollow);
	}

	protected void createAccount(int i) {
		Account account = new Account();
		account.setUsername("user" + i);
		account.setPassword("$$$$");
		accountRepository.save(account);
	}

	public void createFollow(Long i) {
		Follow follow = new Follow();
		follow.setSrc(accountRepository.findById(1L).get());
		follow.setDest(accountRepository.findById(i).get());
		followRepository.save(follow);
	}
}
