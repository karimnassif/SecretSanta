$(document).ready(function (){

    //Load table on load
    $.ajax({
        url: "santa/person/all"
    }).then(function(data) {
        $('#table').bootstrapTable({
            data: data
        })
    });

    //Setting button functions
    $("#newEntry").click(function(){
        toggleForm();
    });
    $("#signIn").click(function() {
        toggleSignin();
    })
    $("#modalClose").click(function(){
        $('#confirmEntryModal').modal('hide')
    })
    $("#submit").click(function(){
        $('#modalSave').show();
        var $name = $('#newName').val();
        var $address = $('#newAddress').val();
        var $email = $('#newEmail').val();
        $('#confirmEntryModal').modal('show');
        if(!$name.trim() || !$address.trim() || !$email.trim()){
            $('#modalText').text("You left a field blank (dumbass)");
            $('#modalSave').hide();
            return;
        }
        $('#modalText').text("Confirm that name is: " + $name + ", address is: " + $address
                            + " and email is: " + $email);
    });
    $("#cancel").click(function(){
        toggleForm();
    });
    $("#submitLogin").click(function(){
        var $username = $('#login').val();
        if($username){
            findPair($username);
        }
    });
    $("#cancelLogin").click(function(){
        toggleSignin();
    });
    $("#modalSave").click(function(){
        submitEntry();
    });

    //Utility functions
    function submitEntry(){
        var $name = $('#newName').val();
        var $address = $('#newAddress').val();
        var $email = $('#newEmail').val();
        var $userName = Math.random().toString(36).substring(7);
        var postData = {name: $name, address: $address, email: $email, username: $userName};
        console.log(postData);
        $.ajax({
            url : "/santa/person",
            type: "POST",
            data: JSON.stringify(postData),
            contentType: "application/json",
            dataType   : "json",
            success    : function(){
                console.log("Post success");
                $('#modalSave').hide();
                $('.modal-title').text("Good Job Pal.");
                $('#modalText').text("Success! To find out your pair after the draw come back and sign in with username: "
                    + $userName + "! Since y'all can't be trusted I saved it to a file in your computer, check /Downloads. :)");
                saveUsername($userName);
            }
        });
    }

    function findPair($username) {
        var $user;
        $.ajax({
            url: "santa/person/username/" + $username,
            type: "GET",
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                $user = data.name;
            }
        });
        $.ajax({
            url: "/santa/pair/receiver/" + $username,
            type: "GET",
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                console.log(data);
                $('#displayPair').html("<br/>Hello " + $user +"!<br/>Your pair is: " + data.name + "!\n" + "<br/>"
                                        + "So send them something festive to: " + data.address
                                        + "!<br/>And if you need their email, its: " + data.email +".");
            }
        });
    }

    function toggleForm(){
        $("#entry-form").toggle();
        $("#initial").toggle();
    }

    function toggleSignin(){
        $("#usernameLogin").toggle();
        $("#initial").toggle();
    }

    function saveUsername(username){
        var element = document.createElement('a');
        element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(username));
        element.setAttribute('download', "thatSweetSweetUsernameBaby");

        element.style.display = 'none';
        document.body.appendChild(element);

        element.click();

        document.body.removeChild(element);
    }

    // Start file download.
    document.getElementById("dwn-btn").addEventListener("click", function(){
        // Generate download of file with some content
        var text = document.username;
        var filename = "thatSweetSweetUsernameBaby.txt";

        download(filename, text);
    }, false);
});