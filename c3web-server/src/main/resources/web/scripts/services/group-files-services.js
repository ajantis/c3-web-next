(function () {
    'use strict';

    angular.module('C3web.services')
    .factory('GroupFilesService', ['JsonService', function (jsonService) {

        var files = function (groupID,files) {
            return jsonService.get('group/' + groupID + '/files/'+files);
        };

        var get = function (groupID) {
            return jsonService.get('group/' + groupID);
        };
        var create = function (file) {
            return jsonService.post("upload", file)
        }

        return {
            list: list,
            get: get,
            create: create
        };
    }]);
})();