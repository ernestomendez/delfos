(function() {
    'use strict';

    angular
        .module('delfosApp')
        .controller('ProjectsDeleteController',ProjectsDeleteController);

    ProjectsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Projects'];

    function ProjectsDeleteController($uibModalInstance, entity, Projects) {
        var vm = this;

        vm.projects = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Projects.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
