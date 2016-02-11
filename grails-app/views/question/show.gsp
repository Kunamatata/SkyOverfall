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
         <div class="question-text"> ${question.text}</div>
         <div class="question-tags">
            <g:each in="${question.tags}" var="tag">
               <div class="post-tag">${tag.name}</div>
            </g:each>
         </div>
                <div class="author">
               <prettytime:display date="${question.dateCreated}" />
               <div class="author-image" style="background-image: url(${question.user.avatar}"></div>
               <div class="author-name">${question.user.username}
               </div>
            </div>
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
               <p>${answer.text}</p>
            </div>
            <div class="author">
               <prettytime:display date="${answer.dateCreated}" />
               <div class="author-image" style="background-image: url(${answer.user.avatar}"></div>
               <div class="author-name">${answer.user.username}
               </div>
            </div>
         </div>
      </g:each>
      <h3 style="margin-top:15px;"> Do you have the answer?</h3>
      <g:form  class="answer-form" controller="answer" action="createAnswer" method="POST">
         <g:hiddenField name="user.id" value="${sec.loggedInUserInfo(field:"id")}" />
         <g:hiddenField name="question.id" value="${question.id}" />
         <g:textArea type="text" name="text" rows="15" cols="92" required="true"/>
         <g:submitButton name="submitAnswer" value="Post Answer" />
      </g:form>
   </body>
</html>