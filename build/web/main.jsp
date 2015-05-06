<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- content -->                      
<div class="row">

    <!-- main col left --> 
    <div class="col-sm-5">
        <div class="well"> 
           <form class="form-horizontal" role="form">
            <h4>Where are you now?</h4>
                <div class="form-group" style="padding:8px;margin-bottom: 0px">
                    <div>
                        <select id="selectWellLocation" class="form-control" name="location">
                            <option value="0">Select location</option>
                            <c:forEach items="${staticLocationList}" var="row">
                                <option value="${row.getLocation_id()}" rel="${row.getCity_name()}">${row.getAttraction()}</option>
                            </c:forEach>
                        </select>
                        <ul class="pull-left list-inline">
                            <li>
                                <select id="selectWellPrivacy" class="form-control" name="privacy">
                                    <option>Public</option>
                                    <option>Friend</option>
                                    <option>Private</option>
                                    <option>Circle</option>
                                </select>
                            </li>
                            <li>
                                <select id="selectWellCircle" disabled class="form-control">
                                    <c:forEach items="${circleList}" var="row">
                                        <option value="${row.getCircle_id()}">${row.getName()}</option>
                                    </c:forEach>
                                </select>
                            </li>
                        </ul>
                    </div>
                </div>
            <button class="btn btn-primary btn-sm pull-right" id="location-submit" type="button">Post</button>
            <ul class="list-inline">
                <li><a href=""><i class="glyphicon glyphicon-map-marker"></i></a></li>
                <li id="cityName"></li>
            </ul>
          </form>
        </div>
        <div id="photos" style="display:none">
        </div>

<!--         <div class="panel panel-default">
            <div class="panel-heading"><a href="#" class="pull-right">Boot</a> <h4>Hola</h4></div>
            <div class="panel-body">
                <img src="//placehold.it/150x150" class="img-circle pull-right"> <a href="#">Free @Bootply</a>
                <div class="clearfix"></div>
                Bootstrap is front end frameworkto build custom web applications that are fast.
                <hr>
                <ul class="list-unstyled"><li><a href="#">Dashboard</a></li><li><a href="#">Darkside</a></li><li><a href="#">Greenfield</a></li></ul>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading"><h4>What Is Bootstrap?</h4></div>
            <div class="panel-body">
                Bootstrap is front end frameworkto build custom web applications that are fast, responsive &amp; intuitive. It consist of CSS and HTML for typography, forms, buttons, tables, grids, and navigation along with custom-built jQuery plug-ins and support for responsive layouts. With dozens of reusable components for navigation, pagination, labels, alerts etc..            
            </div>
        </div> -->
    </div>

    <!-- main col right -->
    <div class="col-sm-7">
        <div class="well" id="search-friend-well"> 
            <form class="form" id="search-friend-form">
                <h4>Find some friends</h4>
                <div class="input-group text-center">
                    <input type="text" class="form-control input-lg typeahead" id="search-friend" placeholder="Enter the name">
                    <span class="input-group-btn">
                        <button class="btn btn-lg btn-primary" type="button">Send Request</button>
                    </span>
                </div>
            </form>
        </div>

        <div id="messages" style="display:none">
        </div>
        <div id="locations" style="display:none">
        </div>
    </div>
</div><!--/row-->
