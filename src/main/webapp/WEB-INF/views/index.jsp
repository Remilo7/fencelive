<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Strona Główna</title>

        <meta charset="UTF-8">

        <link href="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="https://fonts.googleapis.com/css?family=Open+Sans:400,700&amp;subset=latin-ext" />" rel="stylesheet">
        <link href="<c:url value="/resources/static/css/style.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/static/css/buttons.css" />" rel="stylesheet">
        <link href="<c:url value="https://fonts.googleapis.com/css?family=Roboto+Condensed:300,400" />" rel="stylesheet">

    </head>
    <body>

    <div class="container-fluid">
        <div class="row header-row">
            <div class="container-fluid text-center">
                <h1>FenceLive</h1>
                <h3>System wspomagania organizacji zawodów szermierczych</h3>
            </div>
        </div>


        <div class="row no-padding">
            <div class="col-md-2 text-center">
                <div class="button-box">
                    <a href="newTournament" class="btn btn-green btn-border" role="button">Nowy turniej</a>
                    <br>
                    <a href="#" class="btn btn-blue btn-border" role="button">Zarządzaj turniejami</a>
                    <br>
                    <a href="fencers" class="btn btn-navy btn-border" role="button">Baza szermierzy</a>
                    <br>
                    <a href="lists" class="btn btn-navy btn-border" role="button">Listy klasyfikacyjne</a>
                </div>
            </div>

            <div class="col-md-10">
                <table class="table table-hover table-responsive fixed-table">
                    <thead>
                        <tr>
                            <th>Nazwa turnieju</th>
                            <th>Kategoria</th>
                            <th>Płeć</th>
                            <th>Data</th>
                            <th/>
                        </tr>
                    </thead>

                    <c:forEach items="${tournamentList}" var="tournament">
                        <tbody>
                            <tr>
                                <td>${tournament.name}</td>
                                <td>${categories.get(tournament.category-1).name}</td>
                                <td>${tournament.sex}</td>
                                <td>${tournament.date}</td>
                                <td><a href="tournament?tId=${tournament.id}"><button class="btn btn-green btn-border">Przejdź</button></a></td>
                            </tr>
                        </tbody>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>

    </body>
</html>