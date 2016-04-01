var express      = require('express');
var config       = require('config-node')();
var morgan       = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser   = require('body-parser');
var session      = require('express-session');
var mongoose     = require('mongoose');
var passport     = require('passport');
var flash        = require('connect-flash');
var path         = require('path');
var util         = require('util');
var localStrategy = require('passport-local' ).Strategy;
var jsonfile = require('jsonfile');
var sp = require('./sendParams');

mongoose.connect(config.mongodb);

//require('./config/passport')(passport);

var User = require('./userModel');

var app = express();

//Setup Express
app.use(morgan('dev')); // log every request to the console
app.use(cookieParser()); // read cookies (needed for auth)
app.use(bodyParser.json()); // get information from html forms
app.use(bodyParser.urlencoded({ extended: false }));

// required for passport
app.use(session({
                secret: 'deadbeefisnumberone', // session secret
                saveUninitialized: false,
                resave: false }));
app.use(passport.initialize());
app.use(passport.session()); // persistent login sessions
app.use(flash());
app.use(express.static(path.join(__dirname, '../website')));

//Expanding the routes
var userRoutes = require('./userRoutes');
var imageRoutes = require('./imageRoutes');
var testUpload = require('./testUpload');
var songRoutes = require('./songRoutes');

// configure passport
passport.use(new localStrategy(User.authenticate()));
passport.serializeUser(User.serializeUser());
passport.deserializeUser(User.deserializeUser());


//Check current session for authentication before proceeding
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
app.use('/users' , userRoutes); //Login, Logout, Reset, Create
app.use('/images' , checkAuth, imageRoutes); //Routes to handle generation
app.use('/test' , checkAuth, testUpload); //Sample page to handle file uploads
app.use('/songs', songRoutes);

// routes
app.use('/user/', userRoutes);

app.get('/', function(req, res) {
  res.sendFile(path.join(__dirname, '../website', 'index.html'));
});

app.use('/about', function(req, res) {
  res.sendFile(path.join(__dirname, '../website', 'index.html'));
});

app.use('/login', function(req, res) {
  res.sendFile(path.join(__dirname, '../website', 'index.html'));
});

app.use('/register', function(req, res) {
  res.sendFile(path.join(__dirname, '../website', 'index.html'));
});

app.use('/profile', function(req, res) {
  res.sendFile(path.join(__dirname, '../website', 'index.html'));
});

app.use('/upload', function(req, res) {
  res.sendFile(path.join(__dirname, '../website', 'index.html'));
});




//FOR QUICK TESTING
app.get('/deadbeef', function(req, res, next) {
    res.send(
        'Load PreCalculatedJson Without Auth<br><br>'
        + '<a href=/solo>The Solos</a><br><br>'
        + '<a href=/cb>Crying Baby</a><br><br>'
        + '<a href=/leo>Leonidas</a><br><br>'
    );
});


app.get('/solo', function(req, res, next) {
    var file = 'solo.json'
    jsonfile.readFile(file, function(err, obj) {
        var ret = sp.sendParameters(obj, res, 1);
    });
});


app.get('/cb', function(req, res, next) {
    var file = 'cb.json'
    jsonfile.readFile(file, function(err, obj) {
        sp.sendParameters(obj);
        res.send(
            'Load PreCalculatedJson Without Auth<br><br>'
            + '<a href=/solo>The Solos</a><br><br>'
            + '<a href=/cb>Crying Baby</a><br><br>'
            + '<a href=/leo>Leonidas</a><br><br>'
        );
    });

});

app.get('/leo', function(req, res, next) {
    var file = 'leo.json'
    jsonfile.readFile(file, function(err, obj) {
        sp.sendParameters(obj);
        res.send(
            'Load PreCalculatedJson Without Auth<br><br>'
            + '<a href=/solo>The Solos</a><br><br>'
            + '<a href=/cb>Crying Baby</a><br><br>'
            + '<a href=/leo>Leonidas</a><br><br>'
        );
    });
});




// error hndlers
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

app.use(function(err, req, res) {
  res.status(err.status || 500);
  res.end(JSON.stringify({
    message: err.message,
    error: {}
  }));
});

app.listen(3000, function () {
    console.log('Middle-Tier Listening on Port 3000');
});
