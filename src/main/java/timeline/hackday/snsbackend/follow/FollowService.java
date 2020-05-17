package timeline.hackday.snsbackend.follow;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	// TODO Minimize Query
	public boolean unfollow(Long srcId, Long destId) {
		Optional<Follow> optionalFollow = followRepository.findBySrc_IdAndDest_Id(srcId, destId);

		if (optionalFollow.isEmpty()) {
			return false;
		}

		followRepository.deleteBySrc_IdAndDest_Id(srcId, destId);
		return true;
	}

	private Follow mapToFollow(Account srcAccount, Account destAccount) {
		Follow follow = new Follow();
		follow.setSrc(srcAccount);
		follow.setDest(destAccount);
		return follow;
	}

}
