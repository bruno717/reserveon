'use strict';

(function () {

    function InstituteController(instituteService,$scope) {
        var self = this;
        self.loading = false;

        function successRequest(res) {
            console.log('Success ' + res.status + ' ' + res.statusText);
        }
        
        function errorRequest(res) {
            console.log('Error ' + res.status + ' ' + res.statusText);
        }

        self.insertInstitute = function() {
            instituteService.insertInstitute(self.name, self.address, self.registerID, self.image, self.expedient)
            	.then(successRequest, errorRequest);
        };

        self.getInstitute = function () {
            self.loading = true;
            instituteService.getInstitute()
            	.then(
                    function (res){
                        $scope.institutes = res.data;
                        self.loading = false;
                    }, 
                    function (res) {
                        self.loading = false;
                        console.log('Error ' + res.status + ' ' + res.statusText);
                    }
                );
        };

        self.insertImageInstitute = function () {
            instituteService.insertImageInstitute(self.img)
            	.then(successRequest, errorRequest);
        };
    };

    app.controller('InstituteController', InstituteController);

})();

