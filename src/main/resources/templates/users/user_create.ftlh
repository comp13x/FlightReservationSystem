<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="userForm" type="com.gerardrama.entity.form.UserCreateForm" -->
<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Create a new user</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Flight Reservation System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <#if !currentUser??>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">
                        <a class="nav-link" href="/login">Log In <span class="sr-only">(current)</span></a>
                    </button>
                </li>
            </ul>
        </#if>

        <#if currentUser?? && currentUser.role == "ADMIN">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/users">All Users<span class="sr-only">(current)</span></a>
                </li>
            </ul>

            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/user/create">Create Account<span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </#if>

        <#if currentUser??>
            <ul class="navbar-nav ml-auto">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="/user/${currentUser.id}/trips">My Trips<span class="sr-only">(current)</span></a>
                    </li>
                </ul>
                <li class="nav-item active">
                    <a class="nav-link" href="/user/${currentUser.id}">My Account<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item active">
                    <form class="form-inline my-2 my-lg-0" action="/logout" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Log out</button>
                    </form>
                </li>
            </ul>
        </#if>
    </div>
</nav>

<h1>Create a new user</h1>

<div class="text-left d-flex justify-content-left">
    <form class="w-25" role="form" name="userForm" action="" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="form-group">
            <label for="firstName">First Name</label>
            <input class="form-control" type="text" name="firstName" id="firstName" value="${userForm.firstName}" required autofocus/>
        </div>
        <div class="form-group">
            <label for="lastName">Last Name</label>
            <input class="form-control" type="text" name="lastName" id="lastName" value="${userForm.lastName}" required autofocus/>
        </div>
        <div class="form-outline datepicker">
            <label for="dateOfBirth">Date of Birth</label>
            <input class="form-control" type="date" name="dateOfBirth" id="dateOfBirth" value="${userForm.dateOfBirth}" required autofocus/>
        </div>
        <div class="form-group">
            <label for="email">Email address</label>
            <input class="form-control" type="email" name="email" id="email" value="${userForm.email}" required autofocus/>
        </div>
        <div class="form-group">
            <label for="phone">Phone</label>
            <input class="form-control" type="tel" name="phone" id="phone" value="${userForm.phone}" required autofocus/>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input class="form-control" type="password" name="password" id="password" required/>
        </div>
        <div class="form-group">
            <label for="passwordRepeated">Repeat</label>
            <input class="form-control" type="password" name="passwordRepeated" id="passwordRepeated" required/>
        </div>
        <div class="form-group">
            <label for="role">Role</label>
            <select class="form-control" name="role" id="role" required>
                <option <#if userForm.role == 'USER'>selected</#if>>USER</option>
                <option <#if userForm.role == 'ADMIN'>selected</#if>>ADMIN</option>
            </select>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
    </form>
</div>

<@spring.bind "userForm" />
<#if spring.status.error>
<ul>
    <#list spring.status.errorMessages as error>
        <li>${error}</li>
    </#list>
</ul>
</#if>

</body>
</html>