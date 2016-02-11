// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-2.1.3.js
//= require_tree .
//= require_self

if (typeof jQuery !== 'undefined') {
    (function($) {
        $('#spinner').ajaxStart(function() {
            $(this).fadeIn();
        }).ajaxStop(function() {
            $(this).fadeOut();
        });
    })(jQuery);
}

$(document).ready(function() {

    /*Animation when loading page on answers with # in URL*/
    if (typeof $(window.location.hash).offset() != 'undefined') {
        $("html, body").animate({
            scrollTop: $(window.location.hash).offset().top
        }, 1000);
    }

    $(".error").delay(2000).fadeOut("50");
    $(".success").delay(2000).fadeOut("50");

    $("#create-account").click(function() {
        $("#popup-create-account").css({
            visibility: 'visible',
            opacity: '1'
        });
    });

    $("#close-popup").click(function(event) {
        $("#popup-create-account").css({
            visibility: 'hidden',
            opacity: '0'
        });
    });

    $("#edit-profile").click(function(event) {
        $("#profile-form").css({
            display: 'block',
            visibility: 'visible',
            opacity: '1'
        });
    });


    $("#change-avatar").click(function(event) {
        $("#upload-avatar").css({
            display: 'block',
            visibility: 'visible',
            opacity: '1'
        });
    });

    /*Ajax call to change profile description*/
    $("#profile-form").submit(function() {
        $("#profile-form").css({
            opacity: '0',
            visibility: 'hidden',
            display: 'none'
        });
        var profileDescription = $("#textarea-profile").val();
        $.ajax({
            url: '/profile/updateProfileDescription',
            data: {
                profileDescription: profileDescription
            },
            success: function(data) {
                $("#profile").html(data);
            }
        });
        return false; // Page doesn't refresh after click on submit button
    });

    /*Ajax call to change profile avatar*/
    $("#avatar-form").submit(function() {
        $("#upload-avatar").css({
            opacity: '0',
            visibility: 'hidden',
            display: 'none'
        });
        var avatarUrl = $("#avatar-url").val();
        $.ajax({
            url: '/profile/updateUserAvatar',
            data: {
                avatarUrl: avatarUrl
            },
            success: function(data) {
                $('#user-avatar').css("background-image", "url(" + data + ")");
            }
        });
        return false;
    });

    var timer;

    /*Ajax call to change profile avatar*/
    $("#searched-username").keyup(function() {
        $("#is-typing-gif").css('display', 'inline-block');
        clearTimeout(timer); //clear any running timeout on key up
        /*Will start ajax call 500ms after user is done with keyup*/
        timer = setTimeout(function() {
            $("#is-typing-gif").css('display', 'none');
            var username = $("#searched-username").val();
            $.ajax({
                url: '/user/searchUser',
                data: {
                    username: username
                },
                success: function(data) {
                    console.log(data);
                    $("#listUserTemplate").html(data);
                }
            });
        }, 500);
        return false;
    });

    $("#search-questionbytags").keyup(function() {
        $("#is-typing-gif").css('display', 'inline-block');
        clearTimeout(timer); //clear any running timeout on key up
        /*Will start ajax call 500ms after user is done with keyup*/
        timer = setTimeout(function() {
            $("#is-typing-gif").css('display', 'none');
            var tags = $("#search-questionbytags").val();
            $.ajax({
                url: '/question/searchQuestionsByTags',
                data: {
                    tags : tags
                },
                success: function(data) {
                    console.log(data);
                    $("#question-list-template").html(data);
                }
            });
        }, 500);
        return false;
    })


    /*Ajax call when upvote arrow clicked*/
    $(".upvote").click(function(event) {
        postId = $(this).data("postId")
        postType = $(this).data("postType")
        counterDiv = $(this).next();
        $.ajax({
            url: '/post/upvote',
            data: {
                idOfPost: postId
            },
            success: function(data) {
                counterDiv.html(data)
            }
        });
    });

    $(".downvote").click(function(event) {
        downvoteId = $(this).data("postId")
        postType = $(this).data("postType")
        counterDiv = $(this).prev();
        $.ajax({
            url: '/post/downvote',
            data: {
                idOfPost: downvoteId,
            },
            success: function(data) {
                counterDiv.html(data)
            }
        });
    });


});
