var http = require('http');

module.exports = {

    //Send generatedParameters request to BackEnd
    sendParameters: function(params) {
        var generateOptions = {
            host: 'localhost',
            path: '/generateSong',
            port: 3001,
            method: 'POST'
        };


        var req = http.request(generateOptions, function(response) {
            var str = ''
            response.on('data', function(chunk) {
                str += chunk;
            });

            response.on('end', function() {
                console.log("Response from BackEnd:");
                //INSERT CODE TO HANDLE RESPONSE (Will be a song??)
                var response = JSON.parse(str);
                return saveReturn(response);

            });

            response.on('error', function(err) {
                console.log(err);
            });

        });

        req.write(JSON.stringify(params));
        req.end();
    }
};



var saveReturn = function(returnData)
{
    console.log(returnData);


     /*User.findOne({ 'local.resetPasswordToken': req.body.token, 'local.resetPasswordExpires': { $gt: Date.now() } }, function (err, user) {
        if (!user) {
            req.flash('error', 'Password reset token is invalid or has expired.');
            return res.redirect('back');
        }

        //Set new password
        user.local.password = user.generateHash(req.body.resetpass);
        //Unset info
        user.local.resetPasswordToken = undefined;
        user.local.resetPasswordExpires = undefined;

        user.save(function (err) {
            if (err)
                throw err;
            else {
                console.log('RESET PASSWORD');
                req.logIn(user, function (err) {
                    if (err) { throw err; }
                    return res.redirect('/test/upload');
                });
            }
        });
    });*/

    return;

}
