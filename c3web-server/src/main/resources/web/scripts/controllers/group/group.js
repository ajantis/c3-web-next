(function(angular){
    'use strict';
    angular.module('C3web.controllers')
        .controller('group.groupController',['$scope', '$routeParams', 'GroupListService',function($scope, $routeParams, GroupListService) {
         $scope.group = {};
         var idGroup = $routeParams.id;

         $scope.load = function(){
            GroupListService.get(idGroup).then(function(result){
                $scope.group = result.data;
            })
         };

         $scope.load();
     }]);
})(angular)