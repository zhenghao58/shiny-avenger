$(document).ready(function () {/* off-canvas sidebar toggle */
    var id = $('#user-id').attr('value');
    var name = $('#user-true-name').attr('value');
    var path = window.location.pathname;
    var contextPath = path.slice(0, path.indexOf('/', 1));
    var circle_id_list = [];
    $('#circle-list li.list-group-item').each(function (index) {
        circle_id_list.push($(this).attr('value'));
    })
    var hasReceivedAllFriends = false;
    var dropDownHtml = '<div class="friend-dropdown pull-right"> \
        <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="true">Action\
        <span class="caret"></span>\
        </button>\
        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">\
        <li role="presentation"><a role="circle" tabindex="-1" href="#" data-toggle="modal" data-target="#editCircleModal">Edit circle</a></li>\
        <li role="presentation"><a role="delete-circle" tabindex="-1" href="#">Quit current circle</a></li>\
        <li role="presentation"><a role="unfriend" tabindex="-1" href="#">Unfriend</a></li>\
        </ul>\
        </div>';

    $('[data-toggle=offcanvas]').click(function () {
        $(this).toggleClass('visible-xs text-center');
        $(this).find('i').toggleClass('glyphicon-chevron-right glyphicon-chevron-left');
        $('.row-offcanvas').toggleClass('active');
        $('#lg-menu').toggleClass('hidden-xs').toggleClass('visible-xs');
        $('#xs-menu').toggleClass('visible-xs').toggleClass('hidden-xs');
        $('#btnShow').toggle();
    });

    function getAllMessageAjax() {
        return $.getJSON(contextPath + '/api/getAllMessages', {user_id: $('#user-id').attr('value')}, function (responseJson) {
            $.each(responseJson, function (index, message) {

                var $panelWrapper = $('<div class="panel panel-default">').appendTo('#messages').append($('<div class="panel-heading">').html('<a href="#" class="pull-right">' + message.time + '</a><h4>' + message.user_name + '</h4>'));
                
                var $panelBody = $('<div class="panel-body">').appendTo($panelWrapper).append('<p>' + message.text + '</p><div class="clearFix"></div><hr>');
                if(message.location_id) $panelBody.append('<p><i class="glyphicon glyphicon-map-marker"></i> —at <strong style="color: #3B5999">'+$('select#selectLocation option[value="'+message.location_id+'"]').text()+'</strong></p>');
            });
            $('#messages').fadeIn('slow');  
        });
    }

    function getAllPhotoAjax(){
        return $.getJSON(contextPath + '/api/getAllPhotos', {user_id: $('#user-id').attr('value')}, function(responseJson) {
            $.each(responseJson, function(index, photo) {
                var $panelWrapper = $('<div class="panel panel-default">').appendTo('#photos').append($('<div class="panel-thumbnail">').html('<img src="'+contextPath+'/images/'+photo.user_id+'/'+photo.photo_id+'.jpg" class="img-responsive">'));
                var $panelBody = $('<div class="panel-body">').appendTo($panelWrapper).append('<p class="lead">'+photo.user_name+'</p>').append('<p>'+photo.caption+'</p>');
                $panelBody.append('<hr>').append($('<p>').html('<i class="glyphicon glyphicon-map-marker"></i> -at <strong style="color: #3B5999">'+$('select#selectLocation option[value="'+photo.location_id+'"]').text()+'</strong>'));
                $panelBody.append($('<div class="clearfix">')).append('<form><input type="text" class="form-control" placeholder="Add a comment.."></form>');
                $('#photos').fadeIn('slow'); 
            });
        });
    }

    function getAllUsersAjax() {
        return $.getJSON(contextPath + '/api/getAllUsers', {user_id: $('#user-id').attr('value')}, function (data) {
            var allUsers = new Bloodhound({
                datumTokenizer: Bloodhound.tokenizers.whitespace,
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                local: data
            });

            $('.typeahead').typeahead({
                hint: true,
                highlight: true,
                minLength: 1
            },
            {
                name: 'allUsers',
                source: allUsers
            });
        });
    }

//----------------------------------queue Ajax solution 1 not suggested-------------------------
    // $.when(getAllPhotoAjax(), getAllMessageAjax(), getAllUsersAjax()).done(function(a1, a2, a3){
    //     console.log('Initialize finished!');
    // })

//----------------------------------queue Ajax solution 2-------------------------
    // $.when(getAllPhotoAjax())
    //     .done(function() {
    //         getAllMessageAjax();
    //     })
    //     .done(function() {
    //         getAllUsersAjax();
    //     })

//----------------------------------queue Ajax solution 3-------------------------
    $.when(getAllPhotoAjax()).done(getAllMessageAjax()).done(getAllUsersAjax());


//--------------------get friends in a circle-----------------

    $('#circle-list li.list-group-item.active').click(function(event) {
        $circleTitle=$(this);
        var circle_id = $(this).attr('value');
        $.ajax({
            url: contextPath + '/api/getUsersByCircle',
            type: 'GET',
            dataType: 'json',
            data: {circle_id: circle_id, user_id: id},
        })
        .done(function(users) {
            $.each(users, function (userIndex, user) {
                $circleTitle.after($('<li class="list-group-item circle-member" value="'+user.user_id+'">').text(user.name));
                $('#friend-list li.list-group-item[value="'+user.user_id+'"]').attr('data-circle', circle_id);
            });
            $circleTitle.prepend('<span class="badge pull-right">' + users.length + '</span>');
            $circleTitle.unbind('click');
        })
        .fail(function() {
            console.log("circle error on "+ circle_id);
        })
    });
    

//-------------------Post Message----------------------

    $("button#status-submit").click(function () {
        var data = new FormData($('form#status')[0]);
        var privacy = $('#selectPrivacy').val().toLowerCase();
        var content = $('form#status textarea').val();
        var circle_id = privacy === 'circle' ? $('#selectCircle').val() : 0;
        data.append('user_id', id);
        data.append('circle_id', circle_id);
        if (content||$('#fileLabel').val()) {
            $.ajax({
                type: "POST",
                url: contextPath + '/api/postMessage',
                data: data,
                processData: false, // Don't process the files
                contentType: false, // Set content type to false as jQuery will tell the server its a query string request
                success: function (msg) {
                    if(msg==='true'){
                        var $panelWrapper = $('<div class="panel panel-default" style="display:none">').prependTo('#messages').append($('<div class="panel-heading">').html('<a href="#" class="pull-right">Just now</a><h4>' + name + '</h4>'));
                        $('<div class="panel-body">').appendTo($panelWrapper).append('<p>' + content + '</p><div class="clearFix"></div><hr>');
                        $panelWrapper.fadeIn(1000);  
                    }
                    console.log(msg);
                    $('form#status textarea').val('');
                    $('#fileLabel').val('');
                },
                error: function () {
                    console.log('Post failure!');
                }
            });
        } else swal('Error!', 'Message content cannot be empty!', 'error');
    });

//--------------get all friend list and edit friends---------------
    $('#friend-view-btn').click(function (event) {
        $('#main-view').fadeOut(300, function () {
            $('#friend-view').fadeIn(300);
        });

        $.get(contextPath + '/api/getAllFriends', {user_id: id}, function (friend_list) {

            if (friend_list !== 'empty') {
                    if(friend_list.length !== $('#friend-list ul.list-group .list-group-item').length){
                        $('#friend-list ul.list-group .list-group-item').remove();
                        friend_list.forEach(function (item) {
                            $('#friend-list .list-group').append('<li href="#" class="list-group-item" rel="'+item.name+'" value="' + item.user_id + '">' + item.name+dropDownHtml+'</li>');
                        })
                    }
            }

            //----------------join a circle-------------------------------

            $('#friend-list .list-group-item a[role="circle"]').click(function(event) {
                $("#editCircleBtn").unbind('click');
                var $friend_item = $(this).parents('li.list-group-item');
                var friend_user_id = $friend_item.attr('value');
                var friend_name = $friend_item.attr('rel');
                if($friend_item.attr('data-circle')&& $('#editCircleModal .modal-body').has('#warning-join-circle').length===0) {
                        $('#editCircleModal .modal-body').prepend('<strong id="warning-join-circle">This user already joined a circle!</strong>');
                        $('#selectCircleEdit').attr('disabled', 'disabled');
                        $("#editCircleBtn").attr('disabled', 'disabled');
                }else if(!$friend_item.attr('data-circle')){
                    $('strong#warning-join-circle').remove();
                    $('#selectCircleEdit').removeAttr('disabled');
                    $("#editCircleBtn").removeAttr('disabled');
                    $("#editCircleBtn").click(function(event){
                        var circle = $('#selectCircleEdit').val();
                        console.log('4');
                        $.ajax({
                            type: "POST",
                            url: contextPath + '/api/joinCircle',
                            data: {
                                friend_user_id: friend_user_id,
                                circle_id: circle
                            },
                            success: function (msg) {
                                if(msg==='true') {
                                    $friend_item.attr('data-circle', circle);
                                    $circleTitle = $('#circle-list li.active.list-group-item[value='+circle+']');
                                    $circleTitle.after($('<li class="list-group-item circle-member" value="'+friend_user_id+'">').text(friend_name));
                                    var pre = parseInt($circleTitle.children('span').text());
                                    $circleTitle.children('span').text(pre+1);
                                }
                                else console.log('Not added, server side error!')
                            },
                            error: function () {
                                console.log('Post failure!');
                            }
                        });
                    });
                }
            });

//--------------------remove friend from circle-----------
            $('.friend-dropdown ul.dropdown-menu a[role="delete-circle"]').click(function(event) {
                var $friend_item = $(this).parents('li.list-group-item');
                var friend_user_id = $friend_item.attr('value');
                var circle_id = $friend_item.attr('data-circle');
                $.post(contextPath + '/api/quitCircle', {circle_id: circle_id, friend_user_id: friend_user_id}, function(data) {
                    if(data==='true'){
                        swal('Quit Done!', '', 'success');
                        $friend_item.removeAttr('data-circle');
                        $('li.circle-member[value="'+friend_user_id+'"]').remove();
                        $circleSize = $('#circle-list li.active.list-group-item[value='+circle_id+'] span.badge');
                        var pre = parseInt($circleSize.text());
                        $circleSize.text(pre-1);
                    }
                });
            });
        });  
    });

//----------------------Sidebar Switch----------------------
    $('#main-view-btn').click(function (event) {
        $('#friend-view').fadeOut(300, function () {
            $('#main-view').fadeIn(300);
        });
    });

    $('#friend-view .list-group > a').click(function (event) {
        $('#friend-view .well').fadeToggle('fast');
    });


//----------------Create a new circle---------------
    $('#createCircleBtn').click(function (event) {
        var circleName = $('#circle-name').val();
        if (circleName) {
            $.post(contextPath + '/api/addNewCircle', {circle_name: circleName, user_id: id}, function (data) {
                if (data === 'true') {
                    $('#circle-name').val('');
                    swal('Circle Created!', '', 'success');
                    var $newCircleItem = $('<li class="list-group-item active">').appendTo('#circle-list').append('<span class="badge">0</span><strong>' + circleName+'</strong>');
                    $newCircleItem.fadeIn('slow');
                }
                else
                    swal('Error!', 'Server Error', 'error');
            });
        } else
            swal('Error!', 'You must enter the name!', 'error');
    });
//-------------Upload Photo-----------------------
    $('#uploadBtn').change(function () {
        var filePath = $(this).val();
        if(filePath){
            var fileName = filePath.slice(filePath.lastIndexOf('\\') + 1);
            
            if (!$('.modal-footer#modalFooterPost').has('#fileLabel').length) {
                $('.modal-footer#modalFooterPost').append('<div><input id="fileLabel" disabled class="form-control pull-left"/></div>');
            }

            $('input#fileLabel').val(fileName);
        }else $('input#fileLabel').parent().remove();

    });


    $("#search-friend-form button").click(function (event) {
        swal({title: "Send Request?",
            text: 'You will send the friend request to this guy.',
            type: "info",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes!",
            closeOnConfirm: false
        }, function () {
            $.post(contextPath + '/api/request', {friend_name: $('#search-friend').val().trim(), user_id: id}, function (data) {
                if (data === 'true')
                    swal('Sent!', '', 'success');
                else
                    swal('Error!', 'Server Error', 'error');
            });
        });
    });

    $(".dropdown-alerts button.btn-circle").click(function (event) {
        event.stopPropagation();
        var friend_user_id = $(this).val();
        var $item = $('ul.dropdown-alerts li[value=' + friend_user_id + ']');
        var accept = $(this).attr('rel');
        $.post(contextPath + '/api/respond', {friend_user_id: friend_user_id, user_id: id, accept: accept}, function (data) {
            if (data === 'true') {
                $item.hide('slow');
                $item.next('.divider').hide('slow');
            } else
                swal('Error!', 'Server Error', 'error');
        });
    });

    $('#selectPrivacy').change(function () {
        if ($(this).val() === "Circle") {
            $('#selectCircle').removeAttr('disabled');
        } else
            $('#selectCircle').attr('disabled', 'disabled');
    });



});