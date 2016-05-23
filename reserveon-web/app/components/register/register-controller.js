'use strict';

(function () {

    function RegisterController(registerService) {
        var self = this;
        self.loading = false;

        self.register = function () {
            self.loading = true;
            registerService.register(self.email, self.password, self.name)
            	.then(
                    function (res){
                        self.loading = false;
                        if(res.status == 200) {
                            window.location.href = '/#/login';
                            console.log('Success ' + res.status + ' ' + res.statusText);
                        };
                    },
                    function (res) {
                        self.loading = false;
                        console.log('Error ' + res.status + ' ' + res.statusText);
                    }
                )
        };
    };

    app.controller('RegisterController', RegisterController);

})();

