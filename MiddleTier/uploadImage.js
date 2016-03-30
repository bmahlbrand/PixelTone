var AWS = require('aws-sdk');
var fs = require('fs');
var keygen = require('random-key');
var Image = require('./imageModel');
var mongoose = require('mongoose');

var exports = module.exports = {};

//configure aws
AWS.config.loadFromPath('./config/awsConfig.json');
//connect to mongodb
//mongoose.connect("mongodb://127.0.0.1:27017/PixelTone");

exports.uploadImage = function(file) {
	var s3bucket = new AWS.S3({params: {Bucket: 'pixeltone'}});
	var body = fs.createReadStream(file);
	var myKey = keygen.generateBase30(20) + '.jpg';
	
	s3bucket.createBucket(function() {
		var params = {	
			Key: myKey,
			Body: body,
			ContentType: 'image/jpeg',
			ACL: 'public-read'
		};
		console.log('Uploading image to S3...');
		s3bucket.upload(params).on('httpUploadProgress', function(evt) {
				//console.log(evt);
				console.log(Math.round((evt.loaded / evt.total) * 100) + '% uploaded');
				//100% uploaded
				if(evt.loaded == evt.total) {
					
					//console.log("Creating new image in database");

					var newImage = new Image();
					newImage.local.key = myKey;
					newImage.local.user = null;
					newImage.local.uploadDate = new Date();
					newImage.local.songKey = null;

					//console.log(newImage);

					newImage.save(function(err) {
						if (err) {
							console.log("Error occurred");
                            throw err;
                        }
                        else {
                            console.log("Image created:" + myKey);
                            //return;
                        }
					});

					console.log('Uploaded file to https://s3.amazonaws.com/pixeltone/' + myKey);
				}
			}).send(); 
	});
};

//uploadImage('./testImg2.jpg');
//s3bucket.getObject({Key: myKey}, function(err, data) {
//	if (err) 
//		console.log(err, err.stack); // an error occurred
//	else     
//		console.log(data);           // successful response
//});


