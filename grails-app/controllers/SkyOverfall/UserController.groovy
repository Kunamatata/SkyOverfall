package skyoverfall
import org.springframework.security.access.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured
import grails.plugin.springsecurity.SpringSecurityService

@Secured(['ROLE_ADMIN'])
class UserController extends grails.plugin.springsecurity.ui.UserController {

    def springSecurityService

    @Secured(['permitAll'])
    def createUser(){
        User user = new User(params);
        flash.message = [:];
        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'edit'
            return
        }

        if(!user.save()) {

            flash.message = [error : "Oops", type: "error"];
            redirect controller:'home', action: 'index'
        }
        else{
            flash.message = [success : "Your user was saved!", type: "success"];
            redirect controller:'home', action: 'index'
        }
    }

    @Secured(['permitAll'])
    def profile(){

        def user = User.findByUsername(params.name)
        if(user == null){
            return redirect(controller: 'home', action:'index', params: [error : "8041"])
        }
        def numberOfPosts = Post.countByUser(user);
        def userDescription = user.profileDescription;
        def questions = Question.findAllByUser(user);
        def answers = Answer.findAllByUser(user);
        def comments = Comment.findAllByUser(user);
        def avatarLink = user.avatar;

        def reputation = 0
        int rep = 0;

         /*Determine user reputation on answers*/
         for(int i = 0 ; i < answers.votes.size ; ++i){
                 for(int j = 0 ; j < answers.votes[i].size() ; ++j){
                     rep =  answers.votes[i][j].type
                     if(rep == 1){
                         reputation += 5;
                     }
                     else if(rep == -1){
                         reputation -= 2;
                     }
                 }
         }

          /*Determine user reputation on questions*/
         for(int i = 0 ; i < questions.votes.size ; ++i){
                 for(int j = 0 ; j < questions.votes[i].size() ; ++j){
                     rep =  questions.votes[i][j].type
                     if(rep == 1){
                         reputation += 10;
                     }
                     else if(rep == -1){
                         reputation -= 3;
                     }
                 }
         }

        user.reputation = reputation;
        user.save();

        questions = questions.sort {it.dateCreated}.reverse()
        answers = answers.sort {it.dateCreated}.reverse()

        return [user : user, numberOfPosts : numberOfPosts, avatarLink : avatarLink, profileDescription: userDescription, questions : questions, answers : answers, comments : comments];
    }

    @Secured('IS_AUTHENTICATED_FULLY')
    def list(){
        def userList = null

        userList =  User.findAll();

        return  [users : userList]
    }

    @Secured('IS_AUTHENTICATED_FULLY')
    def searchUser(){
        def users = null
        if(params.username != null)
            users = User.findAllByUsernameLike(params.username + "%")
        else
            users =  User.findAll();

        render(template: '../layouts/listUsers', model:[users: users])
    }
}
