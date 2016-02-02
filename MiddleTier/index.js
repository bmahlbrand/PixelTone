var express = require('express');

var app = express();

var userRoutes = require('./userRoutes');
var imageRoutes = require('./imageRoutes');
var testUpload = require('./testUpload');

//Testing working correctly
app.get('/', function (request, response)
{
    response.send('Hello, deadbeef');
});

//Load Route Handlers
app.use('/users' , userRoutes);
app.use('/images' , imageRoutes);
app.use('/test' , testUpload);



app.listen(3000, function () {
  console.log('Middle-Tier Listening on Port 3000');
});