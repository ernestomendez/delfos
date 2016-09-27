(function() {
    'use strict';
    angular
        .module('delfosApp')
        .factory('Activities', Activities);

    Activities.$inject = ['$resource', 'DateUtils'];

    function Activities ($resource, DateUtils) {
        var resourceUrl =  'api/activities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.creationDate = DateUtils.convertLocalDateFromServer(data.creationDate);
                        data.startDate = DateUtils.convertLocalDateFromServer(data.startDate);
                        data.endDate = DateUtils.convertLocalDateFromServer(data.endDate);
                        data.calculatedEndDate = DateUtils.convertLocalDateFromServer(data.calculatedEndDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.creationDate = DateUtils.convertLocalDateToServer(data.creationDate);
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocalDateToServer(data.endDate);
                    data.calculatedEndDate = DateUtils.convertLocalDateToServer(data.calculatedEndDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.creationDate = DateUtils.convertLocalDateToServer(data.creationDate);
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocalDateToServer(data.endDate);
                    data.calculatedEndDate = DateUtils.convertLocalDateToServer(data.calculatedEndDate);
                    return angular.toJson(data);
                }
            },
            'bySprint': {
                method: 'GET',
                url: "api/project/:project/sprint/:sprintId",
                params: {
                    project: null,
                    sprintId: null
                },
                isArray: true
            }
        });
    }
})();
