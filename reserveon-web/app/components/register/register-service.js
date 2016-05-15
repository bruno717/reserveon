'use strict';

(function () {

    function registerService($http, API) {
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
    };

    app.service('registerService', registerService);

})();

