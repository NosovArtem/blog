<#import "parts/common.ftl" as c>

<@c.page>
  <div class="form-row">
    <div class="form-group col-md-6">
      <form method="get" action="/main" class="form-inline">
        <input type="text" name="filter" class="form-control" value="${filter?ifExists}"
               placeholder="Search by tag">
        <button type="submit" class="btn btn-primary ml-2">Search</button>
      </form>
    </div>
  </div>

  <button class="btn btn-primary" type="button" data-toggle="collapse"
          data-target="#collapseExample"
          aria-expanded="false" aria-controls="collapseExample">
    Add new message
  </button>
  <div class="collapse <#if message??>show</#if>" id="collapseExample">
    <form method="post" enctype="multipart/form-data">
      <div class="m-2">
        <div class="form-group">
          <input type="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                 value="<#if message??>${message.text}</#if>" name="text"
                 placeholder="Введите сообщение"/>
            <#if textError??>
              <div class="invalid-feedback">
                  ${textError}
              </div>
            </#if>
        </div>
        <div class="form-group">
          <input type="text" class="form-control ${(tagError??)?string('is-invalid', '')}"
                 value="<#if message??>${message.tag}</#if>" name="tag" placeholder="Тэг">
            <#if tagError??>
              <div class="invalid-feedback">
                  ${tagError}
              </div>
            </#if>
        </div>
        <div class="custom-file">
          <input type="file" name="file" class="custom-file-input" id="customFile">
          <labelclass class="custom-file-label" for="customFile">Choose file</labelclass>
        </div>
      </div>
      <input type="hidden" name="_csrf" value="${_csrf.token}"/>
      <div class="form-group">
        <button type="submit" class="btn btn-primary">Add</button>
      </div>
    </form>
  </div>

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
              ${message.authorName}
          </div>

        </div>
      <#else>
        No message
      </#list>
  </div>
</@c.page>