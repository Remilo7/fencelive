<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Nowy wpis</title>

    <link href="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Open+Sans:400,700&amp;subset=latin-ext" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/subpage.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/buttons.css" />" rel="stylesheet">
</head>
<body data-spy="scroll" data-target=".navbar" data-offset="50" onresize="stickyUpdate()">

<a href="index">
    <div class="container-fluid" id="bg_div">
        <div class="row">
            <div class="col-md-12 no-padding"></div>
        </div>
    </div>
</a>

<div id="nav">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav navbar-center">
                        <li><a href="index#section1">Aktualności</a></li>
                        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">O klubie<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="index#section2">Historia</a></li>
                                <li><a href="coaches">Kadra trenerska</a></li>
                                <li><a href="index#">Zawodnicy</a></li>
                            </ul>
                        </li>
                        <li><a href="index#section3">Treningi</a></li>
                        <li><a href="index#section4">Galeria</a></li>
                        <li><a href="index#section5">Kontakt</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout">Wyloguj się</a></li>
                        <li class="active"><a href="adminPanel">Panel</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</div>

<!-- Treść strony -->


<div class="user_panel container-fluid no-padding">
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6 text-center">
            <h1>Nowy wpis</h1>
        </div>
    </div>
    <form:form action="article.do" method="post" modelAttribute="article" commandName="article" enctype="multipart/form-data">
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6 newArticle">

                <form:input type="text" class="form-control titleInput" path="title" placeholder="Tytuł" />
                <form:textarea class="form-control contentInput" path="content" placeholder="Treść..." />
                <input name="file" id="fileToUpload" type="file" />

            </div>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6 no-padding">
                <div class="col-md-6 newsButtons">
                    <button class="btn btn-navy btn-border pull-left" name="action" value="Back">Wstecz</button></a>
                </div>
                <div class="col-md-6 newsButtons">
                    <button class="btn btn-green btn-border pull-right" name="action" value="Add">Dodaj</button>
                </div>
            </div>
        </div>
    </form:form>
</div>

<script type="text/javascript" src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" />"> </script>
<script type="text/javascript" src="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js" />"> </script>

<!-- Navbar Affix -->
<script type="text/javascript">
    window.onscroll = function() {myFunction()};

    var header = document.getElementById("nav");
    var sticky = header.offsetTop;
    var sticked = false;
    function myFunction() {

        if (window.pageYOffset > header.offsetTop) {
            header.classList.add("sticky");
            sticked = true;
        }
        if (window.pageYOffset < sticky && sticked){
            header.classList.remove("sticky");
            sticked = false;
        }
    }
    function stickyUpdate() {
        console.log(sticky);
        header.classList.remove("sticky");
        sticky = header.offsetTop;
        myFunction()
    }
</script>

<!-- Smooth Scroll -->
<script type="text/javascript">
    $("#nav ul li a[href^='#']").on('click', function(e) {
        e.preventDefault();

        var hash = this.hash;

        $('html, body').animate({
            scrollTop: $(hash).offset().top
        }, 300, function(){
            window.location.hash = hash;
        });
    });
</script>

</body>
</html>
