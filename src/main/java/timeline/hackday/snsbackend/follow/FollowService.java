package timeline.hackday.snsbackend.follow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import timeline.hackday.snsbackend.account.Account;
import timeline.hackday.snsbackend.account.AccountRepository;
import timeline.hackday.snsbackend.batch.IBatchService;
import timeline.hackday.snsbackend.board.Board;
import timeline.hackday.snsbackend.board.BoardRepository;
import timeline.hackday.snsbackend.follow.projection.FollowerSummary;
import timeline.hackday.snsbackend.follow.projection.FollowingSummary;
import timeline.hackday.snsbackend.timeline.Timeline;
import timeline.hackday.snsbackend.timeline.TimelineRepository;

@Service
public class FollowService {

	private final FollowRepository followRepository;

	private final AccountRepository accountRepository;

	private final BoardRepository boardRepository;

	private final TimelineRepository timelineRepository;

	private final IBatchService batchService;

	public FollowService(FollowRepository followRepository,
		AccountRepository accountRepository, BoardRepository boardRepository,
		TimelineRepository timelineRepository, IBatchService batchService) {
		this.followRepository = followRepository;
		this.accountRepository = accountRepository;
		this.boardRepository = boardRepository;
		this.timelineRepository = timelineRepository;
		this.batchService = batchService;
	}

	@Transactional
	public boolean follow(FollowDto followDto) {
		Optional<Account> optionalSrcAccount = accountRepository.findById(followDto.getSrcId());
		Optional<Account> optionalDestAccount = accountRepository.findById(followDto.getDestId());

		if (optionalSrcAccount.isEmpty() || optionalDestAccount.isEmpty()) {
			return false;
		}

		Follow follow = mapToFollow(optionalSrcAccount.get(), optionalDestAccount.get());
		followRepository.save(follow);

		// 팔로우하는 유저(dest)의 게시물을 src 유저의 타임라인에 추가
		long boardCnt = boardRepository.countByAccount_Id(followDto.getDestId());
		if (boardCnt < 200L) {
			// 실시간 처리
			List<Board> boardList = boardRepository.findByAccount_Id(followDto.getDestId());
			boardList.forEach(board -> {
				Timeline timeline = new Timeline();
				timeline.setAccount(optionalSrcAccount.get());
				timeline.setBoard(board);
				timelineRepository.save(timeline);
			});
		} else {
			// Request to batch service
			batchService.addTimelinesToFollower(followDto.getSrcId(), followDto.getDestId());
		}

		return true;
	}

	/*
	 * projection을 사용해 join을 하여 두 번의 쿼리만 발생하도록 함
	 * 1. {id, name}  2. total count
	 */
	public Page<FollowingSummary> getFollowingPage(Long srcId, Pageable pageable) {
		return followRepository.findBySrc_Id(srcId, pageable);
	}

	public Page<FollowerSummary> getFollowerPage(Long destId, Pageable pageable) {
		return followRepository.findByDest_Id(destId, pageable);
	}

	@Transactional
	// TODO Optimize Query (Do Lazy Fetch)
	public boolean unfollow(Long srcId, Long destId) {
		Optional<Follow> optionalFollow = followRepository.findBySrc_IdAndDest_Id(srcId, destId);

		if (optionalFollow.isEmpty()) {
			return false;
		}

		followRepository.deleteById(optionalFollow.get().getId());

		// 팔로우 취소하는 유저(dest)의 게시물을 src 유저의 타임라인에서 삭제
		long boardCnt = boardRepository.countByAccount_Id(destId);
		if (boardCnt < 200L) {
			// 실시간 처리
			timelineRepository.deleteByAccount_IdAndBoard_Account_Id(srcId, destId);
		} else {
			// Request to batch service
			batchService.removeTimelinesToFollower(srcId, destId);
		}

		return true;
	}

	private Follow mapToFollow(Account srcAccount, Account destAccount) {
		Follow follow = new Follow();
		follow.setSrc(srcAccount);
		follow.setDest(destAccount);
		return follow;
	}

}
