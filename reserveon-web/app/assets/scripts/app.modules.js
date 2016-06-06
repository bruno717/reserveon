/**
 * @author Lucas Rodrigues da Silva <lucastec@outlook.com>
 * ReserveOn apllication
 */

'use strict';

var app = angular.module('app', ['ngRoute']);

(function () {

    function authInterceptor(API, authService) {
        return {
            request: function (config) {
                var token = authService.getToken();
                if (config.url.indexOf(API) === 0 && token) {
                    config.headers.Authorization = 'Bearer ' + token;
                }

                return config;
            },

            response: function (res) {
                if (res.config.url.indexOf(API) === 0 && res.data.access_token) {
                    authService.saveToken(res.data.access_token);
                }

                return res;
            }
        };
    }
    
    app.config(function ($httpProvider) {
        $httpProvider.interceptors.push('authInterceptor');
    });

    app.run(function (authService, $rootScope, $window){
        $rootScope.isAuthed = $window.localStorage.getItem('token') ? true : false;
    });

    app.factory('authInterceptor', authInterceptor);
	
	app.constant('API', 'http://reserveonapi.azurewebsites.net');

	app.controller('AppController', function (){});

})();

