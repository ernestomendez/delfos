(function () {
    'use strict';

    angular
        .module('delfosApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('timeSheetView', {
            parent: 'sprintDashBoard',
            url: '/{project}/timesheet/task/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ENGINEER'],
                pageTitle: 'delfosApp.sprint.sprintDashBoard.title'
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/timesheet/timesheet.view.html',
                    controller: 'TimeSheetController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Task', function (Task) {
                            return Task.get({id: $stateParams.id}).$promise;
                        }],
                        mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('task');
                            return $translate.refresh();
                        }]
                    }
                }).result.then(function () {
                    $state.go('sprintDashBoard', {project: $stateParams.project}, {reload: true});
                }, function () {
                    // $state.go('sprintDashBoard',{project: $stateParams.project}, {reload: true});
                    $state.go('^');
                });
            }]
        });
    }
})();
