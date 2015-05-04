<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-md-3">

        <ul class="list-group" id="circle-list">
            <a href="#" class="list-group-item" data-toggle="modal" data-target="#newCircleModal">Create new circle<i class="glyphicon glyphicon-plus pull-right"></i></a>
            <c:forEach items="${circleList}" var="circle">

                <li class="list-group-item active" value="${circle.getCircle_id()}">
                    <strong><c:out value="${circle.getName()}" /></strong>
                </li>


            </c:forEach>
        </ul>

    </div>

    <div class="col-md-9">
        <div class="modal fade" id="newCircleModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Create New Circle</h4>

                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label for="recipient-name" class="control-label">Circle Name:</label>
                                <input type="text" class="form-control" id="circle-name">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" data-dismiss="modal" id="createCircleBtn">Create</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

         <div class="panel panel-default">
            <div class="panel-heading"><a href="#" class="pull-right">View all</a> <h4>My Friends</h4></div>
            <div class="panel-body" id="friend-list">
                <ul class="list-group">
                </ul>
            </div>
        </div> 

        <div class="modal fade" id="editCircleModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Select Circle</h4>

                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <select id="selectCircleEdit" class="form-control">
                                    <c:forEach items="${circleList}" var="row">
                                        <option value="${row.getCircle_id()}">${row.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" data-dismiss="modal" id="editCircleBtn">Add</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>
</div>