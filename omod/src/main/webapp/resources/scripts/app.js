
var app  = angular.module('Tag',['tagService','ngDialog']);


app.controller('tagCtrl',['$scope','TagService','ngDialog',function ($scope,TagService,ngDialog) {
    $scope.init = function(personUuid) {
            $scope.thisPersonUuid = personUuid;
          $scope.loadTags =function () {
              var payload = {
                  objectType: 'org.openmrs.Person',
                  objectUuid: personUuid
              };
              TagService.getTags(payload).then(function (res) {
                  $scope.tags = res;
              });
          }
         $scope.loadTags();
    }

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
                TagService.deleteTag(tag);
                var index = $scope.tags.indexOf(tag);
                $scope.tags.splice(index, 1);
            }, function() {
            });
        };

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
           TagService.createTag(Tag);
           $scope.loadTags();
        });
    }

}]);
