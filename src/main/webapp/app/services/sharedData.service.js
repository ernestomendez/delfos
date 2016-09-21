/**
 * Created by ernesto on 18/09/16.
 */
(function () {
    'use strict';

    angular
        .module('delfosApp')
        .factory('SharedData', SharedData);

    // SharedData.$inject = ['$resource'];

    function SharedData() {
        var Project = {
            endDate: null,
            id: null,
            name: null,
            project: null,
            startDate: null
        };

        return Project;
    }

})();
