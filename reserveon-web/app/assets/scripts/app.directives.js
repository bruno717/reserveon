'user strict';

app.directive('fileModel', ['$parse', function ($parse){

	return {
		restrict: 'A',
		link: function (scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function (){
				scope.$apply(function (){
					modelSetter(scope,element[0].files[0]);
				});
			});

		}
	};

}]);

app.directive('ngInitial', function() {

	return {
		restrict: 'A',
		controller: [
			'$scope', '$element', '$attrs', '$parse', function($scope, $element, $attrs, $parse) {
				var getter, setter, val;
				val = $attrs.ngInitial || $attrs.value;
				getter = $parse($attrs.ngModel);
				setter = getter.assign;
				setter($scope, val);
			}
	    ]
	};
	
});