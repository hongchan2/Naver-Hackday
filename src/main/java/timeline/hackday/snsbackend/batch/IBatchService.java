package timeline.hackday.snsbackend.batch;

public interface IBatchService {

	void addTimelinesToFollowee(long boardId, long accountId);

	void removeTimelinesToFollowee(long boardId, long accountId);

	void addTimelinesToFollower(long srcId, long destId);

	void removeTimelinesToFollower(long srcId, long destId);

}
