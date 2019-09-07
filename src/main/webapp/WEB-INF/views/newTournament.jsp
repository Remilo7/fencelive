<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Nowy turniej</title>

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
            <h1>Nowy turniej</h1>
        </div>
    </div>


    <div class="row no-padding">
        <div class="col-md-2"></div>

        <div class="col-md-8 tournament-form">
            <form:form action="addTournament" method="post" modelAttribute="tournament" cssClass="form-horizontal">

                <div class="form-group">
                    <label for="name" class="control-label col-sm-4">Nazwa:</label>
                    <div class="col-sm-8">
                        <form:input type="text" path="name" class="form-control" placeholder="Wprowadź nazwę turnieju" required="required" maxlength="100" />
                    </div>
                </div>

                <div class="form-group">
                    <label for="date" class="control-label col-sm-4">Data:</label>
                    <div class="col-sm-8">
                        <form:input type="date" class="form-control" path="date" placeholder="Data turnieju: DD.MM.RRRR" required="required" />
                    </div>
                </div>

                <div class="form-group">
                    <label for="category" class="control-label col-sm-4">Kategoria:</label>
                    <div class="col-sm-8">
                        <form:select path="category">
                            <form:option value="1">Senior</form:option>
                            <form:option value="2">Młodzieżowiec</form:option>
                            <form:option value="3">Junior</form:option>
                            <form:option value="4">Kadet</form:option>
                            <form:option value="5">Młodzik</form:option>
                            <form:option value="6">Dzieci</form:option>
                            <form:option value="7">Zuch</form:option>
                            <form:option value="8">Skrzat</form:option>
                        </form:select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="sex" class="control-label col-sm-4">Płeć:</label>
                    <div class="col-sm-8">
                        <form:select path="sex">
                            <form:option value="M">M</form:option>
                            <form:option value="K">K</form:option>
                        </form:select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="out" class="control-label col-sm-4">Odpada po fazie grupowej (procent):</label>
                    <div class="col-sm-8">
                        <form:input type="number" path="outamount" class="form-control" placeholder="Procent odpadających" required="required" min="0" max="50" />
                    </div>
                </div>

                <div class="form-group">
                    <label for="method" class="control-label col-sm-4">Metoda eliminacji:</label>
                    <div class="col-sm-8">
                        <form:select path="method">
                            <form:option value="1">Baraże</form:option>
                            <form:option value="2">Wolny los</form:option>
                        </form:select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="list" class="control-label col-sm-4">Rozstawienie wg listy:</label>
                    <div class="col-sm-8">
                        <form:select path="list">
                            <form:option value="1">Przykładowa lista 1</form:option>
                            <form:option value="2">Przykładowa lista 2</form:option>
                        </form:select>
                    </div>
                </div>

                <button class="btn btn-green btn-border pull-right no-margin-right" name="action" value="create">Utwórz</button>
            </form:form>

            <a href="index"><button class="btn btn-navy btn-border pull-left no-margin-left" name="action" value="back">Wstecz</button></a>

        </div>
    </div>
</div>

</body>
</html>