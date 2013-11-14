<%-- 
    Document   : doctor_profile
    Created on : 12-Nov-2013, 1:50:15 AM
    Author     : Yash Malik
--%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">

    <title>Doctor profile</title>

    <!-- Bootstrap core CSS -->
    <link href="style/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="style/jumbotron-narrow.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  </head>
  <style type="text/css">
      
      .profilelabel {
	color:#999;
	text-align:right;
	vertical-align:top;
	white-space:nowrap;
    }

  </style>
  <body>

    <div class="container">
      <div class="header">
        <ul class="nav nav-pills pull-right">
          <li><a href="LoginServlet">Home</a></li>
          <li class="active"><a href="#">Profile</a></li>
        </ul>
        <h3 class="text-muted">Medicare</h3>
      </div>
      <div style="height: 800px;">
      <jsp:useBean id="doctor" class="social_network.Doctor" scope="request"/>
        <p> <span class="profilelabel">Name:</span> <%= doctor.getFirstName() + " " + doctor.getMiddleName() + " " + doctor.getLastName() %><br/>
            <span class="profilelabel">Gender:</span> <%= doctor.getGender() %><br/>
            <span class="profilelabel">Date of Birth:</span> <%= doctor.getDob()%></br>
            <span class="profilelabel">License Year:</span><%= doctor.getLicenceYear()%></br>
            <span class="profilelabel">Specialization:</span>
            <% 
                    for(String s : doctor.getSpecialisations()) {
                        %>
                        <%= s + ", "%>
                        <%
                    }
            %></br>
            <span class="profilelabel">Address:</span> </br>
            <% 
                    for(social_network.Address a : doctor.getAddresses()) {
                        %>
                        <%= a.prettyPrint()%></br>
                        <%
                    }
            %></br>
        </p>
      </div>

    </div> <!-- /container -->
    <div class="footer">
        <p>&copy; Medicare 2013</p>
    </div>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  </body>
</html>
