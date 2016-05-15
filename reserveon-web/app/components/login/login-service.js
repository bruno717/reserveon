'use strict';

(function () {
    
    function authService($window) {
        var self = this;

        self.parseJwt = function (token) {
            var base64Url = token.split('.')[1];
            var base64 = base64Url.replace('-', '+').replace('_', '/');
            return JSON.parse($window.atob(base64));
        };

        self.saveToken = function (token) {
            $window.localStorage['token'] = token;
        };

        self.getToken = function () {
            return $window.localStorage['token'];
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
            $window.localStorage.removeItem('token');
        };
    };

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

    };

    app.service('loginService', loginService);
    app.service('authService', authService);

})();

