/**
 * Created by ernesto on 13/09/16.
 *
 * state for the user stories dashboard.
 */
(function () {
    'use strict';

    angular
        .module('delfosApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('dashboardProjects', {
            parent: 'app',
            url: '/',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'delfosApp.dashboard.projects.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/storiesDashboard/projectsList.html',
                    controller: 'ProjectsDashboardController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('storiesDashboard');
                    return $translate.refresh();
                }]
            }
        })
            .state('backlogAssignStories', {
                parent: 'dashboardProjects',
                url: '{project}/backlog',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'delfosApp.dashboard.projects.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/storiesDashboard/backlog.stories.html',
                        controller: 'BacklogStoriesController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('storiesDashboard');
                        return $translate.refresh();
                    }]
                }
            })
            .state('assignStory', {
                parent: 'backlogAssignStories',
                url: '/assign',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/storiesDashboard/assignStories.html',
                        controller: 'StoriesAssignController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        keyboard: false
                    }).result.then(function () {
                        $state.go('^');
                    }, function () {
                        $state.go('backlogAssignStories',{project: $stateParams.project}, {reload: true});
                    });
                }]
            });
    }
})();
