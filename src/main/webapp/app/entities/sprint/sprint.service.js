(function() {
    'use strict';
    angular
        .module('delfosApp')
        .factory('Sprint', Sprint);

    Sprint.$inject = ['$resource', 'DateUtils'];

    function Sprint ($resource, DateUtils) {
        var resourceUrl =  'api/sprints/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertLocalDateFromServer(data.startDate);
                        data.endDate = DateUtils.convertLocalDateFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocalDateToServer(data.endDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocalDateToServer(data.endDate);
                    return angular.toJson(data);
                }
            },
            'actives': {
                method: 'GET',
                url: "api/project/:project/sprints/active?date=:date",
                params: {
                    project: null,
                    date: null
                },
                isArray: true
            }
        });
    }
})();
