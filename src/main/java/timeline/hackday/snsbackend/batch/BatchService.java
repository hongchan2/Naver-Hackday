package timeline.hackday.snsbackend.batch;

import java.lang.reflect.Method;

import org.springframework.stereotype.Service;

import timeline.hackday.snsbackend.board.Board;
import timeline.hackday.snsbackend.follow.Follow;

@Service
public class BatchService extends MessagePublisher implements IBatchService{

//	@Override
//	public void addTimelinesToFollowee(long boardId, long userId) {
//		// create enclosed Object
//		Object enclosedObject = new Object() {};
//		Method method = enclosedObject.getClass().getEnclosingMethod();
//		// prepare parameters
//		Object[] parameters = new Object[method.getParameterCount()];
//		
//		parameters[0] = boardId;
//		parameters[1] = userId;
//		
//		String interfaceName = "IBatchService";
//		sendMessage(interfaceName, method, parameters);
//	}
	private String interfaceName = "IBatchService";
	@Override
	public void addTimelinesToFollowee(long boardId, long accountId) {
		// create enclosed Object
		Object enclosedObject = new Object() {};
		Method method = enclosedObject.getClass().getEnclosingMethod();
		// prepare parameters
		Object[] parameters = new Object[method.getParameterCount()];
		
		parameters[0] = boardId;
		parameters[1] = accountId;
		
		sendMessage(interfaceName, method, parameters);
	}

	@Override
	public void removeTimelinesToFollowee(long boardId, long accountId) {
		Object enclosedObject = new Object() {};
		Method method = enclosedObject.getClass().getEnclosingMethod();
		Object[] parameters = new Object[method.getParameterCount()];
		
		parameters[0] = boardId;
		parameters[1] = accountId;
		
		sendMessage(interfaceName, method, parameters);
	}

	@Override
	public void addTimelinesToFollower(long srcId, long destId) {
		Object enclosedObject = new Object() {};
		Method method = enclosedObject.getClass().getEnclosingMethod();
		Object[] parameters = new Object[method.getParameterCount()];
		
		parameters[0] = srcId;
		parameters[1] = destId;
		
		sendMessage(interfaceName, method, parameters);
	}

	@Override
	public void removeTimelinesToFollower(long srcId, long destId) {
		Object enclosedObject = new Object() {};
		Method method = enclosedObject.getClass().getEnclosingMethod();
		Object[] parameters = new Object[method.getParameterCount()];
		
		parameters[0] = srcId;
		parameters[1] = destId;
		
		sendMessage(interfaceName, method, parameters);
	}

	

}
