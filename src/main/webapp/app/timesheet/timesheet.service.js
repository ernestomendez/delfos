/**
 * Created by ernesto on 30/11/16.
 */
(function() {
    'use strict';
    angular
        .module('delfosApp')
        .factory('timeSheet', timeSheet);

    timeSheet.$inject = ['$resource', 'DateUtils'];

    function timeSheet($resource, DateUtils) {
        var resourceUrl = 'api/timesheet/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            // 'get': {
            //     method: 'GET',
            //     transformResponse: function (data) {
            //         if (data) {
            //             data = angular.fromJson(data);
            //             data.startDate = DateUtils.convertLocalDateFromServer(data.startDate);
            //             data.endDate = DateUtils.convertLocalDateFromServer(data.endDate);
            //         }
            //         return data;
            //     }
            // }
            'timeSheet': {
                method: 'GET',
                url: "api/project/:project/timesheet/task/:taskId",
                isArray: true,
                params: {
                    project: null,
                    taskId: null
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocalDateToServer(data.date);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
