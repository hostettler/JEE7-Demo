var app = angular.module('cryptoProxy', []);



app.service('cryptoProxy', [ '$log', function($log) {

	this.crypt = function(student, schema) {
		$log.log('proxycrypt');
		$log.log('student firstname : ' + student.firstName);
		$log.log('student : ' + student);
		$log.log(schema.firstName)
		
		
		for (var property in student) {
			if (student.hasOwnProperty(property)) {
				if (schema[property] == 'crypt') {
					student[property] = 'crypted';
				}
    		}	
		}	
		
		return student;
	}

	this.decrypt = function(student) {
		$log.log('proxydecrypt');
		$log.log('student firstname : ' + student.firstName);
		$log.log('student : ' + student);
	}
} ]);
