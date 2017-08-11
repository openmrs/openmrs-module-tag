
var app  = angular.module('Tag',['tagService','ngDialog','uicommons.common.error']);


app.controller('tagCtrl',['$scope','TagService','ngDialog',
    function ($scope,TagService,ngDialog) {
    var personUuid;
    $scope.init = function(personUuid) {
      $scope.thisPersonUuid = personUuid;
    }
       /**
         * Method to load Tags
         */
        var loadTags =function () {
            var payload = {
                objectType: 'org.openmrs.Person',
                objectUuid: $scope.thisPersonUuid
            };
            TagService.getTags(payload).then(function (res) {
                $scope.tags = res;
            });
        };

        /**
         * Delete Tag Dialog
         */
        $scope.showDialog = function(tag) {
            ngDialog.openConfirm({
                showClose: false,
                closeByEscape: true,
                closeByDocument: true,
                data: angular.toJson({
                    tag: tag.display
                }),
                   template: 'dialogTemplate'
            }).then(function() {
                TagService.deleteTag(tag).then(function (res) {
                    loadTags();
                })
            });
        };

    /**
     * Add Tag Dialog
     */
    $scope.showAddDialog = function() {
        ngDialog.openConfirm({
            showClose: false,
            closeByEscape: true,
            closeByDocument: true,
            template: 'addDialogTemplate'
        }).then(function(addedTag) {
            var Tag = {
                tag:addedTag,
                objectType:"org.openmrs.Person",
                objectUuid:$scope.thisPersonUuid
            };
           TagService.createTag(Tag).then(function (res) {
               loadTags();
           })
        });
    };
        loadTags();
    }]);