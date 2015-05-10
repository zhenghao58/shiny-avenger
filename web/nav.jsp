<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar navbar-blue navbar-static-top">  
    <div class="navbar-header">
        <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a href="" class="navbar-brand logo">b</a>
    </div>
    <nav class="collapse navbar-collapse" role="navigation">
        <form class="navbar-form navbar-left">
            <div class="input-group input-group-sm" style="max-width:360px;">
                <input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
                <div class="input-group-btn">
                    <button class="btn btn-default" type="submit" id="searchBtn"><i class="glyphicon glyphicon-search"></i></button>
                </div>
            </div>
        </form>
        <ul class="nav navbar-nav">
            <li>
                <a href="#"><i class="glyphicon glyphicon-home"></i> Home</a>
            </li>
            <li>
                <a href="#postModal" role="button" data-toggle="modal"><i class="glyphicon glyphicon-plus"></i> Post</a>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right navbar-top-links">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="glyphicon glyphicon-user"></i> ${sessionScope.currentSessionUser.getName()}
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li> 
                </ul>
            </li>
            <li class="dropdown" id="friendRequest">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-bell fa-fw"></i>
                    <c:if test="${!requestList.isEmpty()}">
                        <span class="badge">${requestList.size()}</span>
                    </c:if>
                    <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-alerts">
                    <c:forEach items="${requestList}" var="row">
                        <li value="${row.getUser_id()}">
                            <a>
                                <div>
                                    <p>
                                        <strong>${row.getName()}</strong>

                                        <span class="pull-right text-muted">4 minutes ago</span>
                                        <div class="friend-request-btn">
                                            <button type="button" class="btn btn-info btn-circle" rel="true" value="${row.getUser_id()}">
                                                <i class="fa fa-check"></i>
                                            </button>
                                            <button type="button" class="btn btn-warning btn-circle pull-right" rel="false" value="${row.getUser_id()}">
                                                <i class="fa fa-times"></i>
                                            </button>
                                        </div>
                                    </p>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                    </c:forEach>
                    <li>
                        <a class="text-center">
                            <strong>New Friend Requests</strong>
                        </a>
                    </li>
                </ul>
                <!-- /.dropdown-alerts -->
            </li>
        </ul>
    </nav>
</div>