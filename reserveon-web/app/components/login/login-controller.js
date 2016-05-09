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
                    //config.headers.Authorization = 'Bearer ' + token;
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
            return $http.get(API + '/institute/recents');
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
    app.config(function ($httpProvider,$sceDelegateProvider) {
        $httpProvider.interceptors.push('authInterceptor');
        $httpProvider.defaults.headers.common['Access-Control-Allow-Headers'] = '*';
        $sceDelegateProvider.resourceUrlWhitelist([
            'self',
            'http://reserveonapi.azurewebsites.net/**/**/**',
            'http://reserveonapi.azurewebsites.net/**/**',
            'http://reserveonapi.azurewebsites.net/**',
            'http://reserveonapi.azurewebsites.net',
        ]);
        $httpProvider.defaults.headers.common = {};
        $httpProvider.defaults.headers.post = {};
        $httpProvider.defaults.headers.put = {};
        $httpProvider.defaults.headers.patch = {};
        $httpProvider.defaults.headers.common.Authorization = 'Basic rkMYYE-VxosC3lz2LupkqsKVyKQAV1CP_SRUcJLSR2J4erGwjhywbp2-hamvtfPcrLAwX4O7CYkZLKvMnxNllTSE_FoZ4xHTcbw9kIG-jfPnxaxVjB13YM2BAs5p5dVu5L-rFgKxaVPTEKVo4X3KEhxOVWYudAzy-BAbNbba9Gi9zQ7yK86upEX4hp8O-5ew4RX9tOLRMPZsNQu5vKfQIYQZsP6-PMnGimwJxuOG8Y36BpeMfBc9tRvs5j8OE8aWf5_VPrX78LGRIJ1n-7vQ8ewfUWCxrcbF4QZU1XY6Bi8yowXue97ZkdRA1xa1VtO3SWht4pZVpY6cLWgm1HsDFM-wnYGE7CW9R-IzojF0E0dDR30uE0K2zaJUYeUx6UNrWeIdiIC9HCN3ovQ-_5XgmR57IyS70ocCP443Z1jBvpvnWTx14fTWtuq2qa3Trk8qg8eJYM2lYNy_tXa65nRXbQYeAN0sLLA6DPl-KYbADec';
    });

    app.controller('LoginController', MainCtrl);
    app.controller('RegisterController', MainCtrl);
    app.controller('InstituteController', MainCtrl);
})();

