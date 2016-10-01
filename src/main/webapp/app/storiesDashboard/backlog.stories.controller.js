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
        vm.backlogStoriesList = [];
        vm.sprintStoriesList = [];
        vm.backLogLogEvent = backLogLogEvent;
        vm.listBackloglogEvent = listBackloglogEvent;
        vm.activeSprintLogEvent = activeSprintLogEvent;
        vm.listSprintlogEvent = listSprintlogEvent;
        vm.dropCallback = dropCallback;
        vm.selectedStory = null;

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

        function backLogLogEvent (message, list) {
            console.log("$$$$$$$$$Backlog");
            console.log(message, list);
            console.log("#############################");
        }

        function listBackloglogEvent (message, list) {
            console.log("$$$$$$$$$listBackloglogEvent");
            console.log(message, list);
            console.log("#############################");
        }

        function activeSprintLogEvent (message, list) {
            console.log("&&&&&&&&&&Active sprint");
            console.log(message, list);
            console.log("#############################");
        }

        function listSprintlogEvent (message, list) {
            console.log("&&&&&&&&&&listSprintlogEvent");
            console.log(message, list);
            console.log("#############################");
        }

        function dropCallback (event, index, item, external, type, allowedType, items, listName) {
            console.log("event: ", event);
            console.log("index: ", index);
            console.log("item: ", item);
            console.log("external: ", external);
            console.log("type: ", type);
            console.log("allowedType: ", allowedType);
            console.log("items: ", items);
            console.log("listName", listName);

            // logListEvent('dropped at', event, index, external, type);
            // if (external) {
            //     if (allowedType === 'itemType' && !item.label) return false;
            //     if (allowedType === 'containerType' && !angular.isArray(item)) return false;
            // }
            return item;
        }
    }
})();
