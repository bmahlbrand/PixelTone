angular.module('pixelTone').factory('AuthService',
    ['$q', '$timeout', '$http',
        function ($q, $timeout, $http) {
            
            //path to songs, change when s3 upload works
            var path = "https://s3.amazonaws.com/pixeltone-midi/";

            // create user variable
            var user = null;
            var userName = null;
            var userData = null;
            var playSong = null;
            

            // return available functions for use in the controllers
            return ({
                getUserName: getUserName,
                isLoggedIn: isLoggedIn,
                getUserStatus: getUserStatus,
                login: login,
                logout: logout,
                register: register,
                getData: getData,
                getUserData: getUserData,
                getSong: getSong,
                setSong: setSong,
                getRecentData: getRecentData,
                resetPassword: resetPassword 
            });

            function getUserName() {
                return userName;
            }
            
            function getSong() {
                return playSong;
            }
            
            function setSong(songKey) {
                playSong = path + songKey;
            }

            function getRecentData() {
                if (isLoggedIn()) {
                    getData();
                    return userData;
                }
            }

            function getUserData() {
                if (isLoggedIn())
                    return userData;
            }


            function isLoggedIn() {
                if (user) {
                    return true;
                } else {
                    return false;
                }
            }

            function getUserStatus() {
                $http.get('/user/status')
                    // handle success
                    .success(function (data) {
                        if (data.status) {
                            user = true;
                        } else {
                            user = false;
                        }
                    })
                    // handle error
                    .error(function (data) {
                        user = false;
                    });
            }

            function login(username, password) {

                // create a new instance of deferred
                var deferred = $q.defer();

                // send a post request to the server
                $http.post('/user/login',
                    { username: username, password: password })
                    // handle success
                    .success(function (data, status) {
                        if (status === 200 && data.status) {
                            userName = username;
                            user = true;
                            deferred.resolve();
                        } else {
                            user = false;
                            deferred.reject();
                        }
                    })
                    // handle error
                    .error(function (data) {
                        user = false;
                        deferred.reject();
                    });

                // return promise object
                return deferred.promise;

            }

            function resetPassword(email) {

                // create a new instance of deferred
                var deferred = $q.defer();

                // send a post request to the server
                $http.post('/forgot',
                    { email: email })
                    // handle success
                    .success(function (data, status) {
                        if (status === 200 && data.status) {
                            console.log(status);
                            deferred.resolve();
                        } else {
                            console.log(status);
                            deferred.reject();
                        }
                    })
                    // handle error
                    .error(function (data) {
                        console.log(data);
                        deferred.reject();
                    });

                // return promise object
                return deferred.promise;

            }

            function logout() {

                // create a new instance of deferred
                var deferred = $q.defer();

                // send a get request to the server
                $http.get('/user/logout')
                    // handle success
                    .success(function (data) {
                        user = false;
                        userData = null;
                        deferred.resolve();
                    })
                    // handle error
                    .error(function (data) {
                        user = false;
                        deferred.reject();
                    });

                // return promise object
                return deferred.promise;

            }

            function register(username, password) {

                // create a new instance of deferred
                var deferred = $q.defer();

                // send a post request to the server
                $http.post('/user/register',
                    { username: username, password: password })
                    // handle success
                    .success(function (data, status) {
                        if (status === 200 && data.status) {
                            deferred.resolve();
                        } else {
                            deferred.reject();
                        }
                    })

                    // handle error
                    .error(function (data) {
                        deferred.reject();
                    });

                // return promise object
                return deferred.promise;

            }

            function getData() {
                // create a new instance of deferred
                var deferred = $q.defer();

                if (!isLoggedIn())
                    deferred.reject("not logged in");
                // send a post request to the server
                $http.get('/user/images')
                    // handle success
                    .success(function (data, status) {
                        if (status === 200) {
                            userData = data;
                            console.log(userData);
                            deferred.resolve(data);
                        } else {
                            deferred.reject(status);
                        }
                    })
                    // handle error
                    .error(function (data) {
                        deferred.reject("HTTP ERROR");
                    });
                // return promise object
                return deferred.promise;
            }
        }]);