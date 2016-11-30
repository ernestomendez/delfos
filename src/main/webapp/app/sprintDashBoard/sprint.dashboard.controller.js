/**
 * Created by ernesto on 8/11/16.
 *
 * Controller for sprint dashboard.
 */

(function () {
    'use strict';

    angular
        .module('delfosApp')
        .controller("SprintDashboardController", SprintDashboardController);

    SprintDashboardController.$inject = ['$scope', '$state', 'SharedData', 'Sprint', 'DateUtils', 'AlertService', 'Activities', 'Task', 'Principal'];

    function SprintDashboardController($scope, $state, SharedData, Sprint, DateUtils, AlertService, Activities, Task, Principal) {
        var vm = this;

        vm.getActiveSprints = getActiveSprints;
        vm.SharedData = SharedData;
        vm.getActiveSprints();
        vm.getStoriesBySprint = getStoriesBySprint;
        vm.sprintStoriesList = [];
        vm.setUpdatedStatus = setUpdatedStatus;
        vm.selectedStory = null;
        vm.moveTasks = moveTasks;
        vm.selecteTasks = null;
        vm.dropCallback = dropCallback;
        vm.dragStartCallback = dragStartCallback;
        vm.originStory = null;
        vm.destinationStory = null;
        vm.currentAccount = null;

        function getActiveSprints() {
            var today = DateUtils.convertLocalDateToServer(new Date());
            Sprint.actives({
                project: vm.SharedData.Project.name,
                date: today
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                if (data != 2) {
                    AlertService.error("no hay sprints");
                }

                if (data[0].name == 'Backlog') {
                    vm.activeSprint = data[1];
                } else {
                    vm.activeSprint = data[0];
                }

                vm.sprintStoriesList = vm.getStoriesBySprint(vm.activeSprint);
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }

        }

        function getStoriesBySprint(sprint) {
            return Activities.sprintDashboard({
                project: vm.SharedData.Project.name,
                sprintId: sprint.name
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                return data
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }


        /*********************************************************************************************/

        Principal.identity().then(function(account) {
            vm.currentAccount = account.firstName + ' ' + account.lastName;
        });




        /************* movimientos de las tareas  *********************************************/

        function setUpdatedStatus(statusName) {
            vm.updatedStatus = statusName;
        }

        function moveTasks(originStatus, task) {
            if (vm.updatedStatus != originStatus) {
                task.status = vm.updatedStatus;
                Task.update(task, onSaveSuccess, onSaveError);
            }

            function onSaveSuccess(result) {
                task = result;
            }

            function onSaveError() {
            }
        }

        function dropCallback(item, story) {
            vm.destinationStory = story.activity.id;
            console.log("destination", vm.destinationStory);
            console.log("origin", vm.originStory);
            if (vm.destinationStory === vm.originStory) {
                return item;
            } else {
                return false;
            }

        }

        function dragStartCallback(story) {
            vm.originStory = story.activity.id;
        }


    }

})();
