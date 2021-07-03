<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="updateTrip" type="com.gerardrama.entity.form.TripCreateForm" -->
<#-- @ftlvariable name="trip" type="com.gerardrama.entity.Trip" -->
<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Create a new trip</title>
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
                    <#--                    <a class="nav-link" href="/login">Log In <span class="sr-only">(current)</span></a>-->
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

<h1>Edit trip</h1>
<div class="text-left d-flex justify-content-left">

    <form class="w-25" role="form" name="updateTrip" action="" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="form-group">
            <label for="reason">Reason</label>
            <select class="form-control" name="reason" id="reason" required>
                <option <#if trip.reason == 'Meeting'>selected</#if>>Meeting</option>
                <option <#if trip.reason == 'Training'>selected</#if>>Training</option>
                <option <#if trip.reason == 'Project'>selected</#if>>Project</option>
                <option <#if trip.reason == 'Workshop'>selected</#if>>Workshop</option>
                <option <#if trip.reason == 'Event'>selected</#if>>Event</option>
                <option <#if trip.reason == 'Other'>selected</#if>>Other</option>
            </select>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <input class="form-control" type="text" name="description" id="description" value="${trip.description}"/>
        </div>
        <div class="form-group">
            <label for="fromTrip">From Trip</label>
            <input class="form-control" type="text" name="fromTrip" id="fromTrip" required value="${trip.fromTrip}"/>
        </div>
        <div class="form-group">
            <label for="toTrip">To Trip</label>
            <input class="form-control" type="text" name="toTrip" id="toTrip" required value="${trip.toTrip}"/>
        </div>

        <div class="form-outline datepicker" data-mb-inline="true">
            <label for="departureDate">Departure Date</label>
            <input class="form-control" type="date" name="departureDate" id="departureDate" required value="${trip.departureDate}"/>
        </div>

        <div class="form-outline datepicker">
            <label for="arrivalDate">Arrival Date</label>
            <input class="form-control" type="date" name="arrivalDate" id="arrivalDate" required value="${trip.arrivalDate}"/>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
    </form>
</div>

<@spring.bind "updateTrip" />
<#if spring.status.error>
    <ul>
        <#list spring.status.errorMessages as error>
            <li>${error}</li>
        </#list>
    </ul>
</#if>

</body>
</html>