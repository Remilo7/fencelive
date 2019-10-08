<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Baza szermierzy</title>

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
            <h1>Baza szermierzy</h1>
        </div>
    </div>


    <div class="row no-padding">
        <div class="col-md-1"></div>

        <div class="col-md-10 fencers">

            <input class="form-control tournament-fencers-search" id="myInput" type="text" placeholder="Search..">

            <form:form action="fencers.do" method="post" modelAttribute="fencer">
                <div class="row">
                    <div class="col-md-12">
                        <div class="tournament-fencers-table">
                        <table class="table table-hover table-responsive">
                            <thead>
                            <tr>
                                <th />
                                <th>Nazwisko i imię</th>
                                <th>Klub</th>
                                <th>Kraj</th>
                                <th>Data urodzenia</th>
                            </tr>
                            </thead>

                            <tbody id="myTable">
                                <c:forEach items="${fencerList}" var="fencer">
                                    <tr>
                                        <td><form:checkbox path="idList" value="${fencer.id}"/></td>
                                        <td>${fencer.name}</td>
                                        <td>${fencer.club}</td>
                                        <td>${fencer.country}</td>
                                        <td>${fencer.year}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        </div>
                    </div>
                </div>

                <div class="row bottom-spacer">
                    <div class="col-md-3">
                        <button class="btn btn-navy btn-border pull-left" name="action" value="back">Wstecz</button>
                    </div>

                    <div class="col-md-6">
                        <div class="button-box-horizontal">
                            <button class="btn btn-red btn-border" type="submit" name="action" value="Delete">Usuń</button>
                            <button type="button" class="btn btn-blue btn-border" data-toggle="modal" data-target="#myModal">Importuj z pliku</button>
                            <button class="btn btn-green btn-border" name="action" value="add">Dodaj</button>
                        </div>
                    </div>
                </div>
            </form:form>

        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
        <form:form action="fencers.do" method="post" modelAttribute="fencer" enctype="multipart/form-data">

            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Wybierz plik .xlsx</h4>
                </div>

                <div class="modal-body">
                    <input name="file" id="fileToUpload" type="file" />
                </div>

                <div class="modal-footer">
                    <div class="button-box-center">
                        <button class="btn btn-green btn-border" name="action" value="import">Importuj</button>
                    </div>
                </div>
            </div>
        </form:form>
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