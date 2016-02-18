package skyoverfall

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

@Secured('IS_AUTHENTICATED_ANONYMOUSLY')
class QuestionController {

    def springSecurityService

    static allowedMethods =  [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Question.list(params), model:[questionCount: Question.count()]
    }

    def show(Question question) {
        def user = springSecurityService.currentUser;
        question.views = question.views + 1;
        question.save();
        return [question : question, currentUser : user]
    }

    def create() {
        respond new Question(params)
    }

    @Transactional
    def save(Question question) {
        if (question == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (question.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond question.errors, view:'create'
            return
        }

        question.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'question.label', default: 'Question'), question.id])
                redirect question
            }
            '*' { respond question, [status: CREATED] }
        }
    }

    def edit(Question question) {
        respond question
    }

    @Transactional
    def update(Question question) {
        if (question == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (question.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond question.errors, view:'edit'
            return
        }

        question.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'question.label', default: 'Question'), question.id])
                redirect question
            }
            '*'{ respond question, [status: OK] }
        }
    }

    @Transactional
    def delete(Question question) {

        if (question == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        question.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'question.label', default: 'Question'), question.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    @Secured('IS_AUTHENTICATED_FULLY')
    def submitQuestion()
    {
        String[] listeTags = params.tags.trim().split(",");
        Question question = new Question();
        bindData(question, params, [exclude: ['tags']]);

        flash.message = [:];
        for(String tagName : listeTags)
        {
            Tag tag;
            tag = Tag.findByName(tagName);

            if(tag == null)
            {
                tag = new Tag(name: tagName, description: "Test"); /*Gérer la description plus tard*/
            }
            question.addToTags(tag);
        }

        if (question == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (question.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond question.errors, view:'create'
            return
        }

        if(!question.save()) {
            flash.message = [error : "Oops", type: "error"];
            redirect controller:'question', action: 'create'
        }
        else{
            flash.message = [success : "Your question is online", type: "success"];
            redirect controller:'question', action: 'show', id: question.id
        }
    }

    @Secured('IS_AUTHENTICATED_ANONYMOUSLY')
    def searchQuestionsByTags(){

        def questionsAnswers = [:]
        def questions = null
        if(params.tags != ""){
            def tags = Tag.findAllByName(params.tags)
             questionsAnswers = [:];
             questions = Question.executeQuery("""
                            select q
                            from Question q join q.tags as t
                            where t in (:tags)""", [tags : tags]);


            for(question in questions) {
                questionsAnswers[question] = Answer.countByQuestion(question);
            }
        }
        else{
            questions = Question.findAll()
            questionsAnswers = [:];
            for(question in questions) {
                questionsAnswers[question] = Answer.countByQuestion(question);
            }
        }

        render(template: '../layouts/questionList', model:[questions: questionsAnswers])
    }

    @Secured('IS_AUTHENTICATED_FULLY')
    def editQuestion(){
        def currentUser = springSecurityService.currentUser;
        Question question = Question.findById(params.questionID);
        Long userID = Long.parseLong(params.userID);
        if(userID == currentUser.id)
        {   
            question.text = params.text;
            question.lastUpdated = new Date();
            question.save(failOnError:true)
        }
        
        render question.text
    }


}