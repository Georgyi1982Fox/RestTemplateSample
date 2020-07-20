$user = $('#userInfo');
$info = $('#uroles')
$(document).ready(function () {
    $.ajax({
        method: 'GET',
        url: '/user/about',
        success: function (user) {
            let a = String();
            for (let b of user.roles) {
                a += b.name + " ";
            }
            $user.append('<tr></tr>')
            $user.append('<td>' + user.id + '</td>')
            $user.append('<td>' + user.userName + '</td>')
            $user.append('<td>' + user.lastName + '</td>')
            $user.append('<td>' + user.age + '</td>')
            $user.append('<td>' + user.email + '</td>')
            $user.append('<td>' + a + '</td>')
            $user.append('<tr></tr>')

            $info.append('<th>my roles is ' + a + '</th>')

        }
    });






});





