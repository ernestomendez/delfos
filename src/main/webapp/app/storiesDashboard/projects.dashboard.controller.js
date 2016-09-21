/**
 * Created by ernesto on 13/09/16.
 */
(function () {
    'use strict';

    angular
        .module('delfosApp')
        .controller('ProjectsDashboardController', ProjectsDashboardController);

    ProjectsDashboardController.$inject = ['$scope', '$state', 'Principal', 'Projects', 'SharedData'];

    function ProjectsDashboardController($scope, $state, Principal, Projects, SharedData) {
        var vm = this;
        vm.loadProjectsByEngineer = loadProjectsByEngineer;
        vm.account = null;
        vm.project = null;
        vm.sprint = null;
        vm.goToNext = goToNext;

        vm.SharedData = SharedData;

        vm.loadProjectsByEngineer();

        function loadProjectsByEngineer() {
            getAccount();

        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;

                Projects.byLogin({
                    login: vm.account.login
                },onSuccess, onError);

                function onSuccess(data, headers) {
                    vm.projects = data;
                    vm.SharedData.Project = vm.projects[0];

                }
                function onError(error) {
                    AlertService.error(error.data.message);
                }
            });
        }

        vm.sprintOptions = [{'sprint': 'Backlog'}, {'sprint': 'Sprint Active'}];


        function goToNext() {
            console.log(vm.sprint);
            if(vm.sprint === 'Backlog') {
                console.log("backlog");
                $state.go('backlogAssignStories',{project: vm.SharedData.Project.name});
            } else {
                console.log("sprint");

            }

            // $state.go();
        }

    }

})();
