var http = require('http');

module.exports = {
  getSong :function(songfile, res) {
    var route = {
        host: 'localhost',
        path: '/songs/'+songfile,
        port: 3001,
        method: 'GET'
    };

    var req = http.request(route, function(response) {
        var str = ''
        response.on('data', function(chunk) {
            str += chunk;
        });

        response.on('end', function() {
            console.log("Response from BackEnd:");
            //INSERT CODE TO HANDLE RESPONSE (Will be a song??)
            console.log(response);
            return "sdf";
        });

        response.on('error', function(err) {
            console.log(err);
        });

    });
    req.write(songfile);
    req.end();
}
};
