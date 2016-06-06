'use strict';

app.config(function($routeProvider){
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
		.when('/institute', {
		    templateUrl: 'components/institute/institute.html',
		    controller: 'InstituteController',
		    controllerAs: 'institute'
		})
		.otherwise('/');

});