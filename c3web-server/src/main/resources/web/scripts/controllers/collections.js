(function (angular, undefined){
    'use strict';
    angular.module('C3web.controllers')
        .controller('CollectCtrl',[ '$scope',function ($scope) {
            $scope.elements = [
                {caption: "эксперимент"},
                {caption: "Компьютерный эксперимент"},
                {caption: "Организация эксперимента"},
                {caption: "Многофакторный эксперимент"},
                {caption: "Значимость эксперимента"},
                {caption: "Документирование эксперимента"},
                {caption: "Открытость эксперимента"},
                {caption: "Повторяемость эксперимента"},
                {caption: "Планирование эксперимента"},
                {caption: "Анализ результатов эксперимента"},
                {caption: "Новое в эксперименте"},
                {caption: "Редигер 'Как воспитать ученого'"},
                {caption: "Петр Капица 'Эксперимент. Теория. Практика.'"},
                {caption: "Инженеринг алгоритмов"},
                {caption: "Кризис доверия к эксперименту"}
                ];
            $scope.engElements =[
                {caption: "Experiment"},
                {caption: "Computer experiment"},
                {caption: "Research experiment"},
                {caption: "Experiment factors and parameters"},
                {caption: "Robust parameter design"},
                {caption: "Results analysis and interpretation"},
                {caption: "Algorithm engineering"},
                {caption: "Algorithm engineering Karlsruhe"},
                {caption: "Experiment documentation"},
                {caption: "Experiment reproducibility"},
                {caption: "Experiment capability Crisis"}

            ];
        }]);
})(angular);