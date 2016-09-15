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
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('storiesDashboard');
                    return $translate.refresh();
                }]
            }
        });
    }
})();