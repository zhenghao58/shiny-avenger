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
        <div id="search-photos" style="display:none">
        </div>
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
        <hr>
        <p class="lead">Messages Feed</p>
        <div id="messages" style="display:none">
        </div>
        <div id="search-messages" style="display:none">
        </div>
        <hr>
        <p class="lead">Location Feed</p>
        <div id="locations" style="display:none">
        </div>
    </div>
</div><!--/row-->
