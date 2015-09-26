'use strict';

angular.module('c3web')

    .controller('MainCtrl', function ($scope, $rootScope, $location) {
        $rootScope.$path = $location.path.bind($location);

        $scope.hello = function(name) {
            alert('Hello ' + (name || 'world') + '!');
        }
    });

