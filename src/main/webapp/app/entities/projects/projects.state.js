(function() {
    'use strict';

    angular
        .module('delfosApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('projects', {
            parent: 'entity',
            url: '/projects?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'delfosApp.projects.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/projects/projects.html',
                    controller: 'ProjectsController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('projects');
                    $translatePartialLoader.addPart('projectStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('projects-detail', {
            parent: 'entity',
            url: '/projects/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'delfosApp.projects.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/projects/projects-detail.html',
                    controller: 'ProjectsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('projects');
                    $translatePartialLoader.addPart('projectStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Projects', function($stateParams, Projects) {
                    return Projects.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'projects',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('projects-detail.edit', {
            parent: 'projects-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projects/projects-dialog.html',
                    controller: 'ProjectsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Projects', function(Projects) {
                            return Projects.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('projects.new', {
            parent: 'projects',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projects/projects-dialog.html',
                    controller: 'ProjectsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                startDate: null,
                                endDate: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('projects', null, { reload: 'projects' });
                }, function() {
                    $state.go('projects');
                });
            }]
        })
        .state('projects.edit', {
            parent: 'projects',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projects/projects-dialog.html',
                    controller: 'ProjectsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Projects', function(Projects) {
                            return Projects.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('projects', null, { reload: 'projects' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('projects.delete', {
            parent: 'projects',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projects/projects-delete-dialog.html',
                    controller: 'ProjectsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Projects', function(Projects) {
                            return Projects.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('projects', null, { reload: 'projects' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
