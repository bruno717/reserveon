'use strict';

var app = angular.module('app', ['ngRoute']);

app.controller('AppController', ['$scope', '$routeParams', function($scope, $routeParams){
}]);

app.config(function($routeProvider, $httpProvider){
	$routeProvider
		.when('/login', {
			templateUrl: 'components/login/login.html', 
			controller: 'LoginController',
			controllerAs: 'login'
		})
		.when('/signup', {
			templateUrl: 'components/register/register.html', 
			controller: 'RegisterController' ,
			controllerAs: 'register' 
		})
		.otherwise('/');

});
