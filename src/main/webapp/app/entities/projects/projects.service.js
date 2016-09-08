(function() {
    'use strict';
    angular
        .module('delfosApp')
        .factory('Projects', Projects);

    Projects.$inject = ['$resource', 'DateUtils'];

    function Projects ($resource, DateUtils) {
        var resourceUrl =  'api/projects/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertLocalDateFromServer(data.startDate);
                        data.endDate = DateUtils.convertLocalDateFromServer(data.endDate);
                        data.estimatedEndDate = DateUtils.convertLocalDateFromServer(data.estimatedEndDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocalDateToServer(data.endDate);
                    data.estimatedEndDate = DateUtils.convertLocalDateToServer(data.estimatedEndDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocalDateToServer(data.endDate);
                    data.estimatedEndDate = DateUtils.convertLocalDateToServer(data.estimatedEndDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
