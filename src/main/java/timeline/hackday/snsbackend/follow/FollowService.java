package timeline.hackday.snsbackend.follow;

import java.util.Optional;

import org.springframework.stereotype.Service;

import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.account.AccountRepository;

@Service
public class FollowService {

	private final FollowRepository followRepository;

	private final AccountRepository accountRepository;

	public FollowService(FollowRepository followRepository,
		AccountRepository accountRepository) {
		this.followRepository = followRepository;
		this.accountRepository = accountRepository;
	}

	public boolean follow(FollowDto followDto) {
		Optional<Account> optionalSrcAccount = accountRepository.findById(followDto.getSrcId());
		Optional<Account> optionalDestAccount = accountRepository.findById(followDto.getDestId());

		if (optionalSrcAccount.isEmpty() || optionalDestAccount.isEmpty()) {
			return false;
		}

		Follow follow = mapToFollow(optionalSrcAccount.get(), optionalDestAccount.get());
		followRepository.save(follow);
		return true;
	}

	private Follow mapToFollow(Account srcAccount, Account destAccount) {
		Follow follow = new Follow();
		follow.setSrc(srcAccount);
		follow.setDest(destAccount);
		return follow;
	}
}
