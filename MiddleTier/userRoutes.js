var express = require('express');
var crypto = require('crypto');
var flash = require('connect-flash');
var passport = require('passport');
var User = require('./userModel');


var userRoutes = module.exports = express();

//Handle User Forgot Pass (DISPLAY PAGE)
userRoutes.get('/forgot', function (request, response) {

    if (request.isAuthenticated()) {
        //user is already logged in
        return response.redirect('/');
    }
   
    //Path to forgot pass page
    response.redirect('/users/forgotUI');
});

//Simple GUI for forgot pass testing
userRoutes.get('/forgotUI', function (req, res) {
    res.send('<form action="/users/forgot" method="post">'
        + '<p>Email: <input type="text" name="email" placeholder="email" /></p>'
        + '<p><input type="submit" value="forgot" /></p>'
        + '</form>'
        );
});

//Handle User Forgot Pass sent EMAIL 
userRoutes.post('/forgot', function (req, res) {
    if (req.isAuthenticated()) {
        //user is alreay logged in
        return res.redirect('/');
    }

    User.findOne({ 'local.email': req.body.email }, function (err, user) {
        // if there are any errors, return the error
        if (err) {
            console.log("Other Error");
            return res.redirect('/');
        }
                
        // check to see if theres already a user with that email
        if (user) {
            //Create token
            var token = crypto.randomBytes(32).toString('hex');

            user.local.resetPasswordToken = token;
            user.local.resetPasswordExpires = Date.now() + 3600000; // 1 hour
                
            console.log('Created Token');

            user.save(function (err) {
                if (err)
                    throw err;
                else {
                    console.log('Saved Token Creation');
                        
                    //SEND EMAIL HERE WITH LINK
                        
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

//Handles linked clicked on email
//Check if if usertoken hasn't expired yet, and user exists with that token
//if so, show reset password UI
userRoutes.get('/reset/:token', function(req, res) {
    
    console.log(req.params.token);
    
  User.findOne({ 'local.resetPasswordToken': req.params.token, 'local.resetPasswordExpires': { $gt: Date.now() } }, function(err, user) {
    if (!user) {
      console.log('error', 'Password reset token is invalid or has expired.');
      return res.redirect('/users/forgot');
    }
    
    req.flash('user', user);
    //console.log('Found user to reset:' + req.user);
    return res.redirect('/users/resetUI');   
  });
});

//If token is legit, setup reset password UI
userRoutes.get('/resetUI', function (req, res) {
    var userREQ = req.flash('user');
    if (userREQ == null) {
        console.log('USER IS NULL');
        return res.redirect('/users/forgot');
    }

    User.findOne({ 'local.resetPasswordToken': userREQ[0].local.resetPasswordToken, 'local.resetPasswordExpires': { $gt: Date.now() } }, function (err, user) {
        if (!user) {
            console.log('error', 'Password reset token is invalid or has expired.');
            return res.redirect('/users/forgot');
        }
        else {
            return res.send('<form action="/users/reset" method="post">'
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
userRoutes.post('/reset', function (req, res) {


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
        user.local.password = user.generateHash(req.body.resetpass);
        //Unset info
        user.local.resetPasswordToken = undefined;
        user.local.resetPasswordExpires = undefined;

        user.save(function (err) {
            if (err)
                throw err;
            else {
                console.log('RESET PASSWORD');
                req.logIn(user, function (err) {
                    if (err) { throw err; }
                    return res.redirect('/test/upload');
                });
            }
        });
    });
});

userRoutes.post('/register', function(req, res) {
  User.register(new User({ username: req.body.username }),
    req.body.password, function(err, account) {
    if (err) {
      return res.status(500).json({
        err: err
      });
    }
    passport.authenticate('local')(req, res, function () {
      return res.status(200).json({
        status: 'Registration successful!'
      });
    });
  });
});

userRoutes.post('/login', function(req, res, next) {
  passport.authenticate('local', function(err, user, info) {
    if (err) {
      return next(err);
    }
    if (!user) {
      return res.status(401).json({
        err: info
      });
    }
    req.logIn(user, function(err) {
      if (err) {
        return res.status(500).json({
          err: 'Could not log in user'
        });
      }
      res.status(200).json({
        status: 'Login successful!'
      });
    });
  })(req, res, next);
});

userRoutes.get('/logout', function(req, res) {
  req.logout();
  res.status(200).json({
    status: 'Bye!'
  });
});

userRoutes.get('/status', function(req, res) {
  if (!req.isAuthenticated()) {
    return res.status(200).json({
      status: false
    });
  }
  res.status(200).json({
    status: true
  });
});