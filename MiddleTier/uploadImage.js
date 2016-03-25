var AWS = require('aws-sdk');
var fs = require('fs');
var keygen = require('random-key');

AWS.config.region = 'us-east-1';

var uploadImage = function(file) {
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
		s3bucket.upload(params).on('httpUploadProgress', function(evt) {
				//console.log(evt);
				console.log(Math.round((evt.loaded / evt.total) * 100) + '% uploaded');
				if(evt.loaded == evt.total) {
					console.log('Uploaded file to https://s3.amazonaws.com/pixeltone/' + myKey);
				}
			}).send(); 
	});
};

uploadImage('./testImg2.jpg');
//s3bucket.getObject({Key: myKey}, function(err, data) {
//	if (err) 
//		console.log(err, err.stack); // an error occurred
//	else     
//		console.log(data);           // successful response
//});


