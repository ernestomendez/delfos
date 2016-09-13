(function() {
    'use strict';

    angular
        .module('delfosApp')
        .controller('PhaseDetailController', PhaseDetailController);

    PhaseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Phase'];

    function PhaseDetailController($scope, $rootScope, $stateParams, previousState, entity, Phase) {
        var vm = this;

        vm.phase = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('delfosApp:phaseUpdate', function(event, result) {
            vm.phase = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
