'use strict';

g3charactermanagerApp.factory('Form', function ($resource) {
        return $resource('app/rest/forms/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
