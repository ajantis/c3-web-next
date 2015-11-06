(function () {
    'use strict';

    angular.module('C3web.services')
    .factory('GroupListService', ['JsonService', function (jsonService) {

        var list = function () {
            return jsonService.get('groups');
        };

        var get = function (groupID) {
            return jsonService.get('groups/' + groupID);
        };



        return {
            list: list,
            get: get
        };
    }]);
})();