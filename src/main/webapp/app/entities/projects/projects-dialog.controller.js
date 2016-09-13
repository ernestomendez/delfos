(function() {
    'use strict';

    angular
        .module('delfosApp')
        .controller('ProjectsDialogController', ProjectsDialogController);

    ProjectsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Projects'];

    function ProjectsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Projects) {
        var vm = this;

        vm.projects = entity;
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
            if (vm.projects.id !== null) {
                Projects.update(vm.projects, onSaveSuccess, onSaveError);
            } else {
                Projects.save(vm.projects, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('delfosApp:projectsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
