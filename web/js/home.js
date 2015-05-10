$(document).ready(function () {/* off-canvas sidebar toggle */
    var id = $('#user-id').attr('value');
    var name = $('#user-true-name').attr('value');
    var path = window.location.pathname;
    var contextPath = path.slice(0, path.indexOf('/', 1));
    var circle_id_list = [];
    $('#circle-list li.list-group-item').each(function (index) {
        circle_id_list.push($(this).attr('value'));
    });

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
                if (message.location_id) $panelBody.append('<p><i class="glyphicon glyphicon-map-marker"></i> —at <strong style="color: #3B5999">' + $('select#selectLocation option[value="' + message.location_id + '"]').text() + '</strong></p>');
            });
            $('#messages').fadeIn('slow');
        });
    }

    function getAllPhotoAjax() {
        return $.getJSON(contextPath + '/api/getAllPhotos', {user_id: $('#user-id').attr('value')}, function (responseJson) {
            $.each(responseJson, function (index, photo) {
                var $panelWrapper = $('<div class="panel panel-default">').appendTo('#photos').append($('<div class="panel-thumbnail">').html('<img src="' + contextPath + '/images/' + photo.user_id + '/' + photo.photo_id + '.jpg" class="img-responsive">'));
                var $panelBody = $('<div class="panel-body">').appendTo($panelWrapper).append('<p class="lead">' + photo.user_name + '</p>').append('<p>' + photo.caption + '</p>');
                if(photo.location_id) $panelBody.append('<hr>').append($('<p>').html('<i class="glyphicon glyphicon-map-marker"></i> -at <strong style="color: #3B5999">' + $('select#selectLocation option[value="' + photo.location_id + '"]').text() + '</strong>'));
                $panelBody.append($('<div class="clearfix">')).append('<form><input type="text" class="form-control" placeholder="Add a comment.."></form>');
                $('#photos').fadeIn('slow');
            });
        });
    }

    $('#searchBtn').click(function(event) {
        event.preventDefault();
        $('#locations').hide('slow');
        var str = $('input#srch-term').val();
        if(str){
            $.getJSON(contextPath + '/api/search', {user_id: id, match: str, type: 'message'}, function(responseJson) {
                $.each(responseJson, function (index, message) {

                    var $panelWrapper = $('<div class="panel panel-default">').appendTo('#search-messages').append($('<div class="panel-heading">').html('<a href="#" class="pull-right">' + message.time + '</a><h4>' + message.user_name + '</h4>'));

                    var $panelBody = $('<div class="panel-body">').appendTo($panelWrapper).append('<p>' + message.text + '</p><div class="clearFix"></div><hr>');
                    if (message.location_id)
                        $panelBody.append('<p><i class="glyphicon glyphicon-map-marker"></i> —at <strong style="color: #3B5999">' + $('select#selectLocation option[value="' + message.location_id + '"]').text() + '</strong></p>');
                });
                $('#messages').fadeOut('slow');
                $('#search-messages').fadeIn('slow');
            });

            $.getJSON(contextPath + '/api/search', {user_id: id, match: str, type: 'photo'}, function (responseJson) {
                $.each(responseJson, function (index, photo) {
                    var $panelWrapper = $('<div class="panel panel-default">').appendTo('#search-photos').append($('<div class="panel-thumbnail">').html('<img src="' + contextPath + '/images/' + photo.user_id + '/' + photo.photo_id + '.jpg" class="img-responsive">'));
                    var $panelBody = $('<div class="panel-body">').appendTo($panelWrapper).append('<p class="lead">' + photo.user_name + '</p>').append('<p>' + photo.caption + '</p>');
                    $panelBody.append('<hr>').append($('<p>').html('<i class="glyphicon glyphicon-map-marker"></i> -at <strong style="color: #3B5999">' + $('select#selectLocation option[value="' + photo.location_id + '"]').text() + '</strong>'));
                    $panelBody.append($('<div class="clearfix">')).append('<form><input type="text" class="form-control" placeholder="Add a comment.."></form>');
                });
                $('#photos').fadeOut('slow');
                $('#search-photos').fadeIn('slow');
            });
        }
    });

    function getAllLocationAjax() {
        return $.getJSON(contextPath + '/api/getAllLocations', {user_id: $('#user-id').attr('value')}, function (responseJson) {
            $.each(responseJson, function (index, location) {

                var $panelWrapper = $('<div class="panel panel-default">').appendTo('#locations').append($('<div class="panel-heading">').html('<a href="#" class="pull-right">' + location.time + '</a><h4>' + location.attraction + '</h4>'));
                $('<div class="panel-body">').appendTo($panelWrapper).append('<p>' + location.name + ' is at <strong style="color: #3B5999">' + location.attraction + ', ' + location.city_name + '</strong></p><div class="clearFix"></div><hr>');
                $('#locations').fadeIn(1000);
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
    $.when(getAllMessageAjax()).done(getAllPhotoAjax()).done(getAllLocationAjax()).done(getAllUsersAjax());


//--------------------get friends in a circle-----------------

    $('#circle-list li.list-group-item.active').click(function (event) {
        $circleTitle = $(this);
        var circle_id = $(this).attr('value');
        $.ajax({
            url: contextPath + '/api/getUsersByCircle',
            type: 'GET',
            dataType: 'json',
            data: {circle_id: circle_id, user_id: id},
        }).done(function (users) {
            $.each(users, function (userIndex, user) {
                $circleTitle.after($('<li class="list-group-item circle-member" value="' + user.user_id + '">').text(user.name));
                $('#friend-list li.list-group-item[value="' + user.user_id + '"]').attr('data-circle', circle_id);
            });
            $circleTitle.prepend('<span class="badge pull-right">' + users.length + '</span>');
            $circleTitle.unbind('click');
        }).fail(function () {
            console.log("circle error on " + circle_id);
        })
    });


//-------------------Post Message----------------------

    $("button#status-submit").click(function () {
        var data = new FormData($('form#status')[0]);
        var privacy = $('#selectPrivacy').val().toLowerCase();
        var content = $('form#status textarea').val();
        var circle_id = privacy === 'circle' ? $('#selectCircle').val() : 0;
        var location_id = $('#selectLocation').val();
        data.append('user_id', id);
        data.append('circle_id', circle_id);
        if (content || $('#fileLabel').val()) {
            $.ajax({
                type: "POST",
                url: contextPath + '/api/postMessage',
                data: data,
                processData: false, // Don't process the files
                contentType: false, // Set content type to false as jQuery will tell the server its a query string request
                success: function (msg) {
                    if (msg === 'true') {
                        if ($('#fileLabel').val()) {
                            var $panelWrapper = $('<div class="panel panel-default">').prependTo('#photos').append($('<div class="panel-thumbnail">').html('<img src="' + $('#blah').attr('src') + '" class="img-responsive">'));
                            var $panelBody = $('<div class="panel-body">').appendTo($panelWrapper).append('<p class="lead">' + name + '</p>').append('<p>' + content + '</p>');
                            if (parseInt(location_id)) $panelBody.append('<hr>').append($('<p>').html('<i class="glyphicon glyphicon-map-marker"></i> -at <strong style="color: #3B5999">' + $('select#selectLocation option[value="' + location_id + '"]').text() + '</strong>'));
                            $panelBody.append($('<div class="clearfix">')).append('<form><input type="text" class="form-control" placeholder="Add a comment.."></form>');
                            $panelWrapper.fadeIn('slow');
                            $('#blah').attr('src', '');
                        } else {
                            var $panelWrapper = $('<div class="panel panel-default" style="display:none">').prependTo('#messages').append($('<div class="panel-heading">').html('<a href="#" class="pull-right">Just now</a><h4>' + name + '</h4>'));
                            var $panelBody = $('<div class="panel-body">').appendTo($panelWrapper).append('<p>' + content + '</p><div class="clearFix"></div><hr>');
                            if (parseInt(location_id)) $panelBody.append('<p><i class="glyphicon glyphicon-map-marker"></i> —at <strong style="color: #3B5999">' + $('select#selectLocation option[value="' + location_id + '"]').text() + '</strong></p>');
                            $panelWrapper.fadeIn(1000);
                        }
                    }
                    console.log(msg);
                    $('form#status textarea').val('');
                    $('#fileLabel').val('');
                    $('#fileLabel').parent().remove();
                },
                error: function () {
                    console.log('Post failure!');
                }
            });
        } else
            swal('Wait!', 'Message content cannot be empty!', 'error');
    });

//-------------------------Post Location--------------------------
    $('button#location-submit').click(function (event) {
        var location_id = parseInt($('#selectWellLocation').val());
        var privacy = $('#selectWellPrivacy').val().toLowerCase();
        var circle_id = privacy === 'circle' ? $('#selectWellCircle').val() : 0;
        var location_name = $('select#selectLocation option[value="' + location_id + '"]').text();
        var city_name = $('#selectWellLocation option[value="' + location_id + '"]').attr('rel')
        if (!location_id)
            swal('Wait!', 'You must choose a location', 'error');
        else {
            $.post(contextPath + '/api/postLocation', {
                user_id: id,
                location_id: location_id,
                privacy: privacy,
                circle_id: circle_id
            }, function (msg) {
                console.log(msg);
                if (msg === 'true') {
                    var $panelWrapper = $('<div class="panel panel-default" style="display:none">').insertAfter('#search-friend-well').append($('<div class="panel-heading">').html('<a href="#" class="pull-right">Just now</a><h4>' + location_name + '</h4>'));
                    $('<div class="panel-body">').appendTo($panelWrapper).append('<p>' + name + ' is at <strong style="color: #3B5999">' + location_name + ', ' + city_name + '</strong></p><div class="clearFix"></div><hr>');
                    $panelWrapper.fadeIn(1000);
                }
            });
        }
    });

//--------------get all friend list and edit friends---------------
    $('#friend-view-btn').click(function (event) {
        $('#main-view').fadeOut(300, function () {
            $('#friend-view').fadeIn(300);
        });

        $.get(contextPath + '/api/getAllFriends', {user_id: id}, function (friend_list) {

            if (friend_list !== 'empty') {
                if (friend_list.length !== $('#friend-list ul.list-group .list-group-item').length) {
                    $('#friend-list ul.list-group .list-group-item').remove();
                    friend_list.forEach(function (item) {
                        $('#friend-list .list-group').append('<li href="#" class="list-group-item" rel="' + item.name + '" value="' + item.user_id + '" data-toggle="popover" title="Connections" data-placement="left">' + item.name + dropDownHtml + '</li>');
                        
                        $.get(contextPath + '/api/getAllFriends',{user_id: item.user_id}, function (ff_list) {
                            var $target = $('#friend-list .list-group li.list-group-item[value="'+item.user_id+'"]');
                            var popover_content = '';
                            $.each(ff_list, function (index, ff) {
                                popover_content += ff.name + '; '
                            });
                            $target.attr('data-content', popover_content);
                        });
                        
                    })

                    $('[data-toggle="popover"]').popover();
                }
            }

            //----------------join a circle-------------------------------

            $('#friend-list .list-group-item a[role="circle"]').click(function (event) {
                $("#editCircleBtn").unbind('click');
                var $friend_item = $(this).parents('li.list-group-item');
                var friend_user_id = $friend_item.attr('value');
                var friend_name = $friend_item.attr('rel');
                if ($friend_item.attr('data-circle') && $('#editCircleModal .modal-body').has('#warning-join-circle').length === 0) {
                    $('#editCircleModal .modal-body').prepend('<strong id="warning-join-circle">This user already joined a circle!</strong>');
                    $('#selectCircleEdit').attr('disabled', 'disabled');
                    $("#editCircleBtn").attr('disabled', 'disabled');
                } else if (!$friend_item.attr('data-circle')) {
                    $('strong#warning-join-circle').remove();
                    $('#selectCircleEdit').removeAttr('disabled');
                    $("#editCircleBtn").removeAttr('disabled');
                    $("#editCircleBtn").click(function (event) {
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
                                if (msg === 'true') {
                                    $friend_item.attr('data-circle', circle);
                                    $circleTitle = $('#circle-list li.active.list-group-item[value=' + circle + ']');
                                    $circleTitle.after($('<li class="list-group-item circle-member" value="' + friend_user_id + '">').text(friend_name));
                                    var pre = parseInt($circleTitle.children('span').text());
                                    $circleTitle.children('span').text(pre + 1);
                                }
                                else
                                    console.log('Not added, server side error!')
                            },
                            error: function () {
                                console.log('Post failure!');
                            }
                        });
                    });
                }
            });

            //--------------------remove friend from circle-----------
            $('.friend-dropdown ul.dropdown-menu a[role="delete-circle"]').click(function (event) {
                var $friend_item = $(this).parents('li.list-group-item');
                var friend_user_id = $friend_item.attr('value');
                var circle_id = $friend_item.attr('data-circle');
                $.post(contextPath + '/api/quitCircle', {circle_id: circle_id, friend_user_id: friend_user_id}, function (data) {
                    if (data === 'true') {
                        swal('Quit Done!', '', 'success');
                        $friend_item.removeAttr('data-circle');
                        $('li.circle-member[value="' + friend_user_id + '"]').remove();
                        $circleSize = $('#circle-list li.active.list-group-item[value=' + circle_id + '] span.badge');
                        var pre = parseInt($circleSize.text());
                        $circleSize.text(pre - 1);
                    }
                });
            });

            //--------------------remove friend----------
            $('.friend-dropdown ul.dropdown-menu a[role="unfriend"]').click(function (event) {
                var $friend_item = $(this).parents('li.list-group-item');
                var friend_user_id = $friend_item.attr('value');
                var user_id = id;
                var circle_id = $friend_item.attr('data-circle');
                console.log(friend_user_id + ' ' + user_id);
                $.post(contextPath + '/api/unfriend', {user_id: user_id, friend_user_id: friend_user_id}, function (data) {
                    console.log(data);
                    if (data === 'true') {
                        swal('Remove!', '', 'success');
                        $('li.circle-member[value="' + friend_user_id + '"]').remove();
                        $circleSize = $('#circle-list li.active.list-group-item[value=' + circle_id + '] span.badge');
                        $('#friend-list li.list-group-item[value="' + friend_user_id + '"]').remove();
                        var pre = parseInt($circleSize.text());
                        $circleSize.text(pre - 1);
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
                    var $newCircleItem = $('<li class="list-group-item active">').appendTo('#circle-list').append('<span class="badge">0</span><strong>' + circleName + '</strong>');
                    $newCircleItem.fadeIn('slow');
                    $('#selectCircleEdit').append('<option disabled>' + circleName + ' (You have to refresh the page)</option>');
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
        if (filePath) {
            var fileName = filePath.slice(filePath.lastIndexOf('\\') + 1);

            if (!$('.modal-footer#modalFooterPost').has('#fileLabel').length) {
                $('.modal-footer#modalFooterPost').append('<div><input id="fileLabel" disabled class="form-control pull-left"/></div>');
            }

            $('input#fileLabel').val(fileName);
        } else
            $('input#fileLabel').parent().remove();

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


//--------------------------respond to a friend request----------------------
    $(".dropdown-alerts button.btn-circle").click(function (event) {
        event.stopPropagation();
        var friend_user_id = $(this).val();
        var $item = $('ul.dropdown-alerts li[value=' + friend_user_id + ']');
        var accept = $(this).attr('rel');
        $.post(contextPath + '/api/respond', {friend_user_id: friend_user_id, user_id: id, accept: accept}, function (data) {
            if (data === 'true') {
                $item.hide('slow');
                $item.next('.divider').hide('slow');
                var requestNumber = parseInt($('#friendRequest span.badge').text());
                requestNumber -= 1;
                $('#friendRequest span.badge').text(requestNumber);
            } else
                swal('Error!', 'Server Error', 'error');
        });
    });
//---------------------------Realtime Change------------------------------
    $('#selectWellLocation').change(function (event) {
        $('#cityName').text($('#selectWellLocation option:selected').attr('rel'));
    });

    $('#selectPrivacy').change(function () {
        if ($(this).val() === "Circle") {
            $('#selectCircle').removeAttr('disabled');
        } else
            $('#selectCircle').attr('disabled', 'disabled');
    });

    $('#selectWellPrivacy').change(function () {
        if ($(this).val() === "Circle") {
            $('#selectWellCircle').removeAttr('disabled');
        } else
            $('#selectWellCircle').attr('disabled', 'disabled');
    });

    function readURL(input) {

        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#blah').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }

    function geoFindMe() {
      var output = document.getElementById("out");

      if (!navigator.geolocation){
        output.innerHTML = "<p>Geolocation is not supported by your browser</p>";
        return;
      }

      function success(position) {
        var latitude  = position.coords.latitude;
        var longitude = position.coords.longitude;

        output.innerHTML = '<p class="text-center">Latitude is ' + latitude + '° <br>Longitude is ' + longitude + '°</p>';

        var img = new Image();
        img.src = "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=13&size=300x300&sensor=false";

        output.appendChild(img);
        $('#out img').css({
            display: 'block',
            marginRight: 'auto',
            marginLeft: 'auto'
        });
        $('#selectLocation').empty();
        $('#selectLocation').append('<option value="0">Select location</option>');
        $('#selectLocation').append('<option value="1">Wall Street</option>');
        $('#selectLocation').append('<option value="3">Statue of Liberty</option>');
        $('#selectLocation').append('<option value="4">Time Square</option>');
      };

      function error() {
        output.innerHTML = "Unable to retrieve your location";
      };

      output.innerHTML = '<p class="text-center">Locating…</p>';

      navigator.geolocation.getCurrentPosition(success, error);
    }

    $('#showLocation').click(geoFindMe);
    $("#uploadBtn").change(function(){
        readURL(this);
    });



});