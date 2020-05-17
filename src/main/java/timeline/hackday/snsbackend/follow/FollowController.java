package timeline.hackday.snsbackend.follow;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class FollowController {

	@Autowired
	FollowService followService;

	@PostMapping("/follow")
	public ResponseEntity createFollow(@RequestBody @Valid FollowDto followDto, Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		boolean isComplete = followService.follow(followDto);
		return isComplete ? ResponseEntity.status(HttpStatus.CREATED).body(followDto) :
			ResponseEntity.badRequest().build();
	}
}
