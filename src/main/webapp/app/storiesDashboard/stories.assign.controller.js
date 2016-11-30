/**
 * Created by ernesto on 10/10/16.
 */
(function () {
    'use strict';

    angular
        .module('delfosApp')
        .controller('StoriesAssignController', StoriesAssignController);

    StoriesAssignController.$inject = ['$scope', '$state', 'SharedData', 'AlertService', 'SharedActivity', '$uibModalInstance', 'POINTS', 'BacklogStories'];

    function StoriesAssignController($scope, $state, SharedData, AlertService, SharedActivity, $uibModalInstance, POINTS, BacklogStories) {
        var vm = this;
        vm.clear = clear;
        vm.sharedData = SharedData;
        vm.engineer = null;
        vm.engineers = vm.sharedData.Project.users;
        vm.points = POINTS;
        vm.activity = SharedActivity.Activity;
        vm.updateStory = updateStory;

        vm.pointsAssigned = null;
        vm.estimatedTime = null;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function updateStory() {
            console.log(vm.pointsAssigned);
            vm.activity.assignedTo = vm.engineer.firstName + ' ' + vm.engineer.lastName;
            vm.activity.storyPoints = vm.pointsAssigned.points;
            vm.activity.estimatedTime = vm.estimatedTime;
            vm.activity.status = 'Accepted';

            BacklogStories.assign(vm.activity, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }

})();
