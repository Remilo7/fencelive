<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Zarządzanie turniejami</title>

    <meta charset="UTF-8">

    <link href="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Open+Sans:400,700&amp;subset=latin-ext" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/buttons.css" />" rel="stylesheet">
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Roboto+Condensed:300,400" />" rel="stylesheet">

</head>
<body>

<div class="container-fluid">
    <div class="row header-row-medium">
        <div class="container-fluid text-center">
            <h1>Zarządzanie turniejami</h1>
        </div>
    </div>


    <div class="row no-padding">
        <div class="col-md-1"></div>

        <div class="col-md-10 fencers">

            <form:form action="manageTournaments" method="post" modelAttribute="tournament">
                <div class="row">
                    <div class="col-md-12">
                        <div class="fencers-table">
                            <table class="table table-hover table-responsive">
                                <thead>
                                <tr>
                                    <th />
                                    <th>Nazwa turnieju</th>
                                    <th>Kategoria</th>
                                    <th>Płeć</th>
                                    <th>Data</th>
                                </tr>
                                </thead>

                                <tbody>
                                    <c:forEach items="${tournaments}" var="tournament">
                                        <tr>
                                            <td><form:radiobutton path="id" value="${tournament.id}"/></td>
                                            <td>${tournament.name}</td>
                                            <td>${categories.get(tournament.category-1).name}</td>
                                            <td>${tournament.sex}</td>
                                            <td>${tournament.date}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="row bottom-spacer">

                    <button class="btn btn-navy btn-border pull-left" name="action" value="back">Wstecz</button>

                    <button class="btn btn-red btn-border pull-right" type="submit" name="action" value="Delete">Usuń</button>

                </div>
            </form:form>

        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js" />"> </script>
<script type="text/javascript" src="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js" />"> </script>

</body>
</html>