(function() {
    'use strict';

    angular
        .module('delfosApp')
        .controller('ProjectsDetailController', ProjectsDetailController);

    ProjectsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Projects'];

    function ProjectsDetailController($scope, $rootScope, $stateParams, previousState, entity, Projects) {
        var vm = this;

        vm.projects = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('delfosApp:projectsUpdate', function(event, result) {
            vm.projects = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
