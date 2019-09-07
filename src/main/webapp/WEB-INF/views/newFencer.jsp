<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Nowy zawodnik</title>

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
            <h1>Dodaj zawodnika do bazy</h1>
        </div>
    </div>


    <div class="row no-padding">
        <div class="col-md-2"></div>

        <div class="col-md-8 tournament-form">
            <form:form action="addFencer" method="post" modelAttribute="fencer" cssClass="form-horizontal">

                <div class="form-group">
                    <label for="name" class="control-label col-sm-4">Imię:</label>
                    <div class="col-sm-8">
                        <form:input type="text" path="name" class="form-control" placeholder="Wprowadź imię zawodnika" required="required" maxlength="50" />
                    </div>
                </div>

                <div class="form-group">
                    <label for="surname" class="control-label col-sm-4">Nazwisko:</label>
                    <div class="col-sm-8">
                        <form:input type="text" path="surname" class="form-control" placeholder="Wprowadź nazwisko zawodnika" required="required" maxlength="50" />
                    </div>
                </div>

                <div class="form-group">
                    <label for="year" class="control-label col-sm-4">Rok urodzenia:</label>
                    <div class="col-sm-8">
                        <form:input type="number" class="form-control" path="year" placeholder="Rok urodzenia zawodnika" required="required" min="1920" max="2100" />
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
                    <label for="club" class="control-label col-sm-4">Klub:</label>
                    <div class="col-sm-8">
                        <form:input type="text" path="club" class="form-control" placeholder="Wprowadź nazwę klubu zawodnika" required="required" maxlength="100" />
                    </div>
                </div>

                <div class="form-group">
                    <label for="country" class="control-label col-sm-4">Kraj:</label>
                    <div class="col-sm-8">
                        <form:input type="text" path="country" class="form-control" placeholder="Wprowadź trzyliterowy skrót kraju" required="required" maxlength="3" />
                    </div>
                </div>

                <button class="btn btn-green btn-border pull-right no-margin-right" name="action" value="add">Dodaj</button>
            </form:form>

            <a href="fencers"><button class="btn btn-navy btn-border pull-left no-margin-left" name="action" value="back">Wstecz</button></a>

        </div>
    </div>
</div>

</body>
</html>