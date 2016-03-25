var express = require('express');
var config = require('config-node')();
var multer = require('multer');
var https = require('https');
var http = require('http');
var fs = require ('fs');
//Setup Image analyzer (replace with API later)
var ColorThief = require('color-thief');
var color = new ColorThief();

var imageRoutes = module.exports = express();

//Simplified color constants
var COLORS = [ "BLACK", "WHITE", "RED", "GREEN", "BLUE", "YELLOW", "ORANGE", "PURPLE", "GREY", "BROWN"];

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
     
        //Get Dominant Color
        var colorThief = color.getColor(image);
        var r = colorThief[0];
        var g = colorThief[1];
        var b = colorThief[2];
        
        //Get pallette of colors
        var colorPal = color.getPalette(image, 4);

        //Convert DomColor   
        var domColor = getColor(r, g, b);
        
        var pals = [];
        
        //Covert the top 3 color pallettes to a simplified range of colors
        for (var i = 0; i < 4; i++) 
        {
            var r = colorPal[i][0];
            var g = colorPal[i][1];
            var b = colorPal[i][2];
            //console.log("Converted:" +  getColor(r, g, b));
            var pal = getColor(r, g, b);
            pals.push(pal);
        }   
                
        //Create object to send to generator
        var generationParameters =
            {
                "numberOfFaces": totalFaces,
                "faces": faces,
                "domColor": domColor,
                "pal1": pals[0],
                "pal2": pals[1],
                "pal3": pals[2]
            }

        return sendParameters(generationParameters);
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

//http://www.calculatorcat.com/free_calculators/color_slider/rgb_hex_color_slider.phtml
//Parse the RGB Color values into a rough color estimate
var getColor = function (R, G, B) {
    //White
    //All are above 240
    if (R >= 240 && G >= 240 && B >= 240)
        return COLORS[1];
    
    //Black
    //All less than 45
    if (R <= 45 && G <= 45 && B <= 45)
        return COLORS[0];
    
    //GREY
    //If difference is < 15 GREY
    //get absolute value
    if (Math.abs(R - G) <= 15 && Math.abs(R - B) <= 15)
        return COLORS[8]
    
    //RED
    if (R >= G && R >= B) 
    {
        //Red or Brown
        if (Math.abs(G - B) <= 30) 
        {
            //BROWN
            if (R < 140)
                return COLORS[9];
            //RED
            else
                return COLORS[2]
        }
           
        //PURPLE
        if (B > G)
            return COLORS[7];
        //ORANGE OR YELLOW
        if (G > B) 
        {
            //YELLOW
            if (G > 120)
                return COLORS[5];
            else 
            {   //BROWN
                if (R < 150)
                    return COLORS[9];
                else //ORANGE
                    return COLORS[6];
            }

        }
        //Something else RED
        return COLORS[2];
    }
    
    //GREEN
    //shit is green no matter what others are???
    if (G >= R && G >= B) 
    {
        //Green
        if (Math.abs(R - B) <= 30)
            return COLORS[3];
            
        ///STILL HAVE GREEN??
        if (B > R)
            return COLORS[3];
        //Something else GREEN
        return COLORS[3];
    }
    
    //BLUE
    if (B >= R && B >= G) 
    {
        //BLUE
        if (Math.abs(G - R) <= 30)
            return COLORS[4];
        
        //PURPLE
        if (R > G)
            return COLORS[7];
            
        //Something else
        return COLORS[4];
    }
};

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
