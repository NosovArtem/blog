<#include "security.ftl">

<div class="card-columns">
    <#list messages as message>
      <div class="card my-3" style="width: 18rem;">
        <div>
            <#if message.filename??>
              <img class="card-img-top" src="${message.filename}">
            </#if>
        </div>
        <div class="m-2">
          <span>${message.text}</span>
          <i>${message.tag}</i>
        </div>
        <div class="card-footer text-muted">
           <a href="/user-messages/${message.author.id}">${message.author.username}</a>
          <#if message.author.id == currentUserId>
            <a class="btn btn-primary" href="/user-messages/${message.author.id}?messageId=${message.id}">Edit</a>
          </#if>
        </div>

      </div>
    <#else>
      No message
    </#list>
</div>