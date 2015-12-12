(function(angular){
    'use strict';
    angular.module('C3web.controllers')
        .controller('group.groupFilesController',['$scope', function($scope) {
         $scope.elements=[{uid:'1', type:'dir',name:'TcpConnection terminated, stopping', size:'300 MB', owner:'Delar', ctime:'Tue, 17 Jun 2014 13:04:55 GMT'},
         {uid:'2', type:'img', size:'10 MB',name:'TcpConnection terminated',  owner:'Delar', ctime:'Tue, 17 Jun 2014 13:04:55 GMT'},
         {uid:'3', type:'txt', size:'1.2 MB',name:'TcpConnection terminated',  owner:'Delar', ctime:'Tue, 17 Jun 2014 13:04:55 GMT'}];
         $scope.detailInfo = false;
         $scope.load = function(){
            $scope.detailInfo = false;
         };
         $scope.load();

         $scope.showDetails = function(name) {
         		for (var i = 0; i < $scope.elements.length; i++) {
         			if (name === $scope.elements[i].name) {
         				$scope.element = $scope.elements[i];
         				break;
         			}
         		}
         		$scope.detailInfo = true;
         	}

         $scope.closeDetails = function() {
            $scope.detailInfo = false;
        }
     }]);
})(angular)