(function () {
    'use strict';

    angular.module('C3web.services')
    .factory('FilesService', ['JsonService', function (jsonService) {

        var get = function (groupID,files) {
            return jsonService.get('group/' + groupID + '/files/'+files);
        };

        var list = function (groupID) {
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