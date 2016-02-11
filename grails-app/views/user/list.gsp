<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'answer.label', default: 'Answer')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
      <input class="col-8" type="text" placeholder="Username" id="searched-username" required="true" autocomplete="off"/><img src="https://stackoverflow.com/content/img/progress-dots.gif" id="is-typing-gif"/>
      <div id="listUserTemplate">
           <g:render template="/layouts/listUsers" model="[users : users]" />
      </div>
    </body>
</html>