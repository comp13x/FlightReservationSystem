<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="flightForm" type="com.gerardrama.entity.form.FlightCreateForm" -->
<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Create a new flight</title>
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

<h1>Create a new flight</h1>
<div class="text-left d-flex justify-content-left">
    <form class="w-25" role="form" name="flightForm" action="" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="form-group">
            <label for="fromFlight">From Flight</label>
            <input class="form-control" type="text" name="fromFlight" id="fromFlight" required value="${flightForm.fromFlight}"/>
        </div>

        <div class="form-group">
            <label for="toFlight">To Flight</label>
            <input class="form-control" type="text" name="toFlight" id="toFlight" required value="${flightForm.toFlight}"/>
        </div>

        <div class="form-outline datepicker">
            <label for="departureDate">Departure Date</label>
            <input class="form-control" type="date" name="departureDate" id="departureDate" required value="${flightForm.departureDate}"/>
        </div>

        <div class="form-outline datepicker">
            <label for="arrivalDate">Arrival Date</label>
            <input class="form-control" type="date" name="arrivalDate" id="arrivalDate" required value="${flightForm.arrivalDate}"/>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
    </form>
</div>

<@spring.bind "flightForm" />
<#if spring.status.error>
    <ul>
        <#list spring.status.errorMessages as error>
            <li>${error}</li>
        </#list>
    </ul>
</#if>

</body>
</html>