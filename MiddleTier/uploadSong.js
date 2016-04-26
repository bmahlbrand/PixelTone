var AWS = require('aws-sdk');
var fs = require('fs');
var keygen = require('random-key');
var Song = require('./songModel');
var mongoose = require('mongoose');

var exports = module.exports = {};

//configure aws
AWS.config.loadFromPath('./config/awsConfig.json');
//connect to mongodb
//mongoose.connect("mongodb://127.0.0.1:27017/PixelTone");

exports.uploadSong = function(file, user, songKey) {
	var s3bucket = new AWS.S3({params: {Bucket: 'pixeltone-midi'}});
	var body = fs.createReadStream(file);
	var filename = keygen.generateBase30(20) + '.mid';

	s3bucket.createBucket(function() {
		var params = {
			Key: filename,
			Body: body,
			//ContentType: 'image/jpeg',
			ACL: 'public-read'
		};
		console.log('Uploading midi to S3...');
		s3bucket.upload(params).on('httpUploadProgress', function(evt) {
				//console.log(evt);
				console.log(Math.round((evt.loaded / evt.total) * 100) + '% uploaded');
				//100% uploaded
				if(evt.loaded == evt.total) {

					//console.log("Creating new image in database");

					var newSong = new Song();
					newSong.local.key = songKey;
					newSong.local.url = 'https://s3.amazonaws.com/pixeltone-midi/' + filename;

					//console.log(newImage);

					newSong.save(function(err) {
						if (err) {
							//console.log("Error occurred");
                            throw err;
                        }
                        else {
                            console.log("Song Saved");
                            //return;
                        }
					});

					console.log('Uploaded file to https://s3.amazonaws.com/pixeltone-midi/' + filename);
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
