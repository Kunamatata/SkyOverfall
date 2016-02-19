package skyoverfall

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured
import grails.plugin.springsecurity.SpringSecurityService

@Secured('IS_AUTHENTICATED_ANONYMOUSLY')
class HomeController {

    def index() {

    	List questions = Question.list(max:10);
      def questionsReponses = [:];

      if(params.error == "8041") // 8041 -> User not exist code
        flash.message = [error : "User does not exist!", type: "error"];
      else if(params.login_error == "1")
        flash.message = [error : "Wrong username or password!", type : "error"];
      else if(request.forwardURI == "/login/auth")
        flash.message = [error: "You need to log in to do that action", type: "error"];
    	for(question in questions) {
    		questionsReponses[question] = Answer.countByQuestion(question);
    	}
    	return [questions : questionsReponses];
    }
}