'use strict';

(function () {
    
    function authService($window) {
        var self = this;
        self.saveToken = function (token) {
            $window.localStorage.setItem('token', token);
        };

        self.getToken = function () {
            return $window.localStorage.getItem('token');
        };

        self.isAuthed = function () {
            var token = self.getToken();
            if (token) {
                return true;    
            } else {
                return false;
            }
        };

        self.logout = function () {
            $window.localStorage.removeItem('token');
        };
    }

    function loginService($http, API) {
        var self = this;
        self.login = function (username, password) {
            var data = {
                grant_type: 'password',
                username: username,
                password: password
            };
            var config = {
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj) {
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    }
                    return str.join("&");
                },
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            };
            return $http.post(API + '/token', data, config);
        };

    }

    app.service('loginService', loginService);
    app.service('authService', authService);

})();

