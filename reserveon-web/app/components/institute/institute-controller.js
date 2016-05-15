'use strict';

(function () {

    function InstituteController(instituteService) {
        var self = this;

        function successRequest(res) {
            var img = res.data ? res.data.img : null;
            if(img) {
            	console.log('Success ' + res.status + ' ' + res.statusText);
            };
            console.log('Teste Sucesso');
        };
        
        function errorRequest(res) {
            console.log('Error ' + res.status + ' ' + res.statusText);
        };

        self.insertInstitute = function() {
            instituteService.insertInstitute(self.name, self.address, self.registerID, self.image, self.expedient)
            	.then(successRequest, errorRequest);
        };

        self.getInstitute = function () {
            instituteService.getInstitute()
            	.then(successRequest, errorRequest);
        };

        self.insertImageInstitute = function () {
            instituteService.insertImageInstitute(self.img)
            	.then(successRequest, errorRequest);
        };
    };

    app.controller('InstituteController', InstituteController);

})();

