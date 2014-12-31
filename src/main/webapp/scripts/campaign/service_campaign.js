'use strict';

g3charactermanagerApp.factory('Campaign', function ($resource) {
        return $resource('app/rest/campaigns/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
