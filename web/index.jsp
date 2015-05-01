<%-- 
    Document   : Login page
    Created on : Apr 27, 2015, 4:58:37 PM
    Author     : apple
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <title>Login Page</title>
    </head>

    <body>
        <div class="container">
            <div class="row">
                <div class="jumbotron">
                    <div class="panel panel-login">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-6">
                                    <a href="#" class="active" id="login-form-link">Login</a>
                                </div>
                                <div class="col-xs-6">
                                    <a href="#" id="register-form-link">Register</a>
                                </div>
                            </div>
                            <hr>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <form id="login-form" action="home" method="post" role="form" style="display: block;">
                                        <c:if test="${complete!='yes' && message!=null}">
                                            <div class="alert alert-danger" role="alert">${message}</div>
                                        </c:if>
                                        <c:if test="${complete=='yes'}">
                                            <div class="alert alert-success" role="alert">${message}</div>
                                        </c:if>     
                                        <div class="form-group">
                                            <input type="text" name="un" id="username" tabindex="1" class="form-control" placeholder="Username" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="pw" id="password" tabindex="2" class="form-control" placeholder="Password">
                                        </div>
                                        <div class="form-group text-center">
                                            <input type="checkbox" tabindex="3" class="" name="remember" id="remember">
                                            <label for="remember"> Remember Me</label>
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Log In">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <div class="text-center">
                                                        <a href="#" tabindex="5" class="forgot-password">Forgot Password?</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>

                                    <form id="register-form" action="register" method="post" role="form" style="display: none;" data-toggle="validator">
                                        <div class="form-group">
                                            <input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="" required>
                                        </div>
                                        <div class="form-group">
                                            <input type="text" name="name" maxlength="20" id="name" tabindex="1" class="form-control" placeholder="Your Name" value="" required>
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="password" id="regpassword" tabindex="2" class="form-control" data-minlength="6" placeholder="Password" required>
                                            <span class="help-block">  Minimum of 6 characters</span>
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="confirm-password" data-match="#regpassword" id="confirm-password"  data-match-error="Whoops, these don't match" tabindex="2" class="form-control" placeholder="Confirm Password" required>
                                            <div class="help-block with-errors"></div>
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Register Now">
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="js/login.js"></script>
        <script src="js/validator.js"></script>
    </body>

</html>
