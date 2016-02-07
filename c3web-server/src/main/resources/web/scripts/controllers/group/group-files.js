(function(angular){
    'use strict';
    angular.module('C3web.controllers')
        .controller('group.groupFilesController',['$scope', '$routeParams', 'GroupFilesService',function($scope, $routeParams, FilesService) {
         /*$scope.elements=[{uid:'1', type:'dir',name:'TcpConnection terminated, stopping', size:'300 MB', owner:'Delar', ctime:'Tue, 17 Jun 2014 13:04:55 GMT'},
         {uid:'2', type:'img', size:'10 MB',name:'TcpConnection terminated',  owner:'Delar', ctime:'Tue, 17 Jun 2014 13:04:55 GMT'},
         {uid:'3', type:'txt', size:'1.2 MB',name:'TcpConnection terminated',  owner:'Delar', ctime:'Tue, 17 Jun 2014 13:04:55 GMT'}];*/

            $scope.elements = {};
            $scope.idGroup = $routeParams.id.toString();

            $scope.load = function(){
                FilesService.list($scope.idGroup).then(function(result){
                    var elementArray = [];
                    for (var i = 0; i <  result.data.length; i++) {
                        var file = result.data[i];
                        var fileData = {};
                        fileData.uid = i + 1;
                        fileData.type = "txt";
                        fileData.size = file.metadata.size
                        fileData.name = file.metadata.title;
                        fileData.url = file.url;
                        fileData.owner = file.metadata.owner
                        fileData.ctime = file.metadata.creationTime
                        elementArray.push(fileData)
                    }
                    $scope.elements = elementArray;
                });
            };

            $scope.detailInfo = false;

            $scope.load();

            $scope.showDetails = function (name) {
                for (var i = 0; i < $scope.elements.length; i++) {
                    if (name === $scope.elements[i].name) {
                        $scope.element = $scope.elements[i];
                    }
                }
                $scope.detailInfo = true;
            };

            $scope.closeDetails = function() {
                $scope.detailInfo = false;
            }

            $scope.download = function (url)
            {
               var getFileUrl = "/api/download/"+encodeURIComponent(url);
                window.open(getFileUrl);
            }
        }]);
})(angular)