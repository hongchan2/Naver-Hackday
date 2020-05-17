package timeline.hackday.snsbackend.follow;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import timeline.hackday.snsbackend.follow.projection.FollowerSummary;
import timeline.hackday.snsbackend.follow.projection.FollowingSummary;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	Optional<Follow> findBySrc_IdAndDest_Id(Long srcId, Long destId);

	void deleteBySrc_IdAndDest_Id(Long srcId, Long destId);

	Page<FollowingSummary> findBySrc_Id(Long srcId, Pageable pageable);

	Page<FollowerSummary> findByDest_Id(Long destId, Pageable pageable);

}
