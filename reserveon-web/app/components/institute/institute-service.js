'use strict';

(function () {

    function instituteService($http, API) {
        var self = this;

        self.insertInstitute = function (name, address, register, image, expedient) {
            var data = {
                name: name, 
                address: address, 
                register: register, 
                image: self.image, 
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

        self.insertImageInstitute = function (file) {
            var data = new FormData();
            
            data.append('file', file);

            var config = {
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                }
            };

            return $http.post(API + '/files/upload', data, config);
        };
    };

    app.service('instituteService', instituteService);

})();

