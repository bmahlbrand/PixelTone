var express      = require('express');
var crypto = require('crypto');
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
var nodemailer = require('nodemailer');
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

app.use('/songViewer', function(req, res) {
  res.sendFile(path.join(__dirname, '../website', 'index.html'));
});

app.post('/forgot', function(req, res, next) {
    console.log(req.body.email);
    User.findOne({ username: req.body.email }, function (err, user) {
        // if there are any errors, return the error
        if (err) {
            console.log("Other Error");
            return res.redirect('/');
        }
                
        // check to see if theres already a user with that email
        console.log(user);

        if (user) {
            //Create token
            var token = crypto.randomBytes(32).toString('hex');

            user.local.resetPasswordToken = token;
            console.log(token);
            user.local.resetPasswordExpires = Date.now() + 3600000; // 1 hour
                
            console.log('Created Token');
            console.log(user.username);
            user.save(function (err) {
                if (err)
                    throw err;
                else {
                    console.log('Saved Token Creation');
                        
             
                    var smtpTransport = nodemailer.createTransport('smtps://pixeltonepassreset%40gmail.com:PUTPASSWORDHERE@smtp.gmail.com');
                  var mailOptions = {
                      to: user.username,
                      from: 'pixeltonepassreset@gmail.com',
                      subject: 'Node.js Password Reset',
                      text: 'You are receiving this because you (or someone else) have requested the reset of the password for your account.\n\n' +
                        'Please click on the following link, or paste this into your browser to complete the process:\n\n' +
                        'http://' + req.headers.host + '/reset/' + token + '\n\n' +
                        'If you did not request this, please ignore this email and your password will remain unchanged.\n'
                    };
                    smtpTransport.sendMail(mailOptions, function(error, info) {
                      if(error){
                        return console.log(error);
                    }
                    console.log('Message sent: ' + info.response);
                    });
                    
                    
                    return res.send('Token Created for One Hour:' + token
                        + '<br>Click here to continue: <a href=/users/reset/' + token + '>Here</a>');
                }
            });


        } else {
            console.log('user does not exist');
            return res.redirect('/');
        }
    });
});

app.get('/reset/:token', function(req, res) {
    
    console.log(req.params.token);
    
  User.findOne({ 'local.resetPasswordToken': req.params.token, 'local.resetPasswordExpires': { $gt: Date.now() } }, function(err, user) {
    if (!user) {
      console.log('error', 'Password reset token is invalid or has expired.');
      return res.redirect('/users/forgot');
    }
    
    req.flash('user', user);
    //console.log('Found user to reset:' + req.user);
    return res.redirect('/resetUI');   
  });
});

//If token is legit, setup reset password UI
app.get('/resetUI', function (req, res) {
    var userREQ = req.flash('user');
    if (userREQ == null) {
        console.log('USER IS NULL');
        return res.redirect('/login');
    }

    User.findOne({ 'local.resetPasswordToken': userREQ[0].local.resetPasswordToken, 'local.resetPasswordExpires': { $gt: Date.now() } }, function (err, user) {
        if (!user) {
            console.log('error', 'Password reset token is invalid or has expired.');
            return res.redirect('/users/forgot');
        }
        else {
            return res.send('<form action="/reset" method="post">'
                + '<p>Email: <input type="text" name="resetpass" placeholder="newpass" /></p>'
                + '<p><input type="submit" value="reset" /></p>'
                + '<input type="hidden" name="token" value="' + user.local.resetPasswordToken + '" />'
                + '</form>'
                );

        }
    });

});

//Receives token and password
//Make sure token hasn't expired
//update users password
app.post('/reset', function (req, res) {


    if (req.isAuthenticated()) {
        //user is alreay logged in
        return res.redirect('/');
    }

    User.findOne({ 'local.resetPasswordToken': req.body.token, 'local.resetPasswordExpires': { $gt: Date.now() } }, function (err, user) {
        if (!user) {
            req.flash('error', 'Password reset token is invalid or has expired.');
            return res.redirect('back');
        }

        //Set new password
        user.setPassword(req.body.resetpass, function(err) {
            if (err) //handle error
                console.log(err);
            user.save(function(err) {
                if (err){ //handle error
                    console.log(err);
                }
                else {//handle success
                    console.log("Password saved succesfully");
                }
            });
        });
        
        //Unset info
        user.local.resetPasswordToken = undefined;
        user.local.resetPasswordExpires = undefined;

        user.save(function (err) {
            if (err)
                throw err;
            else {
                console.log('RESET PASSWORD');
                return res.redirect('/login');
            
            }
        });
    });
});



//FOR QUICK TESTING
app.get('/deadbeef', function(req, res, next) {
    res.send(
        'Load PreCalculatedJson Without Auth<br><br>'
        + '<a href=/solo>The Solos</a><br><br>'
        + '<a href=/cb>Crying Baby</a><br><br>'
        + '<a href=/leo>Leonidas</a><br><br>'
        + '<a href=/sad>SAD</a><br><br>'
        + '<a href=/happy>HAPPY</a><br><br>'
    );
});

app.get('/sad', function(req, res, next) {
    var file = 'sad.json'
    jsonfile.readFile(file, function(err, obj) {
        var ret = sp.sendParameters(obj, res, 0);
        res.send(
            'Load PreCalculatedJson Without Auth<br><br>'
            + '<a href=/solo>The Solos</a><br><br>'
            + '<a href=/cb>Crying Baby</a><br><br>'
            + '<a href=/leo>Leonidas</a><br><br>'
            + '<a href=/sad>SAD</a><br><br>'
            + '<a href=/happy>HAPPY</a><br><br>'
        );
    });
});


app.get('/happy', function(req, res, next) {
    var file = 'happy.json'
    jsonfile.readFile(file, function(err, obj) {
        var ret = sp.sendParameters(obj, res, 0);
        res.send(
            'Load PreCalculatedJson Without Auth<br><br>'
            + '<a href=/solo>The Solos</a><br><br>'
            + '<a href=/cb>Crying Baby</a><br><br>'
            + '<a href=/leo>Leonidas</a><br><br>'
            + '<a href=/sad>SAD</a><br><br>'
            + '<a href=/happy>HAPPY</a><br><br>'
        );
    });
});




app.get('/solo', function(req, res, next) {
    var file = 'solo.json'
    jsonfile.readFile(file, function(err, obj) {
        var ret = sp.sendParameters(obj, res, 0);
        res.send(
            'Load PreCalculatedJson Without Auth<br><br>'
            + '<a href=/solo>The Solos</a><br><br>'
            + '<a href=/cb>Crying Baby</a><br><br>'
            + '<a href=/leo>Leonidas</a><br><br>'
            + '<a href=/sad>SAD</a><br><br>'
            + '<a href=/happy>HAPPY</a><br><br>'
        );
    });
});


app.get('/cb', function(req, res, next) {
    var file = 'cb.json'
    jsonfile.readFile(file, function(err, obj) {
        var ret = sp.sendParameters(obj, res, 0);
        res.send(
            'Load PreCalculatedJson Without Auth<br><br>'
            + '<a href=/solo>The Solos</a><br><br>'
            + '<a href=/cb>Crying Baby</a><br><br>'
            + '<a href=/leo>Leonidas</a><br><br>'
            + '<a href=/sad>SAD</a><br><br>'
            + '<a href=/happy>HAPPY</a><br><br>'
        );
    });

});

app.get('/leo', function(req, res, next) {
    var file = 'leo.json'
    jsonfile.readFile(file, function(err, obj) {
        var ret = sp.sendParameters(obj, res, 0);
        res.send(
            'Load PreCalculatedJson Without Auth<br><br>'
            + '<a href=/solo>The Solos</a><br><br>'
            + '<a href=/cb>Crying Baby</a><br><br>'
            + '<a href=/leo>Leonidas</a><br><br>'
            + '<a href=/sad>SAD</a><br><br>'
            + '<a href=/happy>HAPPY</a><br><br>'
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
