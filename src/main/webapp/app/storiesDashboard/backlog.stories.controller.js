/**
 * Created by ernesto on 20/09/16.
 */
(function () {
    'use strict';

    angular
        .module('delfosApp')
        .controller('BacklogStoriesController', BacklogStoriesController);

    BacklogStoriesController.$inject = ['$scope', '$state', 'SharedData', 'Sprint', 'DateUtils', 'AlertService', 'Activities', 'SharedActivity'];

    function BacklogStoriesController($scope, $state, SharedData, Sprint, DateUtils, AlertService, Activities, SharedActivity) {
        var vm = this;
        vm.getActiveSprints = getActiveSprints;
        vm.SharedData = SharedData;
        vm.getActiveSprints();
        vm.backlogSprint = null;
        vm.activeSprint = null;
        vm.getStoriesBySprint = getStoriesBySprint;
        vm.backlogStoriesList = [];
        vm.sprintStoriesList = [];
        vm.selectedStory = null;
        vm.movedStory = SharedActivity;
        vm.setDestinationList = setDestinationList;
        vm.moveActivity = moveActivity;

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
                    vm.backlogSprint = data[0];
                    vm.activeSprint = data[1];
                } else {
                    vm.backlogSprint = data[1];
                    vm.activeSprint = data[0];
                }

                vm.backlogStoriesList = vm.getStoriesBySprint(vm.backlogSprint);
                vm.sprintStoriesList = vm.getStoriesBySprint(vm.activeSprint);
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }

        }

        function getStoriesBySprint(sprint) {
            return Activities.bySprint({
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

        function setDestinationList(listName) {
            vm.destinationListName = listName;
        }

        function moveActivity(originListName, activity, index) {

            if (vm.destinationListName != originListName) {

                if (vm.destinationListName === 'backlog') {
                    //TODO esto no debe ir aquí, estas duplicando código
                    vm.movedStory.Activity = activity;
                    updateStory();
                    vm.sprintStoriesList.splice(index, 1);
                } else {
                    vm.backlogStoriesList.splice(index, 1);
                    vm.movedStory.Activity = activity;
                    vm.movedStory.Activity.sprintWeek = vm.activeSprint.name;
                    $state.go('assignStory');
                }
            } else {
                if (vm.destinationListName === 'backlog') {
                    vm.backlogStoriesList.splice(index, 1);
                } else {
                    vm.sprintStoriesList.splice(index, 1);
                }
            }

        }

        function updateStory() {
            vm.movedStory.Activity.assignedTo = null;
            vm.movedStory.Activity.storyPoints = null;
            vm.movedStory.Activity.estimatedTime = null;
            vm.movedStory.Activity.sprintWeek = vm.backlogSprint.name;
            //TODO Create constants for this magic status.
            vm.movedStory.Activity.status = 'Proposed';

            Activities.update(vm.movedStory.Activity, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {
        }

        function onSaveError() {
        }
    }
})();
