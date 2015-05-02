<%-- 
    Document   : home
    Created on : Apr 28, 2015, 8:20:07 PM
    Author     : apple
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>Tourini</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/sweetalert.css">
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
        <!--[if lt IE 9]>
                <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <link href="css/home.css" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <div class="box">
                <div class="row row-offcanvas row-offcanvas-left">


                    <!-- sidebar -->
                    <div class="column col-sm-2 col-xs-1 sidebar-offcanvas" id="sidebar">

                        <ul class="nav">
                            <li><a href="#" data-toggle="offcanvas" class="visible-xs text-center"><i class="glyphicon glyphicon-chevron-right"></i></a></li>
                        </ul>

                        <ul class="nav hidden-xs" id="lg-menu">
                            <li class="active"><a href="#featured"><i class="glyphicon glyphicon-list-alt"></i> Featured</a></li>
                            <li><a href="#stories"><i class="glyphicon glyphicon-list"></i> Stories</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-paperclip"></i> Saved</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-refresh"></i> Refresh</a></li>
                        </ul>
                        <ul class="list-unstyled hidden-xs" id="sidebar-footer">
                            <li>
                                <a href="#"><h3>Tourini</h3> <i class="glyphicon glyphicon-heart-empty"></i>Hello</a>
                            </li>
                        </ul>

                        <!-- tiny only nav-->
                        <ul class="nav visible-xs" id="xs-menu">
                            <li><a href="#featured" class="text-center"><i class="glyphicon glyphicon-list-alt"></i></a></li>
                            <li><a href="#stories" class="text-center"><i class="glyphicon glyphicon-list"></i></a></li>
                            <li><a href="#" class="text-center"><i class="glyphicon glyphicon-paperclip"></i></a></li>
                            <li><a href="#" class="text-center"><i class="glyphicon glyphicon-refresh"></i></a></li>
                        </ul>

                    </div>
                    <!-- /sidebar -->

                    <!-- main right col -->
                    <div class="column col-sm-10 col-xs-11" id="main">
                        <!-- top nav -->
                        <c:import url="nav.jsp" />
                        <!-- /top nav -->
                        <div class="padding">
                            <div class="full col-sm-9">
                                <c:import url="main.jsp" />
                            </div><!-- /col-9 -->
                        </div><!-- /padding -->
                    </div>
                    <!-- /main -->
                </div>
            </div>
        </div>


        <!--post modal-->
        <div id="postModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        Update Status
                    </div>
                    <form class="form center-block" id="status" name="status">
                        <div class="modal-body">

                            <div class="form-group">
                                <textarea class="form-control input-lg" name="text" autofocus="" placeholder="What do you want to share?"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div>
                                <button id="status-submit" class="btn btn-primary btn-sm" data-dismiss="modal" aria-hidden="true">Post</button>
                                <ul class="pull-left list-inline">
                                    <li>
                                        <div class="fileUpload btn btn-primary btn-sm">
                                            <span>Upload</span>
                                            <input type="file" class="upload" />
                                        </div>
                                    </li>
                                    <!--                                     <li><a href=""><i class="glyphicon glyphicon-upload"></i></a></li>
                                                                        <li><a href=""><i class="glyphicon glyphicon-camera"></i></a></li>
                                                                        <li><a href=""><i class="glyphicon glyphicon-map-marker"></i></a></li> -->
                                </ul>
                            </div>	
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <i id="user-id" value="${currentSessionUser.getUser_id()}"></i>
        <!-- script references -->
        <script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="js/home.js"></script>
        <script src="js/sweetalert.min.js"></script>
        <script type="text/javascript" src="js/typeahead.bundle.min.js"></script>
    </body>
</html>
