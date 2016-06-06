'use strict';

(function () {
    
    function LoginController(loginService, authService) {    
        var self = this;

        self.login = function () {

            self.error.credentials.value = true;
            
            if (self.username === "" || self.username === undefined || self.password === "" || self.password === undefined) {
                if (self.username === "" || self.username === undefined) {
                    self.error.username.value = true;
                    self.error.username.message = "Coloque seu email."
                };
                if (self.password === "" || self.password === undefined) {
                    self.error.password.value = true;
                    self.error.password.message = "Coloque sua senha."
                };
            } else {
                self.loading = true;
                loginService.login(self.username, self.password)
                    .then(
                        function (res){
                            self.loading = false;
                            var token = res.data ? res.data.access_token : null;
                            if(token) {
                                window.location.href = '/#';
                                console.log('Success ' + res.status + ' ' + res.statusText);
                            }
                        },
                        function (res) {
                            self.loading = false;
                            if (res.status === 400) {
                                self.error.credentials.value = false;
                                self.error.credentials.message = "e-mail ou senha incorretos";
                            } else {
                                self.error.credentials.value = false;
                                self.error.credentials.message = "Ocorreu um erro! Atualize a página e tente novamente.";
                            }
                        }
                    );
            };
        };

        self.logout = function () {
            authService.logout();
        };
        
        self.isAuthed = function () {
            return authService.isAuthed ? authService.isAuthed() : false;
        };

    }
    
    app.controller('LoginController', LoginController);

})();

