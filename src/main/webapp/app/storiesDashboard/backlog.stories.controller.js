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
        vm.logEvent = logEvent;
        vm.dropCallback = dropCallback;

        vm.models = {
            selected: null,
            lists: {}
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

                vm.models.lists[vm.backlogSprint.name] = vm.getStoriesBySprint(vm.backlogSprint);
                vm.models.lists[vm.activeSprint.name] = vm.getStoriesBySprint(vm.activeSprint);
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

        function logEvent (message, list) {
            console.log("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            console.log(message, list);
            console.log("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
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

        function logListEvent(action, event, index, external, type) {
            var message = external ? 'External ' : '';
            message += type + ' element is ' + action + ' position ' + index;
            vm.logEvent(message, event);
        }

        function insertedCallback(list, item) {
            console.log("list: ", list);
            console.log("item: ", item);
        }

    }
})();
