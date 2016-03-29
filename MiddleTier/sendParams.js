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
                console.log("Response from BackEnd:" + str);
                //INSERT CODE TO HANDLE RESPONSE (Will be a song??)      
            });

            response.on('error', function(err) {
                console.log(err);
            });

        });

        req.write(JSON.stringify(params));
        req.end();
    }
};