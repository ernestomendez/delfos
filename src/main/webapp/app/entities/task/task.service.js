(function() {
    'use strict';
    angular
        .module('delfosApp')
        .factory('Task', Task);

    Task.$inject = ['$resource', 'DateUtils'];

    function Task ($resource, DateUtils) {
        var resourceUrl =  'api/tasks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertLocalDateFromServer(data.startDate);
                        data.endDate = DateUtils.convertLocalDateFromServer(data.endDate);
                        data.calculatedEndDate = DateUtils.convertLocalDateFromServer(data.calculatedEndDate);
                        data.crationDate = DateUtils.convertLocalDateFromServer(data.crationDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocalDateToServer(data.endDate);
                    data.calculatedEndDate = DateUtils.convertLocalDateToServer(data.calculatedEndDate);
                    data.crationDate = DateUtils.convertLocalDateToServer(data.crationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocalDateToServer(data.endDate);
                    data.calculatedEndDate = DateUtils.convertLocalDateToServer(data.calculatedEndDate);
                    data.crationDate = DateUtils.convertLocalDateToServer(data.crationDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
