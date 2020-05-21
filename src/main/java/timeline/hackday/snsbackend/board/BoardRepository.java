package timeline.hackday.snsbackend.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

	long countByAccount_Id(Long id);

	List<Board> findByAccount_Id(Long id);

}
