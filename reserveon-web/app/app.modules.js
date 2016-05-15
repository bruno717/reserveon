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
        }
    };

	app.directive('fileModel', ['$parse', function ($parse){

		return {
			restrict: 'A',
			link: function (scope, element, attrs) {
				var model = $parse(attrs.fileModel);
				var modelSetter = model.assign;

				element.bind('change', function (){
					scope.$apply(function (){
						modelSetter(scope,element[0].files[0]);
					});
				});

			}
		};

	}]);
    
    app.config(function ($httpProvider) {
        $httpProvider.interceptors.push('authInterceptor');
    });

    app.factory('authInterceptor', authInterceptor);
	
	app.constant('API', 'http://reserveonapi.azurewebsites.net');

	app.controller('AppController', function (){});

})();

