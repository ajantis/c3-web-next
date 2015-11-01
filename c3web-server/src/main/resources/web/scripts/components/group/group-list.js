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

    angular.module('C3web.components')
        .controller('group.groupListController', ctrl);

})(angular)