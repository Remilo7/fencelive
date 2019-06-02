<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Panel zawodnika</title>

    <link href="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Open+Sans:400,700&amp;subset=latin-ext" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/subpage.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/calendar.css" />" rel="stylesheet">
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
                                <li><a href="members">Zawodnicy</a></li>
                            </ul>
                        </li>
                        <li><a href="index#section3">Treningi</a></li>
                        <li><a href="index#section4">Galeria</a></li>
                        <li><a href="index#section5">Kontakt</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout">Wyloguj się</a></li>
                        <li class="active"><a href="userPanel">Panel</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</div>

<!-- Treść strony -->

<div class="user_panel container-fluid">

    <div class="row">
        <div class="col-md-7">
            <h1>${(fencer.name).concat(' ').concat(fencer.surname)}</h1>
        </div>
        <div class="col-md-5"></div>
    </div>

    <div class="row">

        <div class="col-md-7 panel_window">
            <ul class="nav nav-tabs">
                <li><a data-toggle="tab" href="#menu1">Aktualności</a></li>
                <li><a data-toggle="tab" href="#menu2">Przypomnienia</a></li>
                <li class="active"><a data-toggle="tab" href="#menu3">Dane</a></li>
            </ul>

            <div class="tab-content">

                <div id="menu1" class="tab-pane fade">

                    <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Nadawca</th>
                            <th>Treść</th>
                        </tr>
                        </thead>

                        <c:forEach items="${listAktualnosci}" var="message">
                        <tbody>
                        <tr>
                            <td>${message.sender}</td>
                            <td>${message.content}</td>
                        </tr>
                        </tbody>
                        </c:forEach>
                    </table>

                </div>

                <div id="menu2" class="tab-pane fade">

                    <table class="table table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>Nadawca</th>
                            <th>Treść</th>
                        </tr>
                        </thead>

                        <c:forEach items="${listPrzypomnienia}" var="message">
                            <tbody>
                            <tr>
                                <td>${message.sender}</td>
                                <td>${message.content}</td>
                            </tr>
                            </tbody>
                        </c:forEach>
                    </table>

                </div>

                <div id="menu3" class="tab-pane fade in active">
                    <h3>Menu 3</h3>
                    <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
                </div>

            </div>
        </div>

        <div class="col-md-5">
            <div id="calendar"></div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" />"> </script>
<script type="text/javascript" src="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js" />"> </script>
<script type="text/javascript" src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.5.1/moment.min.js" />"> </script>

<!-- Kalendarz -->
<script>
    var data = [];

    <c:forEach items="${eventList}" var="event">
    data.push({eventName: '${event.eventName}', calendar: '${event.calendar}', color: '${event.color}', date: '${event.date}'});
    </c:forEach>
</script>

<script type="text/javascript" src="<c:url value="/resources/static/js/calendar.js" />"> </script>

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
