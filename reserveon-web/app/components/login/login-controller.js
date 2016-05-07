'use strict';
/*
app.controller('LoginController',  ['$scope', function($scope) {
    $scope = 0;
}]);
*/

; (function () {
    function authInterceptor(API, auth) {
        return {
            request: function (config) {
                var token = auth.getToken();
                if (config.url.indexOf(API) === 0 && token) {
                    config.headers.Authorization = 'Bearer ' + token;
                }

                return config;
            },

            response: function (res) {
                if (res.config.url.indexOf(API) === 0 && res.data.access_token) {
                    auth.saveToken(res.data.access_token);
                }

                return res;
            }
        }
    };


    function authService($window) {
        var self = this;

        self.parseJwt = function (token) {
            var base64Url = token.split('.')[1];
            var base64 = base64Url.replace('-', '+').replace('_', '/');
            return JSON.parse($window.atob(base64));
        };

        self.saveToken = function (token) {
            $window.localStorage['jwtToken'] = token;
        };

        self.getToken = function () {
            return $window.localStorage['jwtToken'];
        };

        self.isAuthed = function () {
            var token = self.getToken();
            if (token) {
                var params = self.parseJwt(token);
                return Math.round(new Date().getTime() / 1000) <= params.exp;
            } else {
                return false;
            }
        };

        self.logout = function () {
            $window.localStorage.removeItem('jwtToken');
        };
    };

    function userService($http, API, auth) {
        var self = this;

        self.register = function (email, password, name) {
            var data = {
                email: email,
                password: password,
                name: name,
                profileid: 1
            };
            var config = {
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            };
            return $http.post(API + '/users', data, config);
        };

        self.login = function (username, password) {
            var data = {
                grant_type: "password",
                username: username,
                password: password
            };
            var config = {
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            };
            return $http.post(API + '/token', data, config);
        };

        self.insertInstitute = function (name, address, register, image, expedient) {
            var data = {
                name: name, 
                address: address, 
                register: register, 
                image: image, 
                expedient: expedient
            };
            var config = {
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            };
            return $http.post(API + '/institute', data, config);
        };

        self.getInstitute = function () {
            return $http.get(API + '/institute/recents')
        }

    };

    function MainCtrl(user, auth) {
        var self = this;

        function handleRequest(res) {
            var token = res.data ? res.data.access_token : null;
            if(token) { console.log('JWT:', token); }
            //self.message = res.data.message;
        }

        self.login = function () {
            user.login(self.username, self.password)
            .then(handleRequest, handleRequest)
        }
        self.register = function () {
            user.register(self.email, self.password, self.name)
            .then(handleRequest, handleRequest)
        }
        self.insertInstitute = function() {
            user.insertInstitute(self.name, self.address, self.registerID, self.image, self.expedient)
              .then(handleRequest, handleRequest)
        }
        self.getInstitute = function () {
            user.getInstitute()
             .then(handleRequest, handleRequest)
        }
        self.logout = function () {
            auth.logout && auth.logout()
        }
        self.isAuthed = function () {
            return auth.isAuthed ? auth.isAuthed() : false
        }
    };

    app.factory('authInterceptor', authInterceptor);
    app.service('user', userService);
    app.service('auth', authService);
    app.constant('API', 'http://reserveonapi.azurewebsites.net');
    app.config(function ($httpProvider) {
        $httpProvider.interceptors.push('authInterceptor');
    });

    app.controller('LoginController', MainCtrl);
    app.controller('RegisterController', MainCtrl);
    app.controller('InstituteController', MainCtrl);
})();

