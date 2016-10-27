/**
 * Created by ernesto on 19/10/16.
 */
(function () {
    'use strict';
    angular
        .module('delfosApp')
        .factory('BacklogStories', BacklogStories);

    BacklogStories.$inject = ['$resource', 'DateUtils'];

    function BacklogStories($resource, DateUtils) {
        var resourceUrl = 'api/assign/stories';

        return $resource(resourceUrl, {},{
            'assign': {
                method: 'POST',
                transformRequest: function (data) {
                    data.creationDate = DateUtils.convertLocalDateToServer(data.creationDate);
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocalDateToServer(data.endDate);
                    data.calculatedEndDate = DateUtils.convertLocalDateToServer(data.calculatedEndDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
