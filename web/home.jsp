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
                            <li class="active"><a href="#main-view" id='main-view-btn'><i class="glyphicon glyphicon-list-alt"></i> Home</a></li>
                            <li><a href="#friend-view" id='friend-view-btn'><i class="glyphicon glyphicon-list"></i> Friends</a></li>
                            <li><a href=""><i class="glyphicon glyphicon-refresh"></i> Refresh</a></li>
                        </ul>
                        <ul class="list-unstyled hidden-xs" id="sidebar-footer">
                            <li>
                                <a href="#"><h3>Tourini</h3> <i class="glyphicon glyphicon-heart-empty"></i>Hello</a>
                            </li>
                        </ul>

                        <!-- tiny only nav-->
                        <ul class="nav visible-xs" id="xs-menu">
                            <li><a href="" class="text-center"><i class="glyphicon glyphicon-list-alt"></i></a></li>
                            <li><a href="#friends" class="text-center"><i class="glyphicon glyphicon-list"></i></a></li>
                            <li><a href="" class="text-center"><i class="glyphicon glyphicon-refresh"></i></a></li>
                        </ul>

                    </div>
                    <!-- /sidebar -->

                    <!-- main right col -->
                    <div class="column col-sm-10 col-xs-11" id="main">
                        <!-- top nav -->
                        <c:import url="nav.jsp" />
                        <!-- /top nav -->
                        <div class="padding">
                            <div class="full col-sm-9" id="main-view">
                                <c:import url="main.jsp" />
                            </div><!-- /col-9 -->
                            <div class="full col-sm-9" id="friend-view" style="display:none">
                                <c:import url="friend.jsp" />
                            </div>
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
                        <div class="modal-footer" id="modalFooterPost">
                            <div>    
                                <button id="status-submit" class="btn btn-primary btn-sm" data-dismiss="modal" aria-hidden="true">Post</button>
                                <ul class="pull-left list-inline">
                                    <li>
                                        <div class="fileUpload btn btn-primary btn-sm">
                                            <span>Upload Photo</span>
                                            <input type="file" class="upload" id="uploadBtn" name="file"/>
                                            <img id="blah" src="#" alt="your image" style="display:none"/>
                                        </div>
                                    </li>
                                    <!--                                     <li><a href=""><i class="glyphicon glyphicon-upload"></i></a></li>
                                                                        <li><a href=""><i class="glyphicon glyphicon-camera"></i></a></li>
                                                                        <li><a href=""><i class="glyphicon glyphicon-map-marker"></i></a></li> -->

                                    <li>
                                        <select id="selectPrivacy" class="form-control" name="privacy">
                                            <option>Public</option>
                                            <option>Friend</option>
                                            <option>Private</option>
                                            <option>Circle</option>
                                        </select>
                                    </li>
                                    <li>
                                        <select id="selectCircle" disabled class="form-control">
                                            <c:forEach items="${circleList}" var="row">
                                                <option value="${row.getCircle_id()}">${row.getName()}</option>
                                            </c:forEach>
                                        </select>
                                    </li>
                                </ul>

                            </div>
                            <div>
                                <select id="selectLocation" class="form-control" name="location">
                                    <option value="0">Select location</option>
                                    <c:forEach items="${staticLocationList}" var="row">
                                        <option value="${row.getLocation_id()}">${row.getAttraction()}</option>
                                    </c:forEach>
                                </select>
                            </div>

                        </div>
                    </form>
                    <div id="map" class="center-block">
                        <p><button id="showLocation" class="btn center-block btn-primary">Show my location</button></p>
                        <div id="out" class="text-primary"></div>
                    </div>
                </div>
            </div>
        </div>

        <i id="user-id" value="${currentSessionUser.getUser_id()}"></i>
        <i id="user-true-name" value="${sessionScope.currentSessionUser.getName()}"></i>

        <!-- script references -->
        <script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
        <script type="text/javascript" src="js/typeahead.bundle.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="js/home.js"></script>
        <script src="js/sweetalert.min.js"></script>
        
    </body>
</html>
