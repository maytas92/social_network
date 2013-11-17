<%-- 
    Document   : index
    Created on : 9-Nov-2013, 10:16:06 PM
    Author     : Satyam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">

    <title>Sign in - SPRaY</title>

    <!-- Bootstrap core CSS -->
    <link href="style/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="style/login_page.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="container">
        <% if(request.getSession().getAttribute("login") != null) {
            if(!request.getSession().getAttribute("login").toString().isEmpty()) {
                request.getSession().setAttribute("authenticate", "no");
                response.sendRedirect("/SocialNetwork/ProfileServlet");
            }
        }
        %>
      <form  method="post" action="LoginServlet?authenticate=yes" class="form-signin">
        <h2 class="form-signin-heading">Medicare</h2>
        <input name="login"  type="text" class="form-control" placeholder="Login" required autofocus>
        <input name="password" type="password" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        <% if(request.getAttribute("incorrect_credentials")!=null && request.getAttribute("incorrect_credentials").equals("yes")) { %>
            <span style="color:red"><%= "Invalid Username or Password." %></span><br>
        <% }%>
      </form>
        
    </div> <!-- /container -->

    
    
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  </body>
</html>

