
$(document).ready(function () {/* off-canvas sidebar toggle */
    var id = $('i#user-id').attr('value');
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

    $("button#status-submit").click(function () {
        console.log($('i#user-id').attr('value'));
        $.ajax({
            type: "POST",
            url: contextPath + '/api/postMessage',
            data: {
                text: $('form#status textarea').val(),
                user_id: id,
                privacy: "public"
            },
            success: function (msg) {
                console.log(msg);
                console.log(msg.length);
            },
            error: function () {
                alert("failure");
            }
        });
    });

    $("#search-friend-form button").click(function (event) {
        swal({title: "Send Request?",
            text: "You will send the friend request to this guy.",
            type: "info",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes!",
            closeOnConfirm: false
        }, function () {
            $.post(contextPath + '/api/request', {friend_name: $('#search-friend').val().trim(), user_id: id}, function(data) {
                if(data==="true") swal("Sent!", "", 'success');
                else swal("Error!", "Server Error", 'error');
                console.log(data);
            });
        });
    });


    $.getJSON(contextPath+'/api/getAllUsers', {user_id: id}, function(data) {
        var allUsers = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.whitespace,
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            local: data,     
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




});