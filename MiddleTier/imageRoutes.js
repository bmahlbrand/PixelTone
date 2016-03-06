var express = require('express');
var config = require('config-node')();
var multer = require('multer');
var https = require('https');
var http = require('http');
var fs = require ('fs');
//Setup Image analyzer (replace with API later)
var imagecolors = require('imagecolors');

var imageRoutes = module.exports = express();

//Simplified color constants
//var COLORS = [ "BLACK", "WHITE", "RED", "GREEN", "BLUE", "YELLOW", "ORANGE", "PURPLE", "GREY", "BROWN"];
var NEWCOLORS = [ "brown", "pink", "red", "orange", "green", "yellow", "purple", "blue", "light", "neutral", "dark"];

var key = config.MSEmotionPrimeKey;

if (key == null || key == "")
    console.log("No API KEY (CANT CONNECT TO MS) - GET FROM JACOB/BEN");

var image = "";


//Setup for saving image to disk
//Put into ./tmp folder, rename it with date on the end
var storage = multer.diskStorage({
    destination: function (req, file, callback) {
        callback(null, './tmp');
    },
    filename: function (req, file, callback) {
        callback(null, file.fieldname + '-' + Date.now() + '.jpg');
    }
});

//Can only handle one image right now
var upload = multer({ storage: storage }).single('image');

//Post options for Microsoft Emotion API
var options = {
    host: 'api.projectoxford.ai',
    path: '/emotion/v1.0/recognize',
    method: 'POST',
    headers: {
        'Content-Type': 'application/octet-stream',
        'Ocp-Apim-Subscription-Key': key
    }
};

//Handle response from Microsoft
var parseMSResponse = function (response) {
    var str = '';

    response.on('data', function (chunk) {
        str += chunk;
        //console.log('data');
    });

    response.on('response', function (response) {
        console.log(response.statusCode);
    });

    response.on('end', function () {

        var parsed = JSON.parse(str);
        //console.log(parsed);
       
        var totalFaces = 0;
        var faces = [];
        
        //console.log("Top 3 Facial Traits");
        //Loop through all the faces returned from microsoft
        //Sort and parse results into object to send later
        parsed.forEach(function (face) {
            totalFaces++;
            var tmpEmo = [];

            //Loop through all the emotion scores for each face
            for (var x in face.scores) {
                var key = "emotion";
                var key2 = "value";
                var entry = {};
                entry[key] = x;
                entry[key2] = face.scores[x];
                tmpEmo.push(entry);
            }

            tmpEmo.sortOn("value");

            var emotionArray = [];
                    
            //Get top 3 highest emotion values, and store in object
            for (var i = 7; i > 4; i--) {
                var faceEntry = {};

                faceEntry["emotion"] = tmpEmo[i].emotion;
                faceEntry["value"] = tmpEmo[i].value;
                emotionArray.push(faceEntry);
            }
            var emotions = { 'emotions': emotionArray };
            faces.push(emotions);
        });
     
     
        //can be path or uRL! double bonus
        getColors(image, 5, function (colors) {

            var colorArray = [];
            console.log(colors);
            var numberOfColors = colors.length >= 5 ? 5 : colors.length;
            
            //TWEAK THESE VALUES
            //If dom color has <= 20, and next is within 5 then image is neutral
            if (numberOfColors > 1 && colors[0].percent <= 15 && (colors[0].percent - colors[1].percent <= 5)) {
                console.log("No dominant color");
                var colorObj = {};
                var key = "Percent";
                var key2 = "Color";
                colorObj[key] = colors[0].percent;
                colorObj[key2] = "neutral";
                colorArray.push(colorObj);
            }
            else {
                for (var i = 0; i < numberOfColors; i++) {
                    //If color percent < 10 we don't care
                    if (colors[i].percent > 20) {
                        var colorObj = {};
                        var key = "Percent";
                        var key2 = "Color";
                        colorObj[key] = colors[i].percent;
                        colorObj[key2] = colors[i].family;
                        colorArray.push(colorObj);
                    }
                }
            }


            var generationParameters =
                {
                    "numberOfFaces": totalFaces,
                    "faces": faces,
                    "colorEntries": colorArray
                }

            return sendParameters(generationParameters);
        });
    });


    response.on('error', function (err) {
        console.log("MS ERROR" + err);
    });
}


//Save uploaded image to ./tmp/
imageRoutes.post('/process', function (req, res) {
    upload(req, res, function (err) {
        if (err) {
            console.error(err);
        }
        else if (req.file) {
            console.log(req.body);
            console.log(req.file);
            image = ".\\tmp\\" + req.file.filename;
        }
    });
    res.status(204).end();
});

//Send uploaded image to microsoft
imageRoutes.get('/analyze', function (request, response) {

        var req = https.request(options, parseMSResponse);

        var stream = fs.createReadStream(image);

        stream.on('open', function () {
            stream.pipe(req);
        });

        stream.on('close', function () {
            console.log('Done Sending Image...');
            req.end();
        });

        stream.on('error', function (err) {
            console.log(err);
        });

        response.status(204).end();
});


//Simple array sort
Array.prototype.sortOn = function (key) {
    this.sort(function (a, b) {
        if (a[key] < b[key]) {
            return -1;
        } else if (a[key] > b[key]) {
            return 1;
        }
        return 0;
    });
}


//Send generatedParameters request to BackEnd
var sendParameters = function (params) {
    var generateOptions = {
        host: 'localhost',
        path: '/generateSong',
        port: 3001,
        method: 'POST'
    };

    var req = http.request(generateOptions, function (response) {
        var str = ''
        response.on('data', function (chunk) {
            str += chunk;
        });

        response.on('end', function () {
            console.log("Response from BackEnd:" + str);
            //INSERT CODE TO HANDLE RESPONSE (Will be a song??)      
        });

        response.on('error', function (err) {
            console.log(err);
        });

    });

    req.write(JSON.stringify(params));
    req.end();
};

function getColors(imagePath, numOfColors, callback) {
    imagecolors.extract(imagePath, numOfColors, function(err, colors){
    if (!err){
        return callback(colors);
    }
    console.log("ERRROR GETTING COLORS");
    console.log(err);
    });
};



/*SAVE DATA
var jsonfile = require('jsonfile')
 
var file = '/tmp/data.json'
var obj = {name: 'JP'}
 
jsonfile.writeFile(file, obj, function (err) {
  console.error(err)
})

// READ FILE IN
var jsonfile = require('jsonfile')
var util = require('util')
 
var file = '/tmp/data.json'
jsonfile.readFile(file, function(err, obj) {
  console.dir(obj)
})
*/