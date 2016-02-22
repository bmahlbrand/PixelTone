
var express = require('express');

var testUpload = module.exports = express();

testUpload.get('/upload', function(req, res) {
    res.send('<form action="/images/process" method="post" enctype="multipart/form-data">'
        + '<p>Title: <input type="text" name="title" placeholder="title" /></p>'
        + '<p>Image: <input type="file" name="image" /></p>'
        + '<p><input type="submit" value="Upload" /></p>'
        + '</form>'
        + '<form action="/images/analyze">'
        + '<p><input type="submit" value="Submit" /></p>'
        + '</form>'
    );
});