(function () {
    'use strict';

    angular
        .module('delfosApp')
        .controller("TimeSheetController", TimeSheetController);

    TimeSheetController.$inject = ['$scope', '$state', 'SharedData', 'Sprint', 'DateUtils', 'AlertService', 'Activities', 'Task', 'Principal'];

    function TimeSheetController($scope, $state, SharedData, Sprint, DateUtils, AlertService, Activities, Task, Principal) {
        var vm = this;
        vm.clear = clear;


        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function loadTimeSheet() {

        }

    }

})();
