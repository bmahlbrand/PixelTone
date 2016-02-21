var express = require('express');
var config = require('config-node')();
var multer = require('multer');
var https = require('https');
var fs = require ('fs');
var ColorThief = require('color-thief');
var color = new ColorThief();

var imageRoutes = module.exports = express();

var key = config.MSEmotionPrimeKey;
if(key == null)
    throw err("No API KEY");

var image = "";

var storage = multer.diskStorage({
    destination: function (req, file, callback) {
        callback(null, './tmp');
    },
    filename: function (req, file, callback) {
        callback(null, file.fieldname + '-' + Date.now() + '.jpg');
    }
});

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
var parseMSResponse = function(response) {
    var str = '';

    response.on('data', function (chunk) {
        str += chunk;
        //console.log('data');
    });
  
    response.on('response', function(response) {
        console.log(response.statusCode);
    });

    response.on('end', function() {
        
        var parsed = JSON.parse(str);
        console.log(parsed);
        
        // image = image.substr(1);
        console.log("Grabbing colors from image:" + image);
        //Get Dominant Color
        var colorThief = color.getColor(image);
        console.log("Dominant Color:"+ colorThief);
        
        var r = colorThief[0];
        var g = colorThief[1];
        var b = colorThief[2];
        
        console.log("Converted:#"+  r.toString(16) + g.toString(16) + b.toString(16));
        
        //Get pallette of colors
        var colorPal = color.getPalette(image , 4);
        console.log("Top 4 Colors:" + colorPal);
        
        for(var i = 0; i < 4; i++)
        {
            var r = colorPal[i][0];
            var g = colorPal[i][1];
            var b = colorPal[i][2];
            console.log("Converted:#"+  r.toString(16) + g.toString(16) + b.toString(16));
            
        }
        
        var c = 0;
       
        console.log("Top 3 Facial Traits");
        parsed.forEach( function(face) {
            var scores = face.scores;
             var emotions = [];
            console.log("Face " + c++ + ":");
            for( var x in scores)
            {
                    var key = "emotion";
                    var key2 = "value";
                    var entry = {};
                    entry[key] = x;
                    entry[key2] = scores[x];
                    emotions.push(entry);
            }
  
            emotions.sortOn("value");
            console.log(emotions[7]);
            console.log(emotions[6]);
            console.log(emotions[5]);
        });
    });
    
  
    response.on('error', function (err) {
        console.log("MS ERROR" + err);
    });
}


//Save uploaded image to ./tmp/
imageRoutes.post('/process',  function (req, res) {    
    upload(req, res, function(err) {
        if (err) 
        {
            console.error(err);
        } 
        else if(req.file) 
        {
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
    
    var stream =  fs.createReadStream(image);
    
    stream.on('open', function () {
        stream.pipe(req);
    });
    
    stream.on('close', function () {
        console.log('Done Sending Image...');
        req.end();      
    });
    
    stream.on('error', function(err) {
        console.log(err);
    });
    
    response.status(204).end();
});


//Process Image Parameters
var imageProcessor = function(MSdata)
{

    
}


Array.prototype.sortOn = function(key){
    this.sort(function(a, b){
        if(a[key] < b[key]){
            return -1;
        }else if(a[key] > b[key]){
            return 1;
        }
        return 0;
    });
}