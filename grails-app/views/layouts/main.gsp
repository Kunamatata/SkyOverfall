<!doctype html>
<html lang="en" class="no-js">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <title>
         <g:layoutTitle default="Grails"/>
      </title>
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.css">
      <link href='https://fonts.googleapis.com/css?family=Lato' rel=' stylesheet' type='text/css'>
      <asset:stylesheet src="application.css"/>
      <asset:javascript src="application.js"/>

      <g:layoutHead/>
   </head>
   <body>
      <!-- Navbar -->
      <div id="navbar">
         <a href="/">
            <p class="web-title">SkyOverfall</p>
         </a>
         <sec:ifLoggedIn>
            <g:link class="logout-link" controller='logout'>Logout</g:link>
            <a href="/user/list" class="logout-link">Search Users</a>
            <a href="/profile"><div class="login-link"><sec:loggedInUserInfo  field='username'/></div></a>
         </sec:ifLoggedIn>
         <sec:ifNotLoggedIn>
            <div class="create-account" id="create-account">Create Account</div>
            <div class="login-link" id="login-link">Log in</div>
         </sec:ifNotLoggedIn>
      </div>
       <div id="popup-create-account">
            <div class="close-popup">X</div>
            <g:form  class="create-account-form" controller="user" action="createUser" method="POST">
               <label for="username">Username</label><br>
               <g:textField type="text" name="username" placeholder="username" required="true"/>
               <label for="password">Password</label><br>
               <g:passwordField  name="password"  placeholder="********"required="true"/>
               <g:submitButton name="submitUser" class="btn-blue" value="Create Account" />
            </g:form>
         </div>
<div id="popup-login">
<div class="close-popup">X</div>
         <form action="/login/authenticate" method="POST" id="loginForm" class="login-form" autocomplete="off">
            <label for="username">Username</label>
            <input type="text" name="username" placeholder="username" required="true">
            <label for="password">Password</label>
            <input type="password" name="password"  placeholder="********" required="true">
            <label for="remember_me">Remember Me</label>
            <input type="checkbox" class="chk" name="remember-me"><br>
            <input type="submit" id="submit" value="Login">
      </form>
      </div>
      <div id="container">
       <g:if test="${flash.message}">
   <g:if test="${flash.message.type == 'error'}">
      <div class="${flash.message.type}">${flash.message.error}</div>
    </g:if>
    <g:if test="${flash.message.type == 'success'}">
      <div class="${flash.message.type}">${flash.message.success}</div>
      </g:if>
    </g:if>
         <g:layoutBody/>
      </div>
      <div id="footer"></div>
   </body>
</html>