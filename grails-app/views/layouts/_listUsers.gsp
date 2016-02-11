    <g:each var="user" in="${users}">
      <div class="user-card col-8">
            <div class="user-list-image" style="background-image: url(${user.avatar}"></div>
            <span><a href="/user/${user.username}">${user.username}</a></span>
      </div>
    </g:each>