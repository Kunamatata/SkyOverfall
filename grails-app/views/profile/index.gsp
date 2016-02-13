<!DOCTYPE html>
<html>
   <head>
      <meta name="layout" content="main" />
      <g:set var="entityName" value="${message(code: 'answer.label', default: 'Answer')}" />
      <title>
         <g:message code="default.list.label" args="[entityName]" />
      </title>
   </head>
   <body>
      <div class="row">
         <div class="avatar-card">
            <div class="avatar" id="user-avatar" style="background-image: url('${avatarLink}');">
                <div class="change-avatar" id="change-avatar">Change avatar</div>
            </div>
            <div class="reputation">
               ${user.reputation} <span style="color: #6B6B6B; vertical-align: middle; font-size:11px;">REPUTATION</span>
            </div>
            <div class="reputation">
               ${numberOfPosts} <span style="color: #6B6B6B;vertical-align: middle;font-size:11px;">POSTS</span>
            </div>
         </div>

         <div class="basic-profile-info">
            <h1>
               <sec:loggedInUserInfo  field='username'/>
            </h1>
              <pre id="profile">${profileDescription}</pre>
               <div class="edit-profile-btn" id="edit-profile">Edit your profile description</div>
               <form  class="question-form" name="profile-form" id="profile-form">
                  <textarea placeholder="Write something about you" type="text" name="profileDescription" id="textarea-profile" required="true"></textarea>
                  <input type="submit" id="submit" name="submit" value="Update"></input>
               </form>
         </div>
      </div>
      <div class="avatar-card upload-avatar" id="upload-avatar">
      <form class="form-upload-avatar" id="avatar-form">
            <input type="text" placeholder="Image URL" id="avatar-url" required="true"/>
            <input type="submit" id="upload-avatar" name="submit" value="Update"></input>
     </form>
      </div>
          <div class="row">
          <div class="title-section">
            Questions (${questions.size})
          </div>
         <g:each in="${questions}" var="question">
           <div class="question-profile-item">
               <a href="/question/show/${question.id}">${question.title}</a>
               <div class="question-profile-date"><g:formatDate format="d MMM y h:m:s a" date="${question.dateCreated}"></g:formatDate></div>
           </div>
         </g:each>
    </div>

      <div class="row">
          <div class="title-section">
            Answers (${answers.size})
          </div>
         <g:each in="${answers}" var="answer">
           <div class="question-profile-item">
               <a href="/question/show/${answer.question.id}#${answer.id}">${answer.question.title}</a>
               <div class="question-profile-date"><g:formatDate format="d MMM y h:m:s a" date="${answer.dateCreated}"></g:formatDate></div>
           </div>
         </g:each>
    </div>
          <div class="row">
          <div class="title-section">
            Comments (${comments.size})
          </div>
         <g:each in="${comments}" var="comment">
           <div class="question-profile-item">
           <g:if test="${comment.post.class == skyoverfall.Question}">
              <a href="/question/show/${comment.post.id}#comment-${comment.id}">${comment.post.title}</a>
           </g:if>
           <g:else>
              <a href="/question/show/${comment.post.question.id}#comment-${comment.id}">${comment.post.question.title}</a>
           </g:else>
               <div class="question-profile-date"><g:formatDate format="d MMM y h:m:s a" date="${comment.dateCreated}"></g:formatDate></div>
           </div>
         </g:each>
    </div>
    </div>

   </body>
</html>