<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Szczegóły turnieju</title>

    <meta charset="UTF-8">

    <link href="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Open+Sans:400,700&amp;subset=latin-ext" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/buttons.css" />" rel="stylesheet">
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Roboto+Condensed:300,400" />" rel="stylesheet">

</head>
<body>

<div class="container-fluid tournament">
    <div class="row header-row-small">
        <div class="container-fluid text-center">
            <h2>${tournament}</h2>
        </div>
    </div>

    <div class="row small-padding">

        <div class="col-md-12 panel_window">

            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#tab1">Lista zawodników</a></li>
                <li><a data-toggle="tab" href="#tab2">Faza grupowa</a></li>
                <li><a data-toggle="tab" href="#tab3">Klasyfikacja po grupach</a></li>
                <li><a data-toggle="tab" href="#tab4">Eliminacje</a></li>
                <li><a data-toggle="tab" href="#tab5">Klasyfikacja końcowa</a></li>
            </ul>

            <div class="tab-content">

                <div id="menu1" class="tab-pane fade in active">

                    <input class="form-control tournament-fencers-search" id="myInput" type="text" placeholder="Search..">

                    <form:form action="tournament.do" method="post" modelAttribute="fencer">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="tournament-fencers-table">
                                    <table class="table table-hover table-responsive">
                                        <thead>
                                        <tr>
                                            <th>Status</th>
                                            <th>Nazwisko i imię</th>
                                            <th>Klub</th>
                                            <th>Kraj</th>
                                            <th>Data urodzenia</th>
                                        </tr>
                                        </thead>

                                        <tbody id="myTable">
                                        <c:forEach items="${fencerList}" var="fencer">
                                            <tr>
                                                <td>
                                                    <p style="color: forestgreen; font-weight: bold; font-size: smaller">
                                                        <form:checkbox path="idList" value="${fencer.id}"/>
                                                        <c:if test = "${fencer.competing}">
                                                            Potwierdzony
                                                        </c:if>
                                                    </p>
                                                </td>
                                                <td>${fencer.name}</td>
                                                <td>${fencer.club}</td>
                                                <td>${fencer.country}</td>
                                                <td>${fencer.year}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                                <button class="btn btn-red btn-border pull-left no-margin-left" name="action" value="cancel">Anuluj</button>
                                <button class="btn btn-green btn-border pull-right no-margin-left" name="action" value="confirm">Potwierdź</button>

                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>

    <div class="row small-padding">
        <a href="index"><button class="btn btn-navy btn-border pull-left no-margin-left" name="action" value="back">Wstecz</button></a>
    </div>
</div>

<script type="text/javascript" src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js" />"> </script>
<script type="text/javascript" src="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js" />"> </script>

<script>
    $(document).ready(function(){
        $("#myInput").on("keyup", function() {
            var value = $(this).val().toLowerCase();
            $("#myTable tr").filter(function() {
                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
            });
        });
    });
</script>

</body>
</html>