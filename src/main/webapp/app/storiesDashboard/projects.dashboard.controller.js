/**
 * Created by ernesto on 13/09/16.
 */
(function () {
    'use strict';

    angular
        .module('delfosApp')
        .controller('ProjectsDashboardController', ProjectsDashboardController);

    ProjectsDashboardController.$inject = ['$scope', '$state', 'Principal', 'Projects'];

    function ProjectsDashboardController($scope, $state, Principal, Projects) {
        var vm = this;
        vm.loadProjectsByEngineer = loadProjectsByEngineer;
        vm.account = null;

        vm.loadProjectsByEngineer();

        function loadProjectsByEngineer() {
            console.log("loadProjectsByEngineer");
            getAccount();

            Projects.byLogin({
                login: vm.account.login
            },onSuccess, onError);
            //
            // Task.byDates({
            //         projectId: vm.selectedProject.id,
            //         startDate: DateUtils.convertLocalDateToServer(startOfWeek),
            //         endDate: DateUtils.convertLocalDateToServer(endOfWeek)},
            //     onSuccess, onError);

            function onSuccess(data, headers) {
                vm.projects = data;
                console.log(vm.projects);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }

        }



        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                console.log("?????????????????????????????????????", vm.account);
            });
        }

    }

})();
