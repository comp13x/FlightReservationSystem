<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.util.Optional<String>" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Log in</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <style>
        form-center-in {
            min-width: 20px;
            max-width: 20px;
            align-self: center;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Flight Reservation System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
</nav>

<div class="text-center d-flex justify-content-center" id="form-center-in">
    <form class="w-25" role="form" action="/login" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <h1 class="h3 mb-3 font-weight-normal">Sign in</h1>

        <label class="h6 mb-2 mt-2 font-weight-normal" for="email">Email address</label>
        <input name="email" type="email" id="email" class="form-control" placeholder="Email address" required autofocus>

        <label class="h6 mb-2 mt-2 font-weight-normal" for="password">Password</label>
        <input name="password" type="password" id="password" class="form-control" placeholder="Password" required>

        <div class="checkbox mb-3">
            <label for="remember-me">Remember me</label>
            <input type="checkbox" name="remember-me" id="remember-me"/>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        <p class="mt-5 mb-3 text-muted">&copy; 2021-2022</p>
    </form>
</div>

<#if error.isPresent()>
    <p>The email or password you have entered is invalid, try again.</p>
</#if>
</body>
</html>