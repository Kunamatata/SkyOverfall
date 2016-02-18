package skyoverfall

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

@Secured('IS_AUTHENTICATED_ANONYMOUSLY')

class PostController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Post.list(params), model:[postCount: Post.count()]
    }

    def show(Post post) {
        respond post
    }

    def create() {
        respond new Post(params)
    }

    @Transactional
    def save(Post post) {
        if (post == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (post.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond post.errors, view:'create'
            return
        }

        post.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'post.label', default: 'Post'), post.id])
                redirect post
            }
            '*' { respond post, [status: CREATED] }
        }
    }

    def edit(Post post) {
        respond post
    }

    @Transactional
    def update(Post post) {
        if (post == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (post.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond post.errors, view:'edit'
            return
        }

        post.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'post.label', default: 'Post'), post.id])
                redirect post
            }
            '*'{ respond post, [status: OK] }
        }
    }

    @Transactional
    def delete(Post post) {

        if (post == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        post.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'post.label', default: 'Post'), post.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'post.label', default: 'Post'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

     @Secured('IS_AUTHENTICATED_FULLY')
    def upvote(){

        def user = springSecurityService.currentUser;
        def postId = params.idOfPost;

                Post post = Post.findById(postId);
                Vote voteExists = Vote.findWhere(post : post, user : user)


                if(!voteExists){
                    println "Votes : " + post.votes
                    Vote vote = new Vote(type: 1, post : post, user : user)
                    post.addToVotes(vote)
                    user.addToVotes(vote)
                    post.reputation += 1;
                }

                else if(voteExists.type == -1){
                    post.removeFromVotes(voteExists)
                    voteExists.type = 0;
                    post.addToVotes(voteExists)
                    user.addToVotes(voteExists)
                    post.reputation += 1;

                }
                else if(voteExists.type == 0){
                    post.removeFromVotes(voteExists)
                    voteExists.type = 1;
                    post.addToVotes(voteExists)
                    user.addToVotes(voteExists)
                    post.reputation += 1;
                }
                post.save()
                user.save();
                render post.reputation

    }

    @Secured('IS_AUTHENTICATED_FULLY')
    def downvote(){

        def user = springSecurityService.currentUser;
        def postId = params.idOfPost;

            Post post = Post.findById(postId);
            Vote voteExists = Vote.findWhere(post : post, user : user)

                if(!voteExists){
                    println post.votes
                    Vote vote = new Vote(type: -1, post : post, user : user)
                    post.addToVotes(vote)
                    user.addToVotes(vote)
                    println voteExists
                    post.reputation -= 1
                }
                else if(voteExists.type == 1)
                {
                    post.removeFromVotes(voteExists)
                    voteExists.type = 0;
                    post.addToVotes(voteExists)
                    user.addToVotes(voteExists)

                    post.reputation -= 1
                }

                else if (voteExists.type == 0){
                    post.removeFromVotes(voteExists)
                    voteExists.type = -1;
                    post.addToVotes(voteExists)
                    user.addToVotes(voteExists)

                    post.reputation -= 1
                }

                post.save();
                user.save();
                render post.reputation

    }
}
