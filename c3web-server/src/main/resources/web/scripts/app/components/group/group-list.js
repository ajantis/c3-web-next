(function(angular, undefined){
    'use strict';

    var ctrl = function($scope) {
        $scope.model = {
            greeting: 'Hello',
            list: [
                'One',
                'Two',
                'Three'
            ]
        };
    };

    ctrl.$inject = [
        '$scope'
    ];

    angular.module('app.components')
        .controller('app.components.group.groupListController', ctrl);

})(angular)