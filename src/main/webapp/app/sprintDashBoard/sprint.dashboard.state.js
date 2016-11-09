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
                authorities: ['ROLE_ADMIN'],
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
                    return $translate.refresh();
                }]
            }
        })
    }
})();
