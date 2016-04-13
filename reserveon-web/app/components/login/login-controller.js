'use strict';
/*
app.controller('LoginController',  ['$scope', function($scope) {
    $scope = 0;
}]);
*/

;(function(){
function authInterceptor(API, auth) {
  return {
    request: function(config) {
      var token = auth.getToken();
      if(config.url.indexOf(API) === 0 && token) {
        config.headers.Authorization = 'Bearer ' + token;
      }

      return config;
    },

    response: function(res) {
    if(res.config.url.indexOf(API) === 0 && res.data.token) {
      auth.saveToken(res.data.token);
    }

      return res;
    }
  }
};


function authService($window) {
  var self = this;

  self.parseJwt = function(token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse($window.atob(base64));
  };

  self.saveToken = function(token) {
    $window.localStorage['jwtToken'] = token;
  };

  self.getToken = function() {
    return $window.localStorage['jwtToken'];
  };

  self.isAuthed = function() {
    var token = self.getToken();
    if(token) {
      var params = self.parseJwt(token);
      return Math.round(new Date().getTime() / 1000) <= params.exp;
    } else {
      return false;
    }
  };

  self.logout = function() {
    $window.localStorage.removeItem('jwtToken');
  };
};

function userService($http, API, auth) {
  var self = this;
 
  self.register = function(username, password) {
    return $http.post(API + '/register', {
        username: username,
        password: password
      })
  };

  self.login = function(username, password) {
    return $http.post(API + '/login', {
        username: username,
        password: password
      })
  };

};

function MainCtrl(user, auth) {
  var self = this;

  function handleRequest(res) {
    var token = res.data ? res.data.token : null;
    if(token) { console.log('JWT:', token); }
    self.message = res.data.message;
  }

  self.login = function() {
    user.login(self.username, self.password)
      .then(handleRequest, handleRequest)
  }
  self.register = function() {
    user.register(self.username, self.password)
      .then(handleRequest, handleRequest)
  }
  self.logout = function() {
    auth.logout && auth.logout()
  }
  self.isAuthed = function() {
    return auth.isAuthed ? auth.isAuthed() : false
  }
};

app.factory('authInterceptor', authInterceptor);
app.service('user', userService);
app.service('auth', authService);
app.constant('API', '');
app.config(function($httpProvider) {
  $httpProvider.interceptors.push('authInterceptor');
});

app.controller('LoginController', MainCtrl)
})();

