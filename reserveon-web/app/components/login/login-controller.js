'use strict';

(function () {
    
    function LoginController(loginService, authService) {
        var self = this;
        self.loading = false;

        function successRequest(res) {
            console.log('Success ' + res.status + ' ' + res.statusText);
        }
        
        function errorRequest(res) {
            console.log('Error ' + res.status + ' ' + res.statusText);
        }

        self.login = function () {
            self.loading = true;
            loginService.login(self.username, self.password)
                .then(
                    function (res){
                        self.loading = false;
                        var token = res.data ? res.data.access_token : null;
                        if(token) {
                            window.location.href = '/#';
                            console.log('Success ' + res.status + ' ' + res.statusText);
                        };
                        
                    },
                    function (res) {
                        self.loading = false;
                        console.log('Error ' + res.status + ' ' + res.statusText);
                    }
                )
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

