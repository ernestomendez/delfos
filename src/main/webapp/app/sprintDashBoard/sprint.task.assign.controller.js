/**
 * Created by ernesto on 9/11/16.
 */
(function () {
    'use strict';

    angular
        .module('delfosApp')
        .controller('SprintTaskAssignController', SprintTaskAssignController);

    SprintTaskAssignController.$inject = ['$scope', '$state', '$stateParams', 'AlertService', '$uibModalInstance', 'Task', 'Projects', 'entity'];

    function SprintTaskAssignController($scope, $state, $stateParams, AlertService, $uibModalInstance, Task, Projects, entity) {
        var vm = this;
        vm.getUsersByProject = getUsersByProject;
        vm.clear = clear;
        vm.task = entity;
        vm.engineer = null;
        vm.save = save;

        vm.getUsersByProject();

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function getUsersByProject() {
            Projects.byProjectName({
                name: $stateParams.project
            },onSuccess, onError);

            function onSuccess(data, headers) {
                vm.engineers = data.users;
                var i = 0;
                do {
                    var engineer = vm.engineers[i];

                    var assigned = engineer.firstName + ' ' + engineer.lastName;
                    if (assigned === vm.task.assignedTo ) {
                        vm.engineer = engineer;
                        i = vm.engineers.length;
                    }
                    i++;
                } while (i < vm.engineers.length);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function save () {
            vm.isSaving = true;

            vm.task.assignedTo = vm.engineer.firstName + ' ' + vm.engineer.lastName;

                Task.update(vm.task, onSaveSuccess, onSaveError);

            function onSaveSuccess (result) {
                $uibModalInstance.close(result);
                vm.isSaving = false;
            }

            function onSaveError () {
                vm.isSaving = false;
            }
        }


    }


})();
