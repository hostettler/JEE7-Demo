var app = angular.module('studentServices', ['ngResource']);

app.service('studentService', ['$resource', '$log', function ($resource, $log) {
	return  $resource('../facade/studentService/', {}, {
		getAll: {
			method : 'GET',
			isArray : true
		},
		save: {
			method:'POST'
		},
		create: {
			method:'PUT'
		},
		get : {
			method : 'GET'
		}
	});
			
}]);


