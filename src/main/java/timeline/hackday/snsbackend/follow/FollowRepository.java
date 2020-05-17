package timeline.hackday.snsbackend.follow;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	Optional<Follow> findBySrc_IdAndDest_Id(Long srcId, Long destId);

	void deleteBySrc_IdAndDest_Id(Long srcId, Long destId);
}
