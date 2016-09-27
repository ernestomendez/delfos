/**
 * Created by ernesto on 20/09/16.
 */
(function () {
    'use strict';

    angular
        .module('delfosApp')
        .controller('BacklogStoriesController', BacklogStoriesController);

    BacklogStoriesController.$inject = ['$scope', '$state', 'SharedData', 'Sprint', 'DateUtils', 'AlertService', 'Activities'];

    function BacklogStoriesController($scope, $state, SharedData, Sprint, DateUtils, AlertService, Activities) {
        var vm = this;
        vm.getActiveSprints = getActiveSprints;
        vm.SharedData = SharedData;
        vm.getActiveSprints();
        vm.backlogSprint = null;
        vm.activeSprint = null;
        vm.getStoriesBySprint = getStoriesBySprint;
        vm.backlogStoriesList = null;
        vm.sprintStoriesList = [];

        vm.models = {
            selected: null,
            lists: {"A": vm.backlogStoriesList, "B": vm.sprintStoriesList}
        };


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

    }
})();
