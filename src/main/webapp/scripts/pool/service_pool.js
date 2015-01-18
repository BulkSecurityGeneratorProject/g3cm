'use strict';

g3charactermanagerApp.factory('Pool', function ($resource) {
        return $resource('app/rest/pools/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
