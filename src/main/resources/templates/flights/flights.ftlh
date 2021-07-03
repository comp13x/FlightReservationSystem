<#-- @ftlvariable name="flights" type="java.util.List<com.gerardrama.entity.Flight>" -->
<#-- @ftlvariable name="user" type="com.gerardrama.entity.User" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Flights</title>
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

<div class="container">
    <div class="panel panel-primary">
        <h1>List of Flights</h1>
    </div>
    <div class="panel-body">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Departure Date</th>
                <th>Arrival Date</th>
                <th>From</th>
                <th>To</th>
            </tr>
            </thead>
            <tbody>
            <#list flights as flight>
                <tr>
                    <td>${flight.departureDate}</td>
                    <td>${flight.arrivalDate}</td>
                    <td>${flight.fromFlight}</td>
                    <td>${flight.toFlight}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <a href="flights/create" class="btn btn-primary btn-lg enabled" role="button" aria-disabled="true">Add Flight</a>
</div>
</table>
</body>
</html>



