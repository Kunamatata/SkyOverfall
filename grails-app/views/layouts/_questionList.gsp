<g:each in="${questions}" var="question">
        <g:if test="${question.value > 0}">
<div class="question-summary green-left-border">
                </g:if>
                <g:else>
                <div class="question-summary">
                </g:else>

                <div class="views">${question.key.views}
                <div>views</div></div>

                <div class="answers">
                    ${question.value}
                    <div class="answer-size">${question.value > 1 ? "answers" : "answer"}</div>
                </div>
                <div class="votes">${question.key.reputation}
                <div>votes</div></div>
                <div class="question-title">
                    <g:link controller="question" action="show" id="${question.key.id}"> ${question.key.title}</g:link>
                </div>
                <div class="question-author">
                    <p>Posted by : <a href="/user/${question.key.user.username}">${question.key.user.username}</a> </p>
                </div>
                <div class="tags">
                    <g:each in="${question.key.tags}" var="tag">
                        <div class="post-tag">${tag.name}</div>
                    </g:each>
                </div>
         </div>
</g:each>