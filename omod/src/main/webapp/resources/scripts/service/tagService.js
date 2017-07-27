angular.module('tagService', ['ngResource', 'uicommons.common'])
    .factory('Tag', function($resource) {
        return $resource("/" + OPENMRS_CONTEXT_PATH  + "/ws/rest/v1/tag/:uuid", {
            uuid: '@uuid'
        },{
            get: { method:'GET', isArray:false },
            delete: { method: 'DELETE', isArray:false}
        });
    })
    .factory('TagService', function(Tag) {

        return {

            /**
             * Fetches Tag with given uuid
             *
             * @param tagUuid to search against
             * @returns $promise of Tag object (REST ref representation by default)
             */
            getTag: function(tagUuid) {
                return Tag.get(tagUuid).$promise.then(function(res) {
                    return res.result;
                });
            },

            /**
             * Creates a new tag object
             *
             * @param tag the object to be created
             * @returns {*}
             */
            addTag: function (tag) {
                return new Tag(tag).$save();
            },

            /**
             * deletes the object with given uuid
             *
             * @param tagUuid
             * @returns
             */
            deleteTag: function(tagUuid) {
                return new Tag.delete(tagUuid).$promise.then(function (res) {
                    res.result;
                })
            }
        }
    });