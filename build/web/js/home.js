$(document).ready(function () {/* off-canvas sidebar toggle */
    var id = $('#user-id').attr('value');
    var name = $('#user-true-name').attr('value');
    var path = window.location.pathname;
    var contextPath = path.slice(0, path.indexOf('/', 1));
    $('[data-toggle=offcanvas]').click(function () {
        $(this).toggleClass('visible-xs text-center');
        $(this).find('i').toggleClass('glyphicon-chevron-right glyphicon-chevron-left');
        $('.row-offcanvas').toggleClass('active');
        $('#lg-menu').toggleClass('hidden-xs').toggleClass('visible-xs');
        $('#xs-menu').toggleClass('visible-xs').toggleClass('hidden-xs');
        $('#btnShow').toggle();
    });

    $.getJSON(contextPath + '/api/getAllMessages', {user_id: id}, function (responseJson) {
        $.each(responseJson, function (index, message) {
            var $panelWrapper = $('<div class="panel panel-default">').appendTo('#messages').append($('<div class="panel-heading">').html('<a href="#" class="pull-right">' + message.time + '</a><h4>' + message.user_name + '</h4>'));
            $('<div class="panel-body">').appendTo($panelWrapper).append('<p>' + message.text + '</p><div class="clearFix"></div><hr>');
        });
        $('#messages').fadeIn('slow');
    });


    $("button#status-submit").click(function () {
        var privacy = $('#selectPrivacy').val().toLowerCase();
        var content = $('form#status textarea').val();
        $.ajax({
            type: "POST",
            url: contextPath + '/api/postMessage',
            data: {
                text: content,
                user_id: id,
                privacy: privacy,
                circle_id: privacy === 'circle' ? $('#selectCircle').val() : 0
            },
            success: function (msg) {
                $('form#status textarea').val('');
                var $panelWrapper = $('<div class="panel panel-default" style="display:none">').prependTo('#messages').append($('<div class="panel-heading">').html('<a href="#" class="pull-right">Just now</a><h4>' + name + '</h4>'));
                $('<div class="panel-body">').appendTo($panelWrapper).append('<p>' + content + '</p><div class="clearFix"></div><hr>');
                $panelWrapper.fadeIn(1000);
            },
            error: function () {
                console.log('Post failure!');
            }
        });
    });
    
    $('#friend-view-btn').click(function(event) {
        $('#main-view').fadeOut(600, function() {
            $('#friend-view').fadeIn(700);
        });
    });

    $('#main-view-btn').click(function(event) {
        $('#friend-view').fadeOut(600, function() {
            $('#main-view').fadeIn(700);
        });
    });

    $('#uploadBtn').change(function () {
        if (!$('.modal-footer').has('#fileLabel').length) {
            $('.modal-footer').append('<div><input id="fileLabel" disabled="disabled" class="form-control pull-left"/></div>');
            console.log('here');
        }
        var filePath = $(this).val();
        var fileName = filePath.slice(filePath.lastIndexOf('\\') + 1);
        $('#fileLabel').val(fileName);
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
        // $item.hide('slow');
        // $item.next('.divider').hide('slow');
    });


    $.getJSON(contextPath + '/api/getAllUsers', {user_id: id}, function (data) {
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

    $('#selectPrivacy').change(function () {
        if ($(this).val() === "Circle") {
            $('#selectCircle').removeAttr('disabled');
        } else
            $('#selectCircle').attr('disabled', 'disabled');
    });




});