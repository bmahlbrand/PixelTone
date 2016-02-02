var express = require('express');

var userRoutes = express();

//User Account Handling
userRoutes.post('/login', function (request, response)
{
    response.send('Login');
    //Hook into passport??
     
});

userRoutes.post('/create', function (request, response)
{
    response.send('Hello World');
     //Hook into passport??
});


userRoutes.post('/reset', function (request, response)
{
    response.send('Hello World');
     //Hook into passport??
});


userRoutes.post('/logout', function (request, response)
{
    response.send('Hello World');
     //Hook into passport??
});

module.exports = userRoutes;
