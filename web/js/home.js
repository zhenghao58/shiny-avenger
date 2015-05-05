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
                $('<div class="panel-body">').appendTo($panelWrapper).append('<p>' + message.text + '</p><div class="clearFix"></div><hr>');
            });
            $('#messages').fadeIn('slow');  
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

    $.when(getAllMessageAjax(), getAllUsersAjax()).done(function(a1, a2){
        console.log('Initialize finished!');
    })

//--------------------get friends in a circle-----------------

    $('#circle-list li.list-group-item').click(function(event) {
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
            
        })
        .fail(function() {
            console.log("circle error on "+ circle);
        })
    });
    

//---------Post Message----------------------

    $("button#status-submit").click(function () {
        var data = new FormData($('form#status')[0]);
        var privacy = $('#selectPrivacy').val().toLowerCase();
        var content = $('form#status textarea').val();
        var circle_id = privacy === 'circle' ? $('#selectCircle').val() : 0;
        data.append('user_id', id);
        data.append('circle_id', circle_id);
        if (content) {
            $.ajax({
                type: "POST",
                url: contextPath + '/api/postMessage',
                // data: {
                //     text: content,
                //     user_id: id,
                //     privacy: privacy,
                //     circle_id: privacy === 'circle' ? $('#selectCircle').val() : 0 ,
                //     file:file
                // },
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

                },
                error: function () {
                    console.log('Post failure!');
                }
            });
        } else swal('Error!', 'Message content cannot be empty!', 'error');
    });

//---------get all friend list and edit friends---------------
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

            $('.friend-dropdown ul.dropdown-menu a[role="circle"]').click(function(event) {
                var $friend_item = $(this).parents('li.list-group-item');
                var friend_user_id = $friend_item.attr('value');
                var friend_name = $friend_item.attr('rel');
                console.log($('#editCircleModal .modal-body').has('#warning-join-circle').length);
                if($friend_item.attr('data-circle')&& $('#editCircleModal .modal-body').has('#warning-join-circle').length===0) {
                        $('#editCircleModal .modal-body').prepend('<strong id="warning-join-circle">This user already joined a circle!</strong>');
                        $('#selectCircleEdit').attr('disabled', 'disabled');
                        $("#editCircleBtn").attr('disabled', 'disabled');
                }else{
                    $('strong#warning-join-circle').remove();
                    $('#selectCircleEdit').removeAttr('disabled');
                    $("#editCircleBtn").removeAttr('disabled'); 
                    $("#editCircleBtn").click(function () {
                        var circle = $('#selectCircleEdit').val();

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
                                    $('#circle-list li.list-group-item[value='+circle+']').after($('<li class="list-group-item circle-member" value="'+friend_user_id+'">').text(friend_name));
                                }
                                else console.log('Not added!')
                            },
                            error: function () {
                                console.log('Post failure!');
                            }
                        });
                    });
                }
            });

            $('.friend-dropdown ul.dropdown-menu a[role="delete-circle"]').click(function(event) {
                var $friend_item = $(this).parents('li.list-group-item');
                var friend_user_id = $friend_item.attr('value');
                var circle_id = $friend_item.attr('data-circle');
                $.post(contextPath + '/api/quitCircle', {circle_id: circle_id, friend_user_id: friend_user_id}, function(data) {
                    if(data==='true'){
                        swal('Quit Done!', '', 'success');
                        $('li.circle-member[value="'+friend_user_id+'"]').remove();
                    }
                });
            });
            
        });
        
    });




    $('#main-view-btn').click(function (event) {
        $('#friend-view').fadeOut(300, function () {
            $('#main-view').fadeIn(300);
        });
    });

    $('#friend-view .list-group > a').click(function (event) {
        $('#friend-view .well').fadeToggle('fast');
    });


//----------create a new circle---------------
    $('#createCircleBtn').click(function (event) {
        var circleName = $('#circle-name').val();
        if (circleName) {
            $.post(contextPath + '/api/addNewCircle', {circle_name: circleName, user_id: id}, function (data) {
                if (data === 'true') {
                    $('#circle-name').val('');
                    swal('Circle Created!', '', 'success');
                    var $newCircleItem = $('<li class="list-group-item">').appendTo('#circle-list').append('<span class="badge">0</span><strong>' + circleName+'</strong>');
                    $newCircleItem.fadeIn('slow');
                }
                else
                    swal('Error!', 'Server Error', 'error');
            });
        } else
            swal('Error!', 'You must enter the name!', 'error');
    });

    $('#uploadBtn').change(function () {
        var filePath = $(this).val();
        var fileName = filePath.slice(filePath.lastIndexOf('\\') + 1);
        
        if (!$('.modal-footer').has('#fileLabel').length) {
            $('.modal-footer').append('<div><input id="fileLabel" disabled class="form-control pull-left"/></div>');
        }

        $('input#fileLabel').val(fileName);
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