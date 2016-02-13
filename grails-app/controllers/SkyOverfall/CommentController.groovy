package skyoverfall

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

@Secured('IS_AUTHENTICATED_FULLY')
class CommentController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Comment.list(params), model:[commentCount: Comment.count()]
    }

    def show(Comment comment) {
        respond comment
    }

    def create() {
        respond new Comment(params)
    }

    @Transactional
    def save(Comment comment) {
        if (comment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond comment.errors, view:'create'
            return
        }

        comment.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect comment
            }
            '*' { respond comment, [status: CREATED] }
        }
    }

    def edit(Comment comment) {
        respond comment
    }

    @Transactional
    def update(Comment comment) {
        if (comment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond comment.errors, view:'edit'
            return
        }

        comment.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect comment
            }
            '*'{ respond comment, [status: OK] }
        }
    }

    @Transactional
    def delete(Comment comment) {

        if (comment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        comment.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }


    def submitComment(){
        Comment comment = new Comment()
        comment.text = params.text
        Post post = Post.findById(params.post.id);
        post.addToComments(comment);
        comment.post = post
        comment.user = springSecurityService.currentUser

      if (comment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond post.errors, view:'create'
            return
        }

        if(!comment.save()) {
            flash.message = [error : "Oops couldn't save your comment!", type: "error"];
            redirect controller:'question', action: 'show', id: params.question.id
        }
        else{

            post.save();
            flash.message = [success : "Your comment was added", type: "success"];
            redirect controller:'question', action: 'show', id: params.question.id
        }
    }
}
