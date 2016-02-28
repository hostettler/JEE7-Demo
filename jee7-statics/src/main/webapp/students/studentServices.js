var app = angular.module('studentServices', ['ngResource']);

app.service('studentService', ['$resource', '$log', function ($resource, $log) {
	return  $resource('../facade/studentService/', {}, {
		getAll: {
			method : 'GET',
			isArray : true
		},
		save: {
			method:'POST'
				/*,
			interceptor : {responseError : resourceErrorHandler}*/
		},
		create: {
			method:'PUT'
				/*,
			interceptor : {responseError : resourceErrorHandler}*/
		},
		get : {
			method : 'GET'
		}
	});
			
}]);


