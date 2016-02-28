var controllers = angular.module('studentControllers', [ 'ngTouch', 'ui.grid',
		'ui.grid.selection', 'studentServices', 'ngWebsocket', 
		'ngAnimate', 'angular-growl', 'cryptoProxy' ]);

app.config([ 'growlProvider', function(growlProvider) {
	growlProvider.globalTimeToLive(5000);
} ]);


controllers.factory('SharedData', function() {
	return {
		student : {
			firstName : '',
			lastName : '',
			birthDate : ''
		}
	};
});

controllers.controller('tabsController', [ '$scope', '$log',
		function($scope, $log) {

		} ]);

controllers.controller('eventPopupController', [
		'$scope',
		'$log',
		'$websocket',
		'growl',
		function($scope, $log, $websocket, growl) {
			 var ws = new WebSocket('wss://localhost:8443/jee7-demo-web/studentBus');
			 ws.onopen = function()
			 {	        
				 growl.info("open connection");
			 };
			 ws.onmessage = function (evt) 
			 { 			     	
				var msg = evt.data;
				growl.info(msg);
			 };
			 ws.onclose = function()
			 { 
			 };
		} ]);

controllers.controller('studentFormController',
		[
				'$scope',
				'SharedData',
				'$log',
				'ngFabForm',
				'studentService',
				'$filter',
				'$rootScope',
				'growl',
				'cryptoProxy',
				function($scope, SharedData, $log, ngFabForm, studentService,
						$filter, $rootScope, growl, proxy) {
					
					
					$scope.student = SharedData.student;
					$scope.customFormOptions = {
						validationsTemplate : 'your-tpl.html',
						preventInvalidSubmit : false,
						preventDoubleSubmit : false,
						setFormDirtyOnSubmit : true,
						scrollToAndFocusFirstErrorOnSubmit : true,
						scrollAnimationTime : 900,
						scrollOffset : -100,
					};
					$scope.form.reset = function() {
						$scope.student.id = undefined;
						$scope.student.firstName = undefined;
						$scope.student.lastName = undefined;
						$scope.student.birthDate = undefined;	
						$scope.studentForm.$setPristine();
						$scope.studentForm.$setValidity();
						$scope.studentForm.$setUntouched();
						$scope.$broadcast('NG_FAB_FORM_RESET_ALL');
					}
					$scope.form.save = function() {
						$log.log($scope.student.birthDate);
						$scope.student.birthDate = $filter('date')(
								$scope.student.birthDate, 'yyyy-MM-dd');
						$log.log($scope.student.birthDate);

						
						if (!$scope.student.id || $scope.student.id == -1) {
							$scope.student.id = -1;
							$log.log($scope.student.id);
							$log.log($scope.student.firstName);
							$log.log($scope.student.lastName);
							$log.log($scope.student.birthDate);

							$log.log('Create Student');
							studentService.create($scope.student, function(data) {
								$rootScope.$broadcast('refresh', []);
								growl.info("Student successfully added");
							}, function(error) {
								$scope.student.id = null;
								$rootScope.$broadcast('refresh', []);
								$log.log(error);
								growl.error("Error while adding student!");
								$scope.$broadcast('NG_FAB_FORM_RESET_ALL');
							});
						} else {
							$log.log('Save Student');
							$log.log($scope.student.id);
							$log.log($scope.student.firstName);
							$log.log($scope.student.lastName);
							$log.log($scope.student.birthDate);
							studentService.save($scope.student, function(data) {
								$rootScope.$broadcast('refresh', []);
								growl.info("Student successfully updated");
								$scope.studentForm.setPristine();
							}, function(error) {
								$log.log(error);
								growl.error("Error while updating student!");
								$scope.$broadcast('NG_FAB_FORM_RESET_ALL');
							});
						}

					}
					$scope.today = function() {
						$scope.dt = new Date();
					};
					$scope.today();

					$scope.clear = function() {
						$scope.dt = null;
					};

					// Disable weekend selection
					$scope.disabled = function(date, mode) {
						return (mode === 'day' && (date.getDay() === 0 || date
								.getDay() === 6));
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
						formatYear : 'yyyy',
						startingDay : 1
					};

					$scope.formats = [ 'yyyy-MM-dd', 'yyyy/MM/dd',
							'dd.MM.yyyy', 'shortDate' ];
					$scope.format = $scope.formats[0];
				} ]);

controllers
		.controller(
				'studentGridController',
				[
						'$scope',
						'$log',
						'studentService',
						'SharedData',
						'$rootScope',
						function($scope, $log, studentService, SharedData,
								$rootScope) {
							$scope.$on('refresh', function(event, data) {
								$scope.gridDescription.data = studentService
										.getAll();
							}), $scope.columns = [ {
								field : 'id',
								displayName : 'Id',
								width : '10%'
							}, {
								field : 'firstName',
								displayName : 'First name',
								width : '30%'
							}, {
								field : 'lastName',
								displayName : 'Last name',
								width : '40%'
							}, {
								field : 'birthDate',
								displayName : 'Birth Date',
								width : '20%'
							} ];
							$scope.gridDescription = {
								enableRowSelection : true,
								enableSelectAll : false,
								enableRowHeaderSelection : false,
								multiSelect : false,
								data : studentService.getAll(),
								enableSorting : true,
								columnDefs : $scope.columns,
								onRegisterApi : function(gridApi) {
									$scope.gridApi = gridApi;
									gridApi.selection.on
											.rowSelectionChanged(
													$scope,
													function(row) {
														if (row.isSelected) {
															SharedData.student.firstName = row.entity.firstName;
															SharedData.student.lastName = row.entity.lastName;
															SharedData.student.birthDate = row.entity.birthDate;
															SharedData.student.id = row.entity.id;
														}

													});
								}
							}
						} ]);
