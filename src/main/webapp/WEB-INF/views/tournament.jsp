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

                <%--Lista zawodników i potwierdzanie startujących--%>

                <div id="tab1" class="tab-pane fade in active">

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
                                                        <c:if test = "${generated == false}">
                                                            <form:checkbox path="idList" value="${fencer.id}"/>
                                                        </c:if>

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

                                <c:if test = "${generated == false}">
                                    <button class="btn btn-red btn-border pull-left no-margin-left" name="action" value="cancel">Anuluj</button>
                                    <button class="btn btn-green btn-border pull-right no-margin-left" name="action" value="confirm">Potwierdź</button>
                                </c:if>

                            </div>
                        </div>
                    </form:form>
                </div>

                <%--Faza grupowa--%>

                <div id="tab2" class="tab-pane fade in">

                    <div class="row">
                        <div class="col-md-12">

                            <c:choose>
                                <c:when test="${generated == false}">
                                    <div class="tournament-groups-table">
                                        <form:form action="generateGroups" method="post">
                                            <div class="center-wrapper">
                                                <button class="btn btn-navy btn-border">Generuj grupy</button>
                                            </div>
                                        </form:form>
                                    </div>
                                </c:when>

                                <c:otherwise>
                                    <form:form action="groupFightsResults" method="post" modelAttribute="groupFightsForm">
                                        <div class="tournament-groups-table">

                                            <c:forEach items="${groups}" var="list" varStatus="loop">

                                                <h3>Grupa ${loop.count}</h3>
                                                <table class="table-bordered">
                                                    <tbody>
                                                    <th class="name-cell">Nazwisko, imię</th>
                                                    <th class="club-cell">Klub</th>
                                                    <th class="country-cell">Kraj</th>
                                                    <th class="score-cell"/>

                                                    <c:forEach var="i" begin = "0" end ="${list.size()-1}">
                                                        <th class="score-cell">${i+1}</th>
                                                    </c:forEach>

                                                    <c:forEach items="${list}" var="fencer" varStatus="i">
                                                        <tr>
                                                            <td class="name-cell">${fencer.name}</td>
                                                            <td class="club-cell">${fencer.club}</td>
                                                            <td class="country-cell">${fencer.country}</td>
                                                            <th class="score-cell">${i.count}</th>

                                                                <%--Loop with cells to save scores--%>

                                                            <c:forEach var="j" begin = "0" end ="${list.size()-1}">
                                                                <c:choose>
                                                                    <c:when test="${j>i.index}">
                                                                        <input class="hidden" name="groupFights[${loop.index}][${i.index}][${j}].id" value="${groupFightsForm.groupFights.get(loop.index)[i.index][j].id}" />
                                                                        <td class="score-cell"><input class="score-cell" type="text" name="groupFights[${loop.index}][${i.index}][${j}].score1" value="${groupFightsForm.groupFights.get(loop.index)[i.index][j].score1}" maxlength="2" /></td>
                                                                    </c:when>
                                                                    <c:when test="${j==i.index}">
                                                                        <td bgcolor="black" class="score-cell"/>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <input class="hidden" name="groupFights[${loop.index}][${i.index}][${j}].id" value="${groupFightsForm.groupFights.get(loop.index)[i.index][j].id}" />
                                                                        <td class="score-cell"><input class="score-cell" type="text" name="groupFights[${loop.index}][${i.index}][${j}].score2" value="${groupFightsForm.groupFights.get(loop.index)[i.index][j].score2}" maxlength="2" /></td>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:forEach>
                                        </div>

                                        <button class="btn btn-green btn-border pull-right no-margin-left">Zapisz</button>
                                    </form:form>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
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