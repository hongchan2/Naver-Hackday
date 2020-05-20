package timeline.hackday.snsbackend.batch;


public interface IBatchService {

	public void addTimelinesToFollowee(long boardId, long accountId);
	
	public void removeTimelinesToFollowee(long boardId, long accountId);
	
	public void addTimelinesToFollower(long srcId, long destId);
	
	public void removeTimelinesToFollower(long srcId, long destId);
	
}
