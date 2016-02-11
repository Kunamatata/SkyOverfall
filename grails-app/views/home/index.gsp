<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'answer.label', default: 'Answer')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
         <g:link controller="question" action="create" class="ask-question">Ask Question</g:link>
        <input  type="text" placeholder="Filter questions by tags" id="search-questionbytags" required="true" autocomplete="off"/><img src="https://stackoverflow.com/content/img/progress-dots.gif" id="is-typing-gif"/>
      <div id="question-list-template">
      
          <g:render template="/layouts/questionList" model="[questions : questions]" />
      </div>
    </body>
</html>