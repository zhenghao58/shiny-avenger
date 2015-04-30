
$(document).ready(function () {/* off-canvas sidebar toggle */
    var path=window.location.pathname;
    var contextPath = path.slice(0, path.indexOf('/', 1));
    $('[data-toggle=offcanvas]').click(function () {
        $(this).toggleClass('visible-xs text-center');
        $(this).find('i').toggleClass('glyphicon-chevron-right glyphicon-chevron-left');
        $('.row-offcanvas').toggleClass('active');
        $('#lg-menu').toggleClass('hidden-xs').toggleClass('visible-xs');
        $('#xs-menu').toggleClass('visible-xs').toggleClass('hidden-xs');
        $('#btnShow').toggle();
    });

    var friendlist;
    $("button#status-submit").click(function () {
        $.ajax({
            type: "POST",
            url: contextPath + '/api/request',
            data: $('form#status').serialize(),
            success: function (msg) {
                console.log(msg);
                console.log(msg.length);
            },
            error: function () {
                alert("failure");
            }
        });
    });
    
    var states = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        prefetch: contextPath + '/api/getAllUsers'
    });

    $('.typeahead').typeahead({
        hint: true,
        highlight: true,
        minLength: 1
    },
    {
        name: 'states',
        source: states
    });



});