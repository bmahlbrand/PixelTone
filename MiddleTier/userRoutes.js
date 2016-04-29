var express = require('express');
var crypto = require('crypto');
var flash = require('connect-flash');
var passport = require('passport');
var User = require('./userModel');
var Image = require('./imageModel');


var userRoutes = module.exports = express();

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

userRoutes.get('/getCurrentUser', function(req, res) {
  if (!req.isAuthenticated()) {
    return res.status(200).json({
      status: false
    });
  }
  res.status(200).json({ username: req.user.username })
});



//Get list of images and songs for user
userRoutes.get('/images', function (req, res) {
    if (!req.isAuthenticated()) {
        // console.log("Not authed");
        return res.status(200).json({
            status: false
        });
    }
    
    //console.log(req.user);
    //Find all images uploaded by specific user
    Image.collection.find( { 'local.user': req.user.username }).toArray(function (err, docs) {
        if (!docs) {
            // console.log("No uploads");
            return res.status(200).json({
                status: false
            });
        }
        // console.log("UPloads");
        var imageData = [];

        docs.forEach(function (value) {
            imageData.push(value.local);
        });
        imageData.reverse();
        var jsonString = JSON.stringify(imageData);

        res.status(200).json({
            imageData
        });

    });
});