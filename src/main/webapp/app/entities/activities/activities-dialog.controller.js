(function() {
    'use strict';

    angular
        .module('delfosApp')
        .controller('ActivitiesDialogController', ActivitiesDialogController);

    ActivitiesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Activities'];

    function ActivitiesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Activities) {
        var vm = this;

        vm.activities = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.activities.id !== null) {
                Activities.update(vm.activities, onSaveSuccess, onSaveError);
            } else {
                Activities.save(vm.activities, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('delfosApp:activitiesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.creationDate = false;
        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.calculatedEndDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
