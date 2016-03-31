var express      = require('express');
var config       = require('config-node')();
var morgan       = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser   = require('body-parser');
var session      = require('express-session');
var mongoose     = require('mongoose');
var passport     = require('passport');
var flash        = require('connect-flash');
var util         = require('util');
var jsonfile = require('jsonfile');
var sp = require('./sendParams');

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

//Expanding the routes
var userRoutes = require('./userRoutes');
var imageRoutes = require('./imageRoutes');
var testUpload = require('./testUpload');
var songRoutes = require('./songRoutes');

//Serve up FrontEnd Requests
var dirname = config.staticContent;
app.use("/",express.static(dirname));

//Simple Home Page to test without Angular
app.get('/test', function (request, response) {
    response.send('Hello, welcome to PixelTone<br><br><br>'
    + '<a href=/login>Login</a><br><br>'
    + '<a href=/signup>signup</a><br><br>'
    + '<a href=/users/forgot>forgot</a><br><br>'
    + '<a href=/users/logout>logout</a>'
    );
});

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

//Simple page for Login testing
app.get('/login', function(req, res) {
    res.send('<form action="/login" method="post">'
        + '<p>Email: <input type="text" name="email" placeholder="Enter Email" /></p>'
        + '<p>Password: <input type="password" name="password" /></p>'
        + '<p><input type="submit" value="login" /></p>'
        + '</form>'
    );
});

//Handle Logins with Username/Password
app.post('/login', function(req, res, next) {
    passport.authenticate('local-login', function (err, user) {

        //Sucessfully logged in user
        if (user) {
            req.logIn(user, function (err) {
                if (err) { return next(err); }
                console.log("Logged In User:" + user.local.email);
                return res.redirect('/test/upload');
            });
        }
        else {
            if(err.ID == null){
                 console.log("SOMETHING REALLY BAD");
                 res.redirect('/login');
            }

            switch (err.ID) {
                case 2:
                    console.log(err.message);
                    res.set({ 'error': 2 });
                    res.redirect('/signup');
                    break;
                case 3:
                    console.log(err.message);
                    res.set({ 'error': 3 });
                    res.redirect('/signup');
                    break;
                default:
                    res.redirect('/signup');
                    break;
            }
        }
  })(req, res, next);
});

//Simple Page for Signup Testing
app.get('/signup', function(req, res) {
    res.send(
          '<form action="/signup" method="post">'
        + '<p>Email: <input type="text" name="email" placeholder="Create Email" /></p>'
        + '<p>Password: <input type="password" name="password" /></p>'
        + '<p><input type="submit" value="signup" /></p>'
        + '</form>'
    );
});

//Handle signup data requests
app.post('/signup', function (req, res, next) {
    passport.authenticate('local-signup', function (err, user) {

        //Sucessfully created user
        if (user) {
            console.log("Created User:" + user);
            res.redirect('/login')
        }
        else {
            console.log(err.message);
            res.set({ 'error': 1 });
            res.redirect('/signup')
        }

    })(req, res, next);
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
        sp.sendParameters(obj);
        res.send(
            'Load PreCalculatedJson Without Auth<br><br>'
            + '<a href=/solo>The Solos</a><br><br>'
            + '<a href=/cb>Crying Baby</a><br><br>'
            + '<a href=/leo>Leonidas</a><br><br>'
        );
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

app.listen(3000, function () {
    console.log('Middle-Tier Listening on Port 3000');
});
