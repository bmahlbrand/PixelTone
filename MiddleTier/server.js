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


//Expanding the routes
var userRoutes = require('./userRoutes');
var imageRoutes = require('./imageRoutes');
var testUpload = require('./testUpload');

//Load Route Handlers
app.use(express.static(__dirname + '/public'));
app.use('/api/users' , userRoutes);
app.use('/api/images' , checkAuth, imageRoutes);
app.use('/api/test' , checkAuth, testUpload);

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


app.get('*', function(req, res) {
  res.sendFile(__dirname + '/public/index.html');
});

app.listen(3000, function () {
    console.log('Middle-Tier Listening on Port 3000');
});
