(function(angular){
    'use strict';
    angular.module('C3web.controllers')
        .controller('HelloCtrl', function ($scope, $rootScope, $location) {
            $rootScope.$path = $location.path.bind($location);
            $scope.hello = function(name) {
                alert('Hello ' + (name || 'world') + '!');
            }
        });
})(angular)