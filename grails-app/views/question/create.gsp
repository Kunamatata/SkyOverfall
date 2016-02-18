<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>

   <g:form  class="question-form" controller="question" action="submitQuestion" method="POST">

   <g:hiddenField name="user.id" value="${sec.loggedInUserInfo(field:"id")}" />
  <label>Title</label> <g:textField class="question-title-label" name="title" placeholder="What's up in your life? Be specific with your question." required="true"/>
    <g:textArea type="text" name="text" required="true"/>
    <label>Tags</label>
    <g:textField type="text" name="tags" required="true"/>
      <g:submitButton name="submit" value="Post Question" class="ask-question"/>
    </g:form>
        </div>
    </body>
</html>
