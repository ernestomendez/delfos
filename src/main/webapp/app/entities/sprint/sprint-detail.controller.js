(function() {
    'use strict';

    angular
        .module('delfosApp')
        .controller('SprintDetailController', SprintDetailController);

    SprintDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sprint'];

    function SprintDetailController($scope, $rootScope, $stateParams, previousState, entity, Sprint) {
        var vm = this;

        vm.sprint = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('delfosApp:sprintUpdate', function(event, result) {
            vm.sprint = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
