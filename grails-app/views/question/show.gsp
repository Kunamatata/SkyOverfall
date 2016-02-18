<!DOCTYPE html>
<html>
   <head>
      <meta name="layout" content="main" />
      <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
      <title>
         <g:message code="default.show.label" args="[entityName]" />
      </title>
   </head>
   <body>
      <div id="question-header">
         ${question.title}
      </div>
      <div id="question-body">
         <div class="votecell">
            <i class="fa fa-arrow-up upvote" data-post-id="${question.id}" data-post-type="question"></i>
            <span id="question-vote-counter">${question.reputation}</span>
            <i class="fa fa-arrow-down downvote" data-post-id="${question.id}" data-post-type="question"></i>
         </div>
         <div class="question-text"> <pre>${question.text}</pre></div>
         <div class="question-tags">
            <g:each in="${question.tags}" var="tag">
               <div class="post-tag">${tag.name}</div>
            </g:each>
         </div>
            <div class="author-container">
               <div class="author">
                  <prettytime:display date="${question.dateCreated}" />
                  <div class="author-image" style="background-image: url(${question.user.avatar}"></div>
                  <div class="author-name">
                    <a href="/user/${question.user.username}">${question.user.username}</a>
                  </div>
               </div>
            </div>
            <g:each var="comment" in="${question.comments}">
               <div class="comment-container" id="comment-${comment.id}">
                  ${comment.text}
                  <div class="author-container">
               <div class="author">
                  <prettytime:display date="${comment.dateCreated}" />
                  <div class="author-image" style="background-image: url(${comment.user.avatar}"></div>
                  <div class="author-name"><a href="/user/${comment.user.username}">${comment.user.username}</a></div>
               </div>
            </div>

               </div>
            </g:each>
            <a href="#" id="add-comment-link" class="comment-link">add a comment</a>
                <g:form  name="comment-form" controller="comment" action="submitComment" method="POST">
                   <g:hiddenField name="user.id" value="${sec.loggedInUserInfo(field:"id")}" />
                   <g:hiddenField name="post.id" value="${question.id}" />
                   <g:hiddenField name="question.id" value="${question.id}" />
                   <g:textArea type="text" name="text" rows="15" cols="92" required="true"/>
                   <g:submitButton name="submitComment" value="Post Comment" />
                </g:form>
      </div>
      <p class="number-answers">Answers : ${question.answers.size()}</p>
      <g:each in="${question.answers}" var="answer">
         <div class="answer-body" id="${answer.id}">
            <div class="votecell">
               <i class="fa fa-arrow-up upvote" data-post-id="${answer.id}" data-post-type="answer"></i>
               <span id="answer-vote-counter">${answer.reputation}</span>
               <i class="fa fa-arrow-down downvote" data-post-id="${answer.id}" data-post-type="answer"></i>
             </div>
            <div class="answer-text">
               <pre>${answer.text}</pre>
            </div>
            <div class="author-container">
            <div class="author">
               <prettytime:display date="${answer.dateCreated}" />
               <div class="author-image" style="background-image: url(${answer.user.avatar}"></div>
               <div class="author-name">
                <a href="/user/${answer.user.username}">${answer.user.username}</a>
               </div>
            </div>
            </div>
                    <g:each var="comment" in="${answer.comments}">
               <div class="comment-container" id="comment-${comment.id}">
                  <pre>${comment.text}</pre>
                  <div class="author-container">
               <div class="author">
                  <prettytime:display date="${comment.dateCreated}" />
                  <div class="author-image" style="background-image: url(${comment.user.avatar}"></div>
                  <div class="author-name"><a href="/user/${comment.user.username}">${comment.user.username}</a></div>
               </div>
            </div>

               </div>
            </g:each>
            <a href="#" id="add-comment-link" class="comment-link">add a comment</a>
                <g:form  name="comment-form" controller="comment" action="submitComment" method="POST">
                   <g:hiddenField name="user.id" value="${sec.loggedInUserInfo(field:"id")}" />
                   <g:hiddenField name="question.id" value="${question.id}" />
                   <g:hiddenField name="post.id" value="${answer.id}" />
                   <g:textArea type="text" name="text" rows="15" cols="92" required="true"/>
                   <g:submitButton name="submitComment" value="Post Comment" />
                </g:form>
         </div>

      </g:each>
      <h3 style="margin-top:15px;"> Do you have the answer?</h3>
      <g:form  class="answer-form" controller="answer" action="createAnswer" method="POST">
         <g:hiddenField name="user.id" value="${sec.loggedInUserInfo(field:"id")}" />
         <g:hiddenField name="question.id" value="${question.id}" />
         <g:textArea type="text" name="text" rows="15" cols="92" required="true"/>
         <g:submitButton name="submitAnswer" value="Post Answer" class="ask-question" />
      </g:form>
   </body>
</html>