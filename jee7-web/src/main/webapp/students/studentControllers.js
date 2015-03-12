var controllers = angular.module('studentControllers', [ 'ngTouch', 'ui.grid', 'ui.grid.selection',
		'studentServices' ]);

controllers.factory('SharedData', function () {
	return {student : {firstName:'', lastName:'', birthDate : ''}};
});

controllers.controller('studentFormController', [ '$scope', 'SharedData', '$log', 'ngFabForm','studentService', '$filter', '$rootScope',
                                                  function($scope, SharedData, $log, ngFabForm, studentService, $filter,$rootScope) {
                                                  	$scope.student = SharedData.student;
                                                  	$scope.customFormOptions = {
                                                  		    validationsTemplate: 'your-tpl.html',
                                                  		    preventInvalidSubmit: false,
                                                  		    preventDoubleSubmit: false,
                                                  		    setFormDirtyOnSubmit: true,
                                                  		    scrollToAndFocusFirstErrorOnSubmit: true,
                                                  		    scrollAnimationTime: 900,
                                                  		    scrollOffset: -100,
                                                  		  };
                                                    $scope.form.reset = function() {
                                                    	$scope.student.id = undefined;
                                                    	$scope.student.firstName=undefined;
                                                    	$scope.student.lastName=undefined;
                                                    	$scope.student.birthDate=undefined;
                                                    }	
                                                    $scope.form.save = function() {
                                                    	
                                                    	$log.log($scope.student.birthDate);
                                                    	$scope.student.birthDate = $filter('date')($scope.student.birthDate,'yyyy-MM-dd');
                                                    	$log.log($scope.student.birthDate);
                                                    	
                                                    	if (!$scope.student.id) {
                                                    		
                                                    		$scope.student.id = -1;
                                                    		
                                                      		$log.log($scope.student.id);
                                                    		$log.log($scope.student.firstName);
                                                    		$log.log($scope.student.lastName);
                                                    		$log.log($scope.student.birthDate);
                                                    		
                                                    		$log.log('Create Student');
                                                    		studentService.create($scope.student, function() 
                                                    		{
                                                    			$rootScope.$broadcast('refresh', []);
                                                    		});
                                                    	} else {
                                                    		$log.log('Save Student');
                                                    		
                                                    		$log.log($scope.student.id);
                                                    		$log.log($scope.student.firstName);
                                                    		$log.log($scope.student.lastNme);
                                                    		$log.log($scope.student.birthDate);
                                                    		studentService.save($scope.student, function() 
                                                    		{
                                                    			$rootScope.$broadcast('refresh', []);
                                                    		});
                                                    	}
                                                    	
                                                    	
                                                    	
                                                    }
                                                    $scope.today = function() {
                                                        $scope.dt = new Date();
                                                      };
                                                      $scope.today();

                                                      $scope.clear = function () {
                                                        $scope.dt = null;
                                                      };

                                                      // Disable weekend selection
                                                      $scope.disabled = function(date, mode) {
                                                        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
                                                      };

                                                      $scope.toggleMin = function() {
                                                        $scope.minDate = $scope.minDate ? null : new Date();
                                                      };
                                                      $scope.toggleMin();

                                                      $scope.open = function($event) {
                                                        $event.preventDefault();
                                                        $event.stopPropagation();

                                                        $scope.opened = true;
                                                      };

                                                      $scope.dateOptions = {
                                                        formatYear: 'yyyy',
                                                        startingDay: 1
                                                      };

                                                      $scope.formats = ['yyyy-MM-dd', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
                                                      $scope.format = $scope.formats[0];                                                   
                                                  }]);

controllers.controller('studentGridController', [ '$scope', '$log', 'studentService', 'SharedData', '$rootScope',
		function($scope, $log, studentService, SharedData,$rootScope) {
			$scope.$on('refresh', function(event, data) { 
				$scope.gridDescription.data =  studentService.getAll(); 
			}),
			$scope.columns = [{ 
				field : 'id',
				displayName : 'Id',
  			 width: '10%'
			},
			{ 
				field : 'firstName',
				displayName : 'First name',
				 width: '30%'
			}, {
				field : 'lastName',
				displayName : 'Last name',
				 width: '40%'
			}, {
				field : 'birthDate',
				displayName : 'Birth Date',
				 width: '20%'
			} ];
			$scope.gridDescription = {
				enableRowSelection : true,
				enableSelectAll : false,
				enableRowHeaderSelection: false,
				multiSelect : false,
				data : studentService.getAll(),
				enableSorting : true,
				columnDefs : $scope.columns,
			    onRegisterApi : function(gridApi){
					$scope.gridApi = gridApi;
					gridApi.selection.on.rowSelectionChanged($scope,function(row){
					if (row.isSelected) {
						SharedData.student.firstName =row.entity.firstName;
						SharedData.student.lastName =row.entity.lastName;
						SharedData.student.birthDate =row.entity.birthDate;
						SharedData.student.id =row.entity.id;
					}
					
		        });
			   }
			}		
		} ]);
