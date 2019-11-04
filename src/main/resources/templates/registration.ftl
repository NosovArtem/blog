<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<a> Add new user </a>
<@l.login "/registration" />
${message}
<a href="/login">Login</a>
<@c.page/>