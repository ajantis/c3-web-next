(function(angular){
    'use strict';

    angular.module('app')
        .controller('MainCtrl', function ($scope, $location, version) {
            $scope.$path = $location.path.bind($location);
            $scope.version = version;
        });
})