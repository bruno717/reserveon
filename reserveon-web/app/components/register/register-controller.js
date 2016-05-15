'use strict';

(function () {

    function RegisterController(registerService) {
        var self = this;

        function successRequest(res) {
            console.log('Success ' + res.status + ' ' + res.statusText);
        }
        
        function errorRequest(res) {
            console.log('Error ' + res.status + ' ' + res.statusText);
        }

        self.register = function () {
            registerService.register(self.email, self.password, self.name)
            	.then(successRequest, errorRequest);
        };
    };

    app.controller('RegisterController', RegisterController);

})();

