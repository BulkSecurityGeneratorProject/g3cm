'use strict';

g3charactermanagerApp.factory('AttributeType', function ($resource) {
        return $resource('app/rest/attributeTypes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
