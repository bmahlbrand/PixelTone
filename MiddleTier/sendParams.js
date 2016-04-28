var http = require('http');
var Image = require('./imageModel');
var fs = require('fs');

var aws; 
var hasAws = false;
if(fs.existsSync('./config/awsConfig.json')) {
    aws = require('./uploadSong.js');
    hasAws = true;
} else {
    console.log("No API KEY (CANT CONNECT TO AWS) for song upload");
}

module.exports = {

    //Send generatedParameters request to BackEnd
    sendParameters: function (params, res, sv) {

        var generateOptions;

        generateOptions = {
            host: 'localhost',
            path: '/generateSong',
            port: 3001,
            method: 'POST'
        };

        var midiResponse = {};

        var req = http.request(generateOptions, function (response) {
            var str = ''
            response.on('data', function (chunk) {
                str += chunk;
            });

            response.on('end', function () {
                console.log("Generation Finished on BackEnd");
                var response = JSON.parse(str);
                saveReturn(response);
            });

            response.on('error', function (err) {
                console.log(err);
            });

        });

        req.write(JSON.stringify(params));
        req.end();
    }
};



var saveReturn = function (returnData) {
    console.log(returnData);
    var key = returnData.imageKey;
    var sp = returnData.songPath;
    var faces = returnData.numberOfFaces;
    var proCo = returnData.prominantColor;
    var songkey = returnData.chosenKey;
    var temp = returnData.chosenTempo;
    var rm = returnData.relativeMinor;
    var np = returnData.notePath;
    var name = returnData.name;

    //console.log("found song at: " + sp);

    Image.findOne({ 'local.songKey': key }, function (err, img) {
        if (!img) {
            console.log("can't find key");
            return;
        }

        img.local.songPath = sp;
        img.local.numberOfFaces = faces;
        img.local.topColor = proCo;
       // img.local.topEmotion = 
        img.local.chosenKey = songkey;
        img.local.chosenTempo = temp;
        img.local.relativeMinor = rm;
        img.local.notePath = np;
        img.local.CHname = name;

        img.save(function (err) {
            if (err)
                throw err;
            else {
                //Upload song to S3
                path = "../BackEnd/songs/" + sp; //Might need to be changed for windows
                if(hasAws)
                    aws.uploadSong(path, null,  key);
                return;
            }
        });
    });

}
