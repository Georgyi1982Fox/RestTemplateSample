let map = new Map();
$users = $('#users');
$admin = $('#adminrole');
$userInfo = $('#userInfo');
$(document).ready(function() {
    showUsers();
});
$('#userlist').on('click', function (event){
    showUsers();
});
function showUsers() {
    $.ajax({
        type: 'GET',
        url: '/admin/allUsers',
        success: function (users) {
            $.each(users, function (i, user) {

                map.set(user.id, user);
                let a = new String();
                for (let b of user.roles) {
                    a += b.name + " ";
                }
                $users.append('<tr></tr>')
                $users.append('<td>' + user.id + '</td>')
                $users.append('<td>' + user.userName + '</td>')
                $users.append('<td>' + user.lastName + '</td>')
                $users.append('<td>' + user.age + '</td>')
                $users.append('<td>' + user.email + '</td>')
                $users.append('<td>' + a + '</td>')

                $users.append('<td><button id="updateButton"  data-toggle="modal" data-target="#editModal" class="btn btn-primary"' +
                    'data-id="' + user.id + '" >Edit</button></td>')
                $users.append('<td><button id="deleteButton"   class="remove"   class="btn btn-info" style="background: #dc143c"' +
                    'data-id="' + user.id + '">Delete</button></td>')
                $users.append('<tr></tr>')

                //$admin.append('<th>' + 'name:' + ' '  + user.userName + ' ' + 'email:' + ' ' + user.email + ' ' + 'with roles:' + ' ' + a + '</th>')
            });
        }
    });
}

$(document).ready(function () {
    $("#editModal").on('show.bs.modal', function (event) {
        let updateId = $(event.relatedTarget).data('id');

        let updateUserName = map.get(updateId).userName;
        let updateLastName = map.get(updateId).lastName;
        let updateAge = map.get(updateId).age;
        let updateEmail = map.get(updateId).email;
        let updatePassword = map.get(updateId).password;

        $(".modal-body #editid").val(updateId);
        $(".modal-body #edituserName").val(updateUserName);
        $(".modal-body #editlastName").val(updateLastName);
        $(".modal-body #editage").val(updateAge);
        $(".modal-body #editemail").val(updateEmail);
        $(".modal-body #editpassword").val(updatePassword);

        $("#Editsubmit").on('click', function (event) {

            let roles = {id: 1, name: $('#editrole').val()[0]};

            let user = {
                'id': $("#editid").val(),
                'userName': $('#edituserName').val(),
                'lastName': $('#editlastName').val(),
                'age': $('#editage').val(),
                'email': $('#editemail').val(),
                'password': $('#editpassword').val(),
                'roles': [roles],
            };
            $.ajax({
                method: 'PUT',
                url: `/admin/update/`,
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(user),
                success: function (data) {
                    $('#editModal').modal('hide');
                    $users.empty()
                    showUsers();
                },
                error: console.log('error')
            });


        });
    });
});




$(document).ready(function () {

    $users.delegate('.remove', 'click', function (event) {

        $.ajax({
            method: 'DELETE',
            url: `/admin/delete/` + $(this).attr('data-id'),
            success: function (data) {
                $('#editModal').modal('hide');
                map.clear();
                $users.empty()
                showUsers(data)
            }
        })
    })
})

$(document).ready(function () {
    $(function () {
        $('#Addsubmit').on('click', function (event) {


           let roles = {
                id: 1, name: $('#addrole').val()[0],
            }



            var user = {
                'userName': $('#userNameAdd').val(),
                'lastName': $('#lastNameAdd').val(),
                'age': $('#ageAdd').val(),
                'email': $('#emailAdd').val(),
                'password': $('#passwordAdd').val(),
                'roles': [roles],
            };

            $.ajax({
                type: 'POST',
                url: '/admin/addUser',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(user),
                success: function (data) {
                    $users.empty()
                    showUsers()
                    $('#home-tab').tab('show')

                },
                error: function () {
                    alert('error adding user')
                }

            });
        });
    });

    $(document).ready(function () {
        $.get(
            '/admin/get/userRole',
            {},
            function (data) {
                let a = new String();
                for (let b of data.roles) {
                    a += b.name + " ";
                }
                $('#userroles').append('<th>my roles is ' + a + '</th>')
            }
        );
    })



    $(document).ready(function() {
        $('#userPage').on('click', function (event) {
            $.ajax({
                type: 'GET',
                url: '/user/about',

                success: function (data) {
                    $users.empty()

                    let role = String();
                    for (let b of data.roles) {
                        role += b.name + " ";
                    }
                    $('#users').append('<tr></tr>')
                    $('#users').append('<td>' + data.id + '</td>')
                    $('#users').append('<td>' + data.userName + '</td>')
                    $('#users').append('<td>' + data.lastName + '</td>')
                    $('#users').append('<td>' + data.age + '</td>')
                    $('#users').append('<td>' + data.email + '</td>')
                    $('#users').append('<td>' + role + '</td>')

                    let user;
                    $users.append('<td><button id="updateButton"  data-toggle="modal" data-target="#editModal" class="btn btn-primary"' +
                        'data-id="' + data.id + '" >Edit</button></td>')
                    $users.append('<td><button id="deleteButton"   class="remove"   class="btn btn-info" style="background: #dc143c"' +
                        'data-id="' + data.id + '">Delete</button></td>')


                    $('#users').append('<tr></tr>')
                }

            })
        });
    });




});





