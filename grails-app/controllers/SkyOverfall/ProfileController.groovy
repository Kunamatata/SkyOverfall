package skyoverfall

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured
import grails.plugin.springsecurity.SpringSecurityService

@Secured('IS_AUTHENTICATED_FULLY')
class ProfileController {

    def springSecurityService

    def index() {
        def user = springSecurityService.currentUser;
        def numberOfPosts = Post.countByUser(user);
        def userDescription = user.profileDescription;
        def questions = Question.findAllByUser(user);
        def answers = Answer.findAllByUser(user);
        def avatarLink = user.avatar;

        questions = questions.sort {it.dateCreated}.reverse()
        answers = answers.sort {it.dateCreated}.reverse()
        return [user : user, numberOfPosts : numberOfPosts, avatarLink : avatarLink, profileDescription: userDescription, questions : questions, answers : answers];
    }

    def updateProfileDescription(){
         def user = springSecurityService.currentUser;

         user.profileDescription = params.profileDescription;
         user.save();

         render user.profileDescription; /*Return value to ajax call*/
    }

    def updateUserAvatar(){
        def user = springSecurityService.currentUser;

         user.avatar = params.avatarUrl;
         user.save();

         println user.avatar;
         render user.avatar; /*Return value to ajax call*/
    }
}
