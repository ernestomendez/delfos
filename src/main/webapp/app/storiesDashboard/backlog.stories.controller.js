/**
 * Created by ernesto on 20/09/16.
 */
(function () {
    'use strict';

    angular
        .module('delfosApp')
        .controller('BacklogStoriesController', BacklogStoriesController);

    BacklogStoriesController.$inject = ['$scope', '$state', 'SharedData', 'Sprint', 'DateUtils'];

    function BacklogStoriesController($scope, $state, SharedData, Sprint, DateUtils) {
        var vm = this;
        vm.getActiveSprints = getActiveSprints;
        vm.SharedData = SharedData;
        vm.getActiveSprints();



        function getActiveSprints() {
            var today = DateUtils.convertLocalDateToServer(new Date());
            Sprint.actives({
                project: vm.SharedData.Project.name,
                date: today
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                // vm.tasks = data;
                console.log(data);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

    }
})();
