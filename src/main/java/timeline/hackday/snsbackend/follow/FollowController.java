package timeline.hackday.snsbackend.follow;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@DeleteMapping("/follow/{srcId}/{destId}")
	public ResponseEntity deleteFollow(@PathVariable Long srcId, @PathVariable Long destId) {
		boolean isComplete = followService.unfollow(srcId, destId);
		return isComplete ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
}
