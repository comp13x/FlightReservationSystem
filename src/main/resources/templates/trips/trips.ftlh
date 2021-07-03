<#-- @ftlvariable name="trips" type="java.util.List<com.gerardrama.entity.Trip>" -->
<#-- @ftlvariable name="flights" type="java.util.List<com.gerardrama.entity.Flight>" -->
<#-- @ftlvariable name="users" type="java.util.List<com.gerardrama.entity.User>" -->

<#-- @ftlvariable name="approval" type="com.gerardrama.entity.enums.Approval" -->
<#-- @ftlvariable name="userService" type="com.gerardrama.service.user.impl.UserServiceImpl" -->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>List of Users</title>
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

<div class="container">
    <div class="panel panel-primary">
        <h1>List of Trips</h1>
    </div>
    <div class="panel-body">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Departure Date</th>
                <th>Arrival Date</th>
                <th>From</th>
                <th>To</th>
                <th>Reason</th>
                <th>Description</th>
                <th>Approval</th>
                <th>Flights</th>
                <th colspan="2">Actions</th>
            </tr>
            </thead>

            <tbody>
                <#list trips as trip>
                    <tr>
                        <#if trip.approval == "CREATED" && user.id == currentUser.id>
                            <td>${trip.departureDate}</td>
                            <td>${trip.arrivalDate}</td>
                            <td>${trip.fromTrip}</td>
                            <td>${trip.toTrip}</td>
                            <td>${trip.reason}</td>
                            <td>${trip.description}</td>
                            <td>${trip.getApproval()}</td>
                            <td>
                                <a>
                                    <button class="editBtn"><a href="trips/${trip.tripId}/apply">Submit for approval</a></button>
                                </a>
                            </td>
                            <td>
                                <a href="trips/${trip.tripId}/delete" class="btn btn-danger btn-lg enabled" role="button" aria-disabled="true">Delete</a>
                            </td>
                            <td>
                                <a href="trips/${trip.tripId}/edit" class="btn btn-info btn-lg enabled" role="button" aria-disabled="true">Edit</a>
                            </td>

                        </#if>

                        <#if trip.approval != "CREATED">
                            <td>${trip.departureDate}</td>
                            <td>${trip.arrivalDate}</td>
                            <td>${trip.fromTrip}</td>
                            <td>${trip.toTrip}</td>
                            <td>${trip.reason}</td>
                            <td>${trip.description}</td>
                            <td>${trip.getApproval()}</td>
                            <td>
                                <#if currentUser.role == "ADMIN" && trip.approval == "WAITING FOR APPROVAL">
                                    <td>
                                        <a>
                                            <button class="editBtn"><a href="trips/${trip.tripId}/approve">APPROVE</a></button>
                                        </a>
                                    </td>
                                    <td>
                                        <a>
                                            <button class="editBtn"><a href="trips/${trip.tripId}/deny">DENY</a></button>
                                        </a>
                                    </td>
                                </#if>

                                <#if trip.approval == "APPROVED">
                                    <a>
                                        <button class="editBtn"><a href="trips/${trip.tripId}/flights">See Flights</a></button>
                                    </a>
                                </#if>

                                <#if (trip.approval != "WAITING FOR APPROVAL") && (trip.approval != "APPROVED")>
                                    <a>
                                        <button class="editBtn"><a>See Flights</a></button>
                                    </a>
                                </#if>
                            </td>
                            <td>
                                <a href="trips/${trip.tripId}/delete" class="btn btn-danger btn-lg enabled" role="button" aria-disabled="true">Delete</a>
                            </td>
                            <td>
                                <a href="trips/${trip.tripId}/edit" class="btn btn-info btn-lg enabled" role="button" aria-disabled="true">Edit</a>
                            </td>
                        </#if>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
    <a href="trips/create" class="btn btn-primary btn-lg enabled" role="button" aria-disabled="true">Add Trip</a>
</div>
</body>
</html>



