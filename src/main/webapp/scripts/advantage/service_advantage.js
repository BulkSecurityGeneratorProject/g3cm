'use strict';

g3charactermanagerApp.factory('Advantage', function ($resource) {
        return $resource('app/rest/advantages/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
