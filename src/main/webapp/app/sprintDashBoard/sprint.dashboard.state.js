/**
 * Created by ernesto on 8/11/16.
 *
 * state for sprint dashboard.
 */
(function () {
    'use strict';

    angular
        .module('delfosApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('sprintDashBoard', {
            parent: 'app',
            url: '/{project}/sprintDashBoard',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_ENGINEER'],
                pageTitle: 'delfosApp.sprint.sprintDashBoard.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/sprintDashBoard/sprintDashboard.html',
                    controller: 'SprintDashboardController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sprint');
                    $translatePartialLoader.addPart('activities');
                    return $translate.refresh();
                }]
            }
        })
            .state('task-assign', {
                parent: 'sprintDashBoard',
                url: '/{id}/assignTask',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_ENGINEER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/sprintDashBoard/assign.task.html',
                        controller: 'SprintTaskAssignController',
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
                        $state.go('sprintDashBoard',{project: $stateParams.project}, {reload: true});
                    });
                }]
            })
    }
})();
