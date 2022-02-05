<button class="btn btn-primary" type="button" data-toggle="collapse"
        data-target="#collapseExample"
        aria-expanded="false" aria-controls="collapseExample">
    <#if message??>Message editor<#else>Add message</#if>
</button>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
  <form method="post" enctype="multipart/form-data">
    <div class="m-2">
      <div class="form-group">
        <input type="text" name="text"
               class="form-control ${(textError??)?string('is-invalid', '')}"
               value="<#if message??>${message.text}</#if>"
               placeholder="Введите сообщение"/>
          <#if textError??>
            <div class="invalid-feedback">
                ${textError}
            </div>
          </#if>
      </div>
      <div class="form-group">
        <input type="text" name="tag" class="form-control ${(tagError??)?string('is-invalid', '')}"
               value="<#if message??>${message.tag}</#if>" placeholder="Тэг">
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
    <input type="hidden" name="messageId" value="<#if message??>${message.id}</#if>"/>
    <div class="form-group">
      <button type="submit" class="btn btn-primary">Save message</button>
    </div>
  </form>
</div>
