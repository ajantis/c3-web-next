(function (angular, undefined) {
    var service = function () {
        return {
            equalsIgnoreCase: function (s1, s2) {
                return (!s1 && !s2) || s1.toLowerCase() === s2.toLowerCase();
            }
        };
    };

    angular.module('C3web.services')
        .factory('utility', service);

})(angular);