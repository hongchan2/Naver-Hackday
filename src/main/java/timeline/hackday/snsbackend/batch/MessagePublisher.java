package timeline.hackday.snsbackend.batch;

import java.lang.reflect.Method;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;


public class MessagePublisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private static final String topicExchange = "sns-batch-exchange";
	
	protected void sendMessage(String interfaceName, Method method, Object[] parameters) {
		sendMessage(interfaceName, method.getName(), method.getParameterTypes(), parameters);
	}

	protected void sendMessage(String interfaceName, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
		Message message = new Message(interfaceName, methodName, parameterTypes, parameters);
		rabbitTemplate.convertAndSend(topicExchange, "sns.batch", message);
	}
}
