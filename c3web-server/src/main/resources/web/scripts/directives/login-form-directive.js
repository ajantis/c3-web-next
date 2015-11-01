(function () {
    'use strict';
    angular
.module('C3web.directives')
.controller('loginFormController', ['$scope', '$rootScope', 'Data', function ($scope, $rootScope, Data) {

    $scope.IsLogin = false;
    $scope.login = {};
    $scope.signup = {};
    $scope.doLogin = function (customer) {
        Data.post('login', {
            customer: customer
        }).then(function (results) {
            Data.toast(results);
            if (results.status == "success") {
                 $scope.IsLogin = true;
                 $scope.user = {
						uid:results.uid,
						name : results.name,
                    	email : results.email
					};
            }
        });
    };
    $scope.logout = function () {
        Data.get('logout').then(function (results) {
            Data.toast(results);
            $scope.IsLogin = false;
        });
    }
	$scope.load = function(){
		$scope.IsLogin= false;
		Data.get('ping').then(function (results) {
			if (results.uid) {
				$scope.IsLogin= true;
				$scope.user = {
					uid:results.uid,
					name : results.name,
					email : results.email
				};
			} else {
			   $scope.IsLogin= false;
			}
		});
	};
$scope.load();
}
    ])
    .directive('loginForm', function () {
        return {
            restrict: 'E',
            replace: true,
            scope:
            {
                labelName: '@labelName',
                user : '=user'
            },
            templateUrl: 'views/login-form.html',
            controller: 'loginFormController'
        };
    });
})();