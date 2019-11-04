<#macro login path>
<form method="post" action="${path}">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input type="submit" value="Sign In"/></div>
</form>
</#macro>

<#macro logout>
<div>
    <form method="post" action="/logout" >
        <input type="submit" value="Sign Out"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</div>
</#macro>