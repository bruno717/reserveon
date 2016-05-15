'use strict';

(function () {
    
    function LoginController(loginService, authService) {
        var self = this;

        function successRequest(res) {
            var token = res.data ? res.data.access_token : null;

            if(token) {
                console.log('Success ' + res.status + ' ' + res.statusText);
            };
        };
        
        function errorRequest(res) {
            console.log('Error ' + res.status + ' ' + res.statusText);
        };

        self.login = function () {
            loginService.login(self.username, self.password)
                .then(successRequest, errorRequest)
        }

        self.logout = function () {
            authService.logout && authService.logout()
        }
        
        self.isAuthed = function () {
            return authService.isAuthed ? authService.isAuthed() : false
        }
    };
    
    app.controller('LoginController', LoginController);

})();

