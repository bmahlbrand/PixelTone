var express      = require('express');
var config       = require('config-node')();
var morgan       = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser   = require('body-parser');
var session      = require('express-session');
var mongoose     = require('mongoose');
var passport     = require('passport');
var flash        = require('connect-flash');

mongoose.connect(config.mongodb);

require('./config/passport')(passport);

var app = express();

//Setup Express
app.use(morgan('dev')); // log every request to the console
app.use(cookieParser()); // read cookies (needed for auth)
app.use(bodyParser.json()); // get information from html forms
app.use(bodyParser.urlencoded({ extended: true }));
    
// required for passport
app.use(session({ 
                secret: 'deadbeefisnumberone', // session secret
                saveUninitialized: true,
                resave: true })); 
app.use(passport.initialize());
app.use(passport.session()); // persistent login sessions
app.use(flash());
    
var userRoutes = require('./userRoutes');
var imageRoutes = require('./imageRoutes');
var testUpload = require('./testUpload');

//Testing working correctly
app.get('/', function (request, response) {
    response.send('Hello, deadbeef<br>'
    + '<a href=/login>Login</a><br>'
    + '<a href=/signup>signup</a><br>'
     + '<a href=/users/forgot>forgot</a><br>'
     + '<a href=/users/logout>logout</a>'
    );
});

//Auth before proceeding
var checkAuth = function(req, res, next) { 
    if (!req.isAuthenticated())  
    {
        console.log('Not Authenticated');
        res.sendStatus(401); 
    }
    else 
    {
        console.log('Authenticated');
        next(); 
    }
};


//Load Route Handlers
app.use('/users' , userRoutes);
app.use('/images' , checkAuth, imageRoutes);
app.use('/test' , checkAuth, testUpload);

app.get('/login', function(req, res) {
    res.send('<form action="/login" method="post">'
        + '<p>Email: <input type="text" name="email" placeholder="title" /></p>'
        + '<p>Password: <input type="password" name="password" /></p>'
        + '<p><input type="submit" value="login" /></p>'
        + '</form>'
    );
});


//Login Route
app.post('/login', function(req, res, next) {
        passport.authenticate('local-login', function (err, user) {
           
           //Sucessfully logged in user
           if(user)
           {
                req.logIn(user, function(err) 
                {
                    if (err) { return next(err); }
                    console.log("Logged In User:" + user.local.email);
                    return res.redirect('/test/upload');
                });
           }
           else
           {
               switch(err.ID)
               {
                   case 2:          
                            console.log(err.message);
                            res.set({'error': 2});
                            res.redirect('/signup');
                           // res.sendStatus(400);
                            break;
                   case 3:
                            console.log(err.message);
                            res.set({'error': 3});
                            res.redirect('/signup');
                            //res.sendStatus(400);
                            break;
                  default:
                            res.redirect('/signup');
                            //res.sendStatus(400);
                            break;         
               }
           }     
  })(req, res, next);
});

app.get('/signup', function(req, res) {   
    res.send(
          '<form action="/signup" method="post">'
        + '<p>Email: <input type="text" name="email" placeholder="email" /></p>'
        + '<p>Password: <input type="password" name="password" /></p>'
        + '<p><input type="submit" value="signup" /></p>'
        + '</form>'
    );
});

app.post('/signup', function(req, res, next) {
        passport.authenticate('local-signup', function (err, user) {
         
           //Sucessfully created user
           if(user)
           {
               console.log("Created User:" + user);
               res.redirect('/login')
               //res.sendStatus(200); 
           }
           else
           {
               console.log(err.message);
               res.set({'error': 1});
               res.redirect('/signup')
               //res.sendStatus(400);
           }
           
  })(req, res, next);
});

app.listen(3000, function () {
    console.log('Middle-Tier Listening on Port 3000');
});