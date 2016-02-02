var express = require('express');
var config = require('config-node')();
var multer = require('multer');
var https = require('https');
var fs = require ('fs');

var imageRoutes = express();

var key = config.MSEmotionPrimeKey;

var image = "";

var storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, './tmp')
  },
  filename: function (req, file, cb) {
    cb(null, file.fieldname + '-' + Date.now() + '.png')
  }
});

var upload = multer({ storage: storage }).single('image');

var options = {
  host: 'api.projectoxford.ai',
  path: '/emotion/v1.0/recognize',
  method: 'POST',
  headers: {
     'Content-Type': 'application/octet-stream',
    'Ocp-Apim-Subscription-Key': key
  }
};

//Handle response from microsoft
var callback = function(response) {
  var str = '';

  response.on('data', function (chunk) {
    str += chunk;
    console.log('data');
  });
  
 response.on('response', function(response) {
    console.log(response.statusCode);
  });

  response.on('end', function () {
    var parsed = JSON.parse(str);
    console.log(parsed);
  });
  
    response.on('error', function (err) {
    console.log(err);
  });
}


//User Account Handling
imageRoutes.post('/process',  function (req, res)
{   
     upload(req, res, function(err) {
      if (err) {
                console.error(err);
         } 
      else if(req.file) {
                 console.log(req.body);
                 console.log(req.file);
                 image = ".\\tmp\\" + req.file.filename;
         }
    });

	res.status(204).end(); 
});

//Send uploaded image to microsoft
imageRoutes.get('/analyze', function (request, response)
{
    //console.log(image);
    var req = https.request(options, callback);
    
    var stream =  fs.createReadStream(image);
    
    stream.on('open', function () {
        stream.pipe(req);
    });
    
    stream.on('close', function () {
      console.log('done writing');
      req.end();      
    });
    
    stream.on('error', function(err) 
    {
        console.log(err);
    });
    
    response.status(204).end();
});

module.exports = imageRoutes;
